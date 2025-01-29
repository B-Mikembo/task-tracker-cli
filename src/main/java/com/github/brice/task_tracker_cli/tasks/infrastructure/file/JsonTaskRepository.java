package com.github.brice.task_tracker_cli.tasks.infrastructure.file;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.domain.services.ports.TaskRepository;

public class JsonTaskRepository implements TaskRepository {
    @Override
    public Task save(Task task) {
        System.out.println("[infrastructure] save Task id => " + task.id());
        return null;
    }
}
