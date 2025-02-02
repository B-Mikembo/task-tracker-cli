package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @ParameterizedTest
    @EmptySource
    @ValueSource(strings = {" ", "\n", "\t"})
    void userCannotCreateTaskWithEmptyDescription(String description) {
        assertThrows(IllegalArgumentException.class, () -> new Task(description));
    }

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