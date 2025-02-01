package com.github.brice.task_tracker_cli.tasks.dataproviders.file;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.brice.task_tracker_cli.tasks.dataproviders.file.JSONParser.Kind.*;
import static java.lang.Integer.parseInt;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.joining;
import static java.util.stream.IntStream.rangeClosed;

public class JSONParser {
    private static final Pattern PATTERN = compile(Arrays.stream(Kind.VALUES).map(k -> k.regex).collect(joining("|")));

    public static void parse(String input, JSONVisitor visitor) {
        var lexer = new Lexer(PATTERN.matcher(input));
        try {
            parse(lexer, visitor);
        } catch (IllegalStateException e) {
            throw new IllegalStateException(e.getMessage() + "\n while parsing input " + input, e);
        }
    }

    private static void parse(Lexer lexer, JSONVisitor visitor) {
        var token = lexer.next();
        switch (token.kind) {
            case LEFT_CURLY -> {
                visitor.startObject(null);
                parseObject(null, lexer, visitor);
            }
            case LEFT_BRACKET -> {
                visitor.startArray(null);
                parseArray(null, lexer, visitor);
            }
            default -> throw token.error(Kind.LEFT_CURLY, Kind.LEFT_BRACKET);
        }
    }

    private static void parseArray(String currentKey, Lexer lexer, JSONVisitor visitor) {
        var token = lexer.next();
        if (token.is(RIGHT_BRACKET)) {
            visitor.endArray(currentKey);
            return;
        }
        for (; ; ) {
            parseValue(null, token, lexer, visitor);
            token = lexer.next();
            if (token.is(RIGHT_BRACKET)) {
                visitor.endArray(currentKey);
                return;
            }
            token.expect(COMMA);
            token = lexer.next();
        }
    }

    private static void parseObject(String currentKey, Lexer lexer, JSONVisitor visitor) {
        var token = lexer.next();
        if (token.is(RIGHT_CURLY)) {
            visitor.endObject(currentKey);
            return;
        }
        for (; ; ) {
            var key = token.expect(STRING);
            lexer.next().expect(COLON);
            token = lexer.next();
            parseValue(key, token, lexer, visitor);
            token = lexer.next();
            if (token.is(RIGHT_CURLY)) {
                visitor.endObject(currentKey);
                return;
            }
            token.expect(COMMA);
            token = lexer.next();
        }
    }

    private JSONParser() {
        throw new AssertionError();
    }

    private static void parseValue(String currentKey, Token token, Lexer lexer, JSONVisitor visitor) {
        switch (token.kind) {
            case INTEGER -> visitor.value(currentKey, parseInt(token.text));
            case STRING -> visitor.value(currentKey, token.text);
            case LEFT_CURLY -> {
                visitor.startObject(currentKey);
                parseObject(currentKey, lexer, visitor);
            }
            case LEFT_BRACKET -> {
                visitor.startArray(currentKey);
                parseArray(currentKey, lexer, visitor);
            }
            default -> throw token.error(STRING, LEFT_BRACKET, LEFT_CURLY);
        }
    }

    enum Kind {
        INTEGER("([0-9]+)"),
        STRING("\"([^\\\"]*)\""),
        LEFT_CURLY("(\\{)"),
        RIGHT_CURLY("(\\})"),
        LEFT_BRACKET("(\\[)"),
        RIGHT_BRACKET("(\\])"),
        COLON("(\\:)"),
        COMMA("(\\,)"),
        BLANK("([ \t]+)");

        private static final Kind[] VALUES = values();
        private final String regex;

        Kind(String regex) {
            this.regex = regex;
        }
    }

    public interface JSONVisitor {
        void endArray(String key);

        void endObject(String key);

        void startArray(String key);

        void startObject(String key);

        void value(String key, Object value);
    }

    private record Lexer(Matcher matcher) {
        private Token next() {
            for (; ; ) {
                if (!matcher.find()) {
                    throw new IllegalStateException("no token recognized");
                }
                var index = rangeClosed(1, matcher.groupCount()).filter(i -> matcher.group(i) != null).findFirst().orElseThrow();
                var kind = Kind.VALUES[index - 1];
                if (kind != Kind.BLANK) {
                    return new Token(kind, matcher.group(index), matcher().start(index));
                }
            }
        }
    }

    private record Token(Kind kind, String text, int location) {

        public boolean is(Kind kind) {
            return this.kind == kind;
        }

        private String expect(Kind kind) {
            if (this.kind != kind) {
                throw error(kind);
            }
            return text;
        }

        public IllegalStateException error(Kind... expectedKinds) {
            return new IllegalStateException("expect " + Arrays.stream(expectedKinds).map(Kind::name).collect(joining(", ")) + " but recognized " + kind + " at " + location);
        }
    }
}
