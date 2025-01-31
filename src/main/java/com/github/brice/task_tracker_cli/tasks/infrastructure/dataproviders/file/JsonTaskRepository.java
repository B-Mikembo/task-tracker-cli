package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.application.repositories.TaskRepository;

public class JsonTaskRepository implements TaskRepository {
    @Override
    public Task save(Task task) {
        var jsonWriter = new JSONWriter();
        var taskEntity = TaskEntity.fromDomain(task);
        var jsonTaskEntity = jsonWriter.toJSON(taskEntity);
        System.out.println("Saved task: " + jsonTaskEntity);
        return null;
    }
}
