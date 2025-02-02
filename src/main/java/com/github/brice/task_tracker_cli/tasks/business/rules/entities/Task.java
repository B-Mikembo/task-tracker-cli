package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;

public class Task {
    private LocalDateTime createdAt;
    private String description;
    private long id;
    private TaskStatus status;
    private LocalDateTime updatedAt;

    public Task(String description) {
        this(0L, description, TaskStatus.TODO, LocalDateTime.now(), LocalDateTime.now());
    }

    public Task(long id, String description, TaskStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public long id() {
        return id;
    }

    public Task markAsInProgress() {
        status = TaskStatus.IN_PROGRESS;
        return this;
    }

    public TaskStatus status() {
        return status;
    }

    public Task update(Task task) {
        requireNonNull(task);
        return new Task(id, task.description(), status, createdAt, task.updatedAt());
    }

    public String description() {
        return description;
    }

    public LocalDateTime updatedAt() {
        return updatedAt;
    }
}
