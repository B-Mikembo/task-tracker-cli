package com.github.brice.task_tracker_cli.tasks.application;

import com.github.brice.task_tracker_cli.tasks.config.TasksRegistry;
import com.github.brice.task_tracker_cli.tasks.infrastructure.file.JsonTaskRepository;

public class TaskTrackerCLI {

    private static TasksRegistry tasksRegistry;

    public static void main(String[] args) {
        configureRegistries();
    }

    private static void configureRegistries() {
        configureTasksRegistry();
    }

    private static void configureTasksRegistry() {
        tasksRegistry.register("json", JsonTaskRepository::new);
    }
}
