package com.github.brice.task_tracker_cli.tasks.repositories.file;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.*;

public final class Utils {
    public static BeanInfo beanInfo(Class<?> beanType) {
        try {
            return Introspector.getBeanInfo(beanType);
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Constructor<?> defaultConstructor(Class<?> beanType) {
        try {
            return beanType.getConstructor();
        } catch (NoSuchMethodException e) {
            throw (NoSuchMethodError) new NoSuchMethodError("no public default constructor " + beanType.getName()).initCause(e);
        }
    }

    public static Class<?> erase(Type type) {
        return switch (type) {
            case Class<?> clazz -> clazz;
            case ParameterizedType parameterizedType -> erase(parameterizedType.getRawType());
            case GenericArrayType genericArrayType -> erase(genericArrayType.getGenericComponentType()).arrayType();
            case TypeVariable<?> typeVariable -> erase(typeVariable.getBounds()[0]);
            case WildcardType wildcardType -> erase(wildcardType.getLowerBounds()[0]);
            default -> throw new AssertionError("unknown type " + type.getTypeName());
        };
    }

    public static Object invokeMethod(Object instance, Method method, Object... args) {
        try {
            return method.invoke(instance, args);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw (IllegalAccessError) new IllegalAccessError().initCause(e);
        }
    }

    public static Object newInstance(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Utils() {
        throw new AssertionError();
    }
}
