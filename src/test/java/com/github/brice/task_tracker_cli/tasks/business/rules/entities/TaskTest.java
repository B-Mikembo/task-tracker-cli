package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {

    @Test
    void userCanMarkTaskAsInProgress() {
        var existingTask = new Task("Buy milk");
        var updatedTask = existingTask.markInProgress();
        assertEquals(TaskStatus.IN_PROGRESS, updatedTask.status());
    }

    @Test
    void userCanMarkDoneTask() {
        var existingTask = new Task("Buy milk");
        var updateTask = existingTask.markDone();
        assertEquals(TaskStatus.DONE, updateTask.status());
    }
}