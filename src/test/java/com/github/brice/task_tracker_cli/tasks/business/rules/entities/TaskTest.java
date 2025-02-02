package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    @Test
    void userCanUpdateTask() {
        var existingTask = new Task("Buy milk");
        var updateTaskRequest = new Task("Buy bread");
        var updatedTask = existingTask.update(updateTaskRequest);
        assertEquals(existingTask.createdAt(), updatedTask.createdAt());
        assertEquals(existingTask.id(), updatedTask.id());
        assertEquals(existingTask.status(), updatedTask.status());
        assertEquals(updateTaskRequest.description(), updatedTask.description());
        assertEquals(updateTaskRequest.updatedAt(), updatedTask.updatedAt());
    }
}