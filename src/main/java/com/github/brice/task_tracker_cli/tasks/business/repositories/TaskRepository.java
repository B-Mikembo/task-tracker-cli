package com.github.brice.task_tracker_cli.tasks.business.repositories;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

import java.util.UUID;

public interface TaskRepository {
    Task save(Task task);
    Task findById(long taskId);
}
