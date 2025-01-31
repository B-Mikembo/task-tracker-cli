package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

public class JSONParser {
    private JSONParser() {
        throw new AssertionError();
    }

    enum Kind {

        NULL("(null)"),
        TRUE("(true)"),
        FALSE("(false)"),
        DOUBLE("([0-9]*\\.[0-9]*)"),
        INTEGER("([0-9]+)"),
        STRING("\"([^\\\"]*)\""),
        LEFT_CURLY("(\\{)"),
        RIGHT_CURLY("(\\})"),
        LEFT_BRACKET("(\\[)"),
        RIGHT_BRACKET("(\\])"),
        COLON("(\\:)"),
        COMMA("(\\,)"),
        BLANK("([ \t]+)");

        private final String regex;

        Kind(String regex) {
            this.regex = regex;
        }
        private static final Kind[] VALUES = values();
    }

    public interface JSONVisitor {
        void value(String key, Object value);
        void startObject(String key);
        void endObject(String key);
        void startArray(String key);
        void endArray(String key);
    }
}
