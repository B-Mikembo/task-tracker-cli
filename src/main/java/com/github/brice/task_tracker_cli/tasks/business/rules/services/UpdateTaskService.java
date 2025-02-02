package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

import java.util.UUID;

public class UpdateTaskService {
    private final TaskRepository taskRepository;

    public UpdateTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long taskId, Task task) {
        var existingTask = taskRepository.findById(taskId);
        taskRepository.save(existingTask.update(task));
    }
}
