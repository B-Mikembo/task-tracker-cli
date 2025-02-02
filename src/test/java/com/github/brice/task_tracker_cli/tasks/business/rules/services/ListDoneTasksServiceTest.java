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

class ListDoneTasksServiceTest {
    private ListDoneTasksService listDoneTasksService;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        taskRepository = new InMemoryTaskRepository();
        listDoneTasksService = new ListDoneTasksService(taskRepository);
    }

    @Test
    void userCanListDoneTasks() {
        taskRepository.save(new Task(1L, "Buy milk", TaskStatus.IN_PROGRESS, LocalDateTime.now(), LocalDateTime.now()));
        taskRepository.save(new Task(2L, "Buy bread", TaskStatus.DONE, LocalDateTime.now(), LocalDateTime.now()));
        var doneTasks = listDoneTasksService.execute();
        assertEquals(1, doneTasks.size());
        assertTrue(doneTasks.stream().allMatch(task -> TaskStatus.DONE == task.status()));
    }
}