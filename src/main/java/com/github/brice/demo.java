package com.github.brice;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface demo {

    static void main(String[] args) {
        var commandRegistry = commandRegistry();

        commandRegistry.command(args).accept(args);
    }

    static void process(String[] args) {
        if(args.length < 1) {
            throw new IllegalArgumentException("Usage: <command> [parameters]");
        }

    }

    static CommandRegistry commandRegistry() {
        return new CommandRegistry.Builder()
                .registerCommand("add", addTask())
                .toRegistry();
    }

    static Consumer<String[]> addTask() {
        return args -> {
            System.out.println("In Add Task implementation");
            if (args.length < 2) {
                throw new IllegalArgumentException("Task title is required");
            }
            var tasks = loadTasks().get();
            var task = new Task(tasks.length() + 1, args[1], "todo", LocalDateTime.now(), LocalDateTime.now());
            tasks.put(new JSONObject(task));
            saveTasks().accept(tasks);
            System.out.println("Task added: " + task.description());
        };
    }

    static Supplier<JSONArray> loadTasks() {
        return () -> {
            var path = Path.of("tasks.json");
            try {
                var content = Files.readString(path);
                return new JSONArray(content);
            } catch (IOException e) {
                throw new IllegalStateException("Error loading tasks: " + e.getMessage());
            }
        };
    }

    static Consumer<JSONArray> saveTasks() {
        return tasks -> {
            var path = Path.of("tasks.json");
            try {
                Files.writeString(path, tasks.toString(4), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new IllegalStateException("Error saving tasks: " + e.getMessage());
            }
        };
    }
}
