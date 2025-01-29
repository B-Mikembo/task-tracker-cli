package com.github.brice.task_tracker_cli.tasks.domain.use_cases.ports;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;

public interface TaskRepository {
    Task save(Task task);
}
