package com.github.brice.task_tracker_cli.tasks.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private UUID id = UUID.randomUUID();
    private String description;
    private TaskStatus status = TaskStatus.TODO;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();

    public Task(String description) {
        this.description = description;
    }

    public Task(UUID id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
