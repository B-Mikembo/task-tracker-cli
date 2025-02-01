package com.github.brice.task_tracker_cli.tasks.entrypoints.cli.resource;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;

import java.util.UUID;

public record TaskResponse(
        UUID id
) {

    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(
                task.id()
        );
    }
}
