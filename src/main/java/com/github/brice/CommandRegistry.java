package com.github.brice;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public record CommandRegistry(Map<String, Consumer<String[]>> commandMap) {
    public Consumer<String[]> command(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("No command provided");
        }
        return commandMap.get(args[0]);
    }

    public static class Builder {
        private final HashMap<String, Consumer<String[]>> map = new HashMap<>();

        public Builder registerCommand(String strCommand, Consumer<String[]> action) {
            map.put(strCommand, action);
            return this;
        }

        public CommandRegistry toRegistry() {
            return new CommandRegistry(Map.copyOf(map));
        }
    }
}
