package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Objects;

public class JSONReader {


    public <T> T parseJSON(String text, Class<T> expectedClass) {
        return expectedClass.cast(parseJSON(text, (Type) expectedClass));
    }

    public Object parseJSON(String text, Type expectedType) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(expectedType);
        return null;
    }
}
