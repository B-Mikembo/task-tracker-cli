package com.github.brice.task_tracker_cli.tasks.business.repositories;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();

    Task findById(long taskId);

    Task save(Task task);
}
