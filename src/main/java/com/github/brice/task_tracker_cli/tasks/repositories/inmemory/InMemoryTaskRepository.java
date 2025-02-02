package com.github.brice.task_tracker_cli.tasks.repositories.inmemory;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

import java.util.HashMap;
import java.util.UUID;

public class InMemoryTaskRepository implements TaskRepository {
    private final HashMap<Long, Task> tasks = new HashMap<>();

    @Override
    public Task findById(long taskId) {
        return tasks.get(taskId);
    }

    @Override
    public Task save(Task task) {
        tasks.put(task.id(), task);
        return task;
    }
}
