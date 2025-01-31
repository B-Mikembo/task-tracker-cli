package com.github.brice.task_tracker_cli.tasks.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public record Task(
        UUID id,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public Task(String description) {
        this(UUID.randomUUID(), description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
    }

    public Task {
        if(description.isBlank()) {
            throw new IllegalArgumentException();
        }
        requireNonNull(id);
        requireNonNull(description);
        requireNonNull(status);
        requireNonNull(createdAt);
        requireNonNull(updatedAt);
    }
}
