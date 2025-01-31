package com.github.brice.task_tracker_cli.tasks.application.repositories;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;

public interface TaskRepository {
    Task save(Task task);
}
