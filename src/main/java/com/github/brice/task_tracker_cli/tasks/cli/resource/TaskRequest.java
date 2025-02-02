package com.github.brice.task_tracker_cli.tasks.cli.resource;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

public record TaskRequest(String description) {
    public Task toDomain() {
        return new Task(description);
    }
}
