package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

public class AddTaskService {
    private final TaskRepository taskRepository;

    public AddTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(Task task) {
        return taskRepository.save(task);
    }
}
