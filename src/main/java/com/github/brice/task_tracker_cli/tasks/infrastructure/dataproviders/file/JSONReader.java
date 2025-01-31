package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class JSONReader {

    private static final ClassValue<BeanData> BEAN_DATA_CLASS_VALUE = new ClassValue<>() {
        @Override
        protected BeanData computeValue(Class<?> type) {
            var beanInfo = Utils.beanInfo(type);
            var constructor = Utils.defaultConstructor(type);
            var map = Arrays.stream(beanInfo.getPropertyDescriptors())
                    .filter(property -> !property.getName().equals("class"))
                    .collect(Collectors.toMap(PropertyDescriptor::getName, Function.identity()));
            return new BeanData(constructor, map);
        }
    };
    private final ArrayList<TypeMatcher> typeMatchers = new ArrayList<>();

    public void addTypeMatcher(TypeMatcher typeMatcher) {
        requireNonNull(typeMatcher);
        typeMatchers.add(typeMatcher);
    }

    public <T> T parseJSON(String text, Class<T> expectedClass) {
        return expectedClass.cast(parseJSON(text, (Type) expectedClass));
    }

    public Object parseJSON(String text, Type expectedType) {
        requireNonNull(text);
        requireNonNull(expectedType);
        var stack = new ArrayDeque<Context<?>>();
        var visitor = new JSONParser.JSONVisitor() {
            private Object result;

            @Override
            public void endArray(String key) {
                endObject(key);
            }

            @Override
            public void endObject(String key) {
                var instance = stack.pop().finish();
                if (stack.isEmpty()) {
                    result = instance;
                    return;
                }
                var context = stack.peek();
                context.populate(key, instance);
            }

            @Override
            public void startArray(String key) {
                startObject(key);
            }

            @Override
            public void startObject(String key) {
                var context = stack.peek();
                var type = context == null ? expectedType : context.objectBuilder.typeProvider.apply(key);
                var objectBuilder = findObjectBuilder(type);
                stack.push(Context.create(objectBuilder));
            }

            @Override
            public void value(String key, Object value) {
                var context = stack.peek();
                context.populate(key, value);
            }
        };
        JSONParser.parse(text, visitor);
        return visitor.result;
    }

    ObjectBuilder<?> findObjectBuilder(Type type) {
        return typeMatchers.reversed().stream()
                .flatMap(typeMatcher -> typeMatcher.match(type).stream())
                .findFirst()
                .orElseGet(() -> ObjectBuilder.bean(Utils.erase(type)));
    }

    @FunctionalInterface
    public interface TypeMatcher {
        Optional<ObjectBuilder<?>> match(Type type);
    }

    private record BeanData(Constructor<?> constructor, Map<String, PropertyDescriptor> propertyMap) {
        PropertyDescriptor findProperty(String key) {
            var property = propertyMap.get(key);
            if (property == null) {
                throw new IllegalStateException("unknown key " + key + " for bean " + constructor.getDeclaringClass().getName());
            }
            return property;
        }
    }

    public record ObjectBuilder<T>(Function<? super String, ? extends Type> typeProvider,
                                   Supplier<T> supplier,
                                   Populater<? super T> populater,
                                   Function<? super T, ?> finisher) {
        public static ObjectBuilder<Object> bean(Class<?> beanClass) {
            requireNonNull(beanClass);
            var beanData = BEAN_DATA_CLASS_VALUE.get(beanClass);
            return new ObjectBuilder<>(
                    key -> beanData.findProperty(key).getWriteMethod().getGenericParameterTypes()[0],
                    () -> Utils.newInstance(beanData.constructor),
                    (instance, key, value) -> {
                        var property = beanData.findProperty(key);
                        Utils.invokeMethod(instance, property.getWriteMethod(), value);
                    },
                    Function.identity()
            );
        }

        public static ObjectBuilder<List<Object>> list(Type elementType) {
            requireNonNull(elementType);
            return new ObjectBuilder<>(
                    key -> elementType,
                    ArrayList::new,
                    (list, key, value) -> list.add(value),
                    List::copyOf
            );
        }

        private interface Populater<T> {
            void populate(T instance, String key, Object value);
        }
    }

    private record Context<T>(ObjectBuilder<T> objectBuilder, T result) {
        public Object finish() {
            return objectBuilder.finisher.apply(result);
        }

        private static <T> Context<T> create(ObjectBuilder<T> objectBuilder) {
            return new Context<>(objectBuilder, objectBuilder.supplier.get());
        }

        private void populate(String key, Object value) {
            objectBuilder.populater.populate(result, key, value);
        }
    }
}
