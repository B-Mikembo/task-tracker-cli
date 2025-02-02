package com.github.brice.task_tracker_cli.tasks.cli.resource;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

public record TaskResponse(
        long id
) {

    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(
                task.id()
        );
    }
}
