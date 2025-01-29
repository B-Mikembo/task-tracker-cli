package com.github.brice.task_tracker_cli.tasks.infrastructure.file;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.domain.use_cases.ports.TaskRepository;

public class JsonTaskRepository implements TaskRepository {
    @Override
    public Task save(Task task) {
        return null;
    }
}
