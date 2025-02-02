package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.TaskStatus;
import com.github.brice.task_tracker_cli.tasks.repositories.inmemory.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ListTodoTasksServiceTest {
    private ListTodoTasksService listTodoTasksService;
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        taskRepository = new InMemoryTaskRepository();
        listTodoTasksService = new ListTodoTasksService(taskRepository);
    }

    @Test
    void userCanListAllTodoTask() {
        var todoTask = taskRepository.save(new Task(1L, "Buy milk", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
        var inProgressTask = taskRepository.save(new Task(2L, "Buy bread", TaskStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now()));
        var actualTodoTasks = listTodoTasksService.execute();
        assertTrue(actualTodoTasks.stream().allMatch(actualTask -> TaskStatus.TODO == actualTask.status()));
    }
}