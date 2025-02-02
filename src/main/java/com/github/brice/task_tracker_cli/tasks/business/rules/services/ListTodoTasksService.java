package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.TaskStatus;

import java.util.List;

public class ListTodoTasksService {
    private final TaskRepository taskRepository;

    public ListTodoTasksService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> execute() {
        return taskRepository.findAll().stream()
                .filter(task -> TaskStatus.TODO == task.status())
                .toList();
    }
}
