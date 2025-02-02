package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;

public class MarkDoneService {
    private final TaskRepository taskRepository;

    public MarkDoneService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(long taskId) {
        var existingTask = taskRepository.findById(taskId);
        var updatedTask = existingTask.markDone();
        taskRepository.save(updatedTask);
    }
}
