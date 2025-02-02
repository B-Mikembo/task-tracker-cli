package com.github.brice.task_tracker_cli.tasks.repositories.file;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class JsonTask {
    private String createdAt;
    private String description;
    private String id;
    private String status;
    private String updatedAt;

    public static JsonTask fromDomain(Task task) {
        var jsonTask = new JsonTask();
        jsonTask.setId(task.id().toString());
        jsonTask.setDescription(task.description());
        jsonTask.setStatus(task.status().label());
        jsonTask.setCreatedAt(task.createdAt().toString());
        jsonTask.setUpdatedAt(task.updatedAt().toString());
        return jsonTask;
    }

    public static JsonTask fromJson(String jsonContent) {
        return new JSONReader().parseJSON(jsonContent, new JSONReader.TypeReference<>() {
        });
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Task toDomain() {
        return new Task(UUID.fromString(id), description, TaskStatus.fromLabel(status), LocalDateTime.parse(createdAt), LocalDateTime.parse(updatedAt));
    }

    public String toJson() {
        return new JSONWriter().toJSON(this);
    }
}
