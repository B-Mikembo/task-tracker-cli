package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;

public class MarkTaskInProgressService {
    private final TaskRepository taskRepository;

    public MarkTaskInProgressService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long id) {
        var existingTask = taskRepository.findById(id);
        var updatedTask = existingTask.markAsInProgress();
        taskRepository.save(updatedTask);
    }
}
