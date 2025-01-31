package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class TaskEntity {
    private UUID id;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public UUID id() {
        return id;
    }

    public TaskEntity setId(UUID id) {
        this.id = id;
        return this;
    }

    public String description() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String status() {
        return status;
    }

    public TaskEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String createdAt() {
        return createdAt;
    }

    public TaskEntity setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String updatedAt() {
        return updatedAt;
    }

    public TaskEntity setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static TaskEntity fromDomain(Task task) {
        return new TaskEntity().setId(task.id())
                .setDescription(task.description())
                .setStatus(task.status().label())
                .setCreatedAt(task.createdAt().toString())
                .setUpdatedAt(task.updatedAt().toString());
    }

    public Task toDomain() {
        return new Task(id, description, TaskStatus.fromLabel(status), LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));
    }
}
