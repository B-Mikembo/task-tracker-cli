package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.joining;

public class JSONWriter {
    private interface Generator{
        String generate(JSONWriter writer, Object object);
    }

    private static final ClassValue<Generator> GENERATOR_CLASS_VALUE = new ClassValue<>() {
        @Override
        protected Generator computeValue(Class<?> type) {
            var properties = beanProperties(type);
            var generators = properties.stream()
                    .<Generator>map(property -> {
                        var getter = property.getReadMethod();
                        var propertyName = property.getName();
                        var key = "\"" + propertyName + "\": ";
                        return (writer, object) -> key + writer.toJSON(Utils.invokeMethod(object, getter));
                    })
                    .toList();
            return (writer, object) -> generators.stream()
                    .map(generator -> generator.generate(writer, object))
                    .collect(joining(", ", "{", "}"));
        }
    };

    private static List<PropertyDescriptor> beanProperties(Class<?> type) {
        var beanInfo = Utils.beanInfo(type);
        return Arrays.stream(beanInfo.getPropertyDescriptors())
                .filter(property -> !property.getName().equals("class"))
                .toList();
    }

    private final HashMap<Class<?>, Generator> map = new HashMap<>();

    public String toJSON(Object object) {
        return switch (object) {
            case null -> "null";
            case Boolean value -> "" + value;
            case Number value -> "" + value;
            case String value -> "\"" + value + "\"";
            case UUID value -> value.toString();
            default -> {
                var type = object.getClass();
                var generator = map.get(type);
                if(generator == null) {
                    generator = GENERATOR_CLASS_VALUE.get(type);
                }
                yield generator.generate(this, object);
            }
        };
    }
}
