package com.github.brice.task_tracker_cli.tasks.business.rules.services;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.TaskStatus;
import com.github.brice.task_tracker_cli.tasks.repositories.inmemory.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListInProgressTasksServiceTest {
    private ListInProgressTasksService listInProgressTasksService;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository = new InMemoryTaskRepository();
        listInProgressTasksService = new ListInProgressTasksService(taskRepository);
    }

    @Test
    void userCanListInProgressTasks() {
        taskRepository.save(new Task(1L, "Buy milk", TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now()));
        taskRepository.save(new Task(2L, "Buy bread", TaskStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now()));
        var inProgressTasks = listInProgressTasksService.execute();
        assertEquals(1, inProgressTasks.size());
        assertTrue(inProgressTasks.stream().allMatch(task -> TaskStatus.IN_PROGRESS == task.status()));
    }
}