package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Utils {
    private Utils() {
        throw new AssertionError();
    }

    public static BeanInfo beanInfo(Class<?> beanType) {
        try {
            return Introspector.getBeanInfo(beanType);
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
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
}
