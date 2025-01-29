package com.github.brice.task_tracker_cli.tasks.domain.use_cases;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.domain.use_cases.ports.TaskRepository;

public class AddTaskService {
    private final TaskRepository taskRepository;

    public AddTaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task execute(Task task) {
        return taskRepository.save(task);
    }
}
