package com.github.brice.task_tracker_cli.tasks.cli.resource;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

public record TaskResponse(
        long id,
        String description,
        String status,
        String createdAt,
        String updatedAt
) {

    public static TaskResponse fromDomain(Task task) {
        return new TaskResponse(
                task.id(),
                task.description(),
                task.status().label(),
                task.createdAt().toString(),
                task.updatedAt().toString()
        );
    }

    @Override
    public String toString() {
        return "\n\t{\n\t " + "id=" + id +
                ",\n\t description='" + description + '\'' +
                ",\n\t status='" + status + '\'' +
                ",\n\t createdAt='" + createdAt + '\'' +
                ",\n\t updatedAt='" + updatedAt + '\'' +
                "\n\t}\n";
    }
}
