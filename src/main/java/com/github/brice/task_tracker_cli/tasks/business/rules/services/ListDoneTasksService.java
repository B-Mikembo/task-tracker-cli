package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.TaskStatus;

import java.util.List;

public class ListDoneTasksService {
    private final TaskRepository taskRepository;

    public ListDoneTasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute() {
        return taskRepository.findAll().stream()
                .filter(task -> TaskStatus.DONE == task.status())
                .toList();
    }
}
