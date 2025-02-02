package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void userCanMarkTaskAsInProgress() {
        var existingTask = new Task("Buy milk");
        var updatedTask = existingTask.markAsInProgress();
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.status());
    }
}