package com.github.brice.task_tracker_cli.tasks.config;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;

import java.util.HashMap;
import java.util.function.Supplier;

public class TasksRegistry {
    private final HashMap<String, Supplier<? extends TaskRepository>> map = new HashMap<>();

    public void register(String name, Supplier<? extends TaskRepository> supplier) {
        map.put(name, supplier);
    }

    public TaskRepository create(String name) {
        return map.computeIfAbsent(name, n -> {throw new IllegalArgumentException("Unknown " + n);})
                .get();
    }
}
