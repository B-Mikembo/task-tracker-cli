package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Task(
        long id,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Task(String description) {
        this(0L, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
    }

    public Task {
        if (description.isBlank()) {
            throw new IllegalArgumentException();
        }
        requireNonNull(description);
        requireNonNull(status);
        requireNonNull(createdAt);
        requireNonNull(updatedAt);
    }

    public Task update(Task task) {
        requireNonNull(task);
        return new Task(id, task.description(), status, createdAt, task.updatedAt());
    }
}
