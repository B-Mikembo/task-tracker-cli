package com.github.brice.task_tracker_cli.tasks.dataproviders.inmemory;

import com.github.brice.task_tracker_cli.tasks.application.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryTaskRepository implements TaskRepository {
    private final HashMap<UUID, Task> tasks = new HashMap<>();
    @Override
    public Task save(Task task) {
        tasks.put(task.id(), task);
        return task;
    }
}
