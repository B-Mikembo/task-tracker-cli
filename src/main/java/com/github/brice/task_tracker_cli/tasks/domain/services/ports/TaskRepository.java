package com.github.brice.task_tracker_cli.tasks.domain.services.ports;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;

public interface TaskRepository {
    Task save(Task task);
}
