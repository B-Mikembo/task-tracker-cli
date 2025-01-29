package com.github.brice.task_tracker_cli.tasks.application.cli;

import com.github.brice.task_tracker_cli.tasks.config.TasksRegistry;
import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.domain.services.AddTaskService;
import com.github.brice.task_tracker_cli.tasks.infrastructure.file.JsonTaskRepository;

public class TaskTrackerCLI {

    private static TasksRegistry tasksRegistry;

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Missing command");
        }
        configureRegistries();
        var addTaskService = new AddTaskService(tasksRegistry.create("json"));
        switch (args[0]) {
            case "add" -> {
                if(args.length < 2) {
                    throw new IllegalStateException("Missing description to add task");
                }
                addTaskService.execute(new Task(args[1]));
            }
            default -> throw new IllegalArgumentException("Unknown command: " + args[0]);
        }
    }

    private static void configureRegistries() {
        configureTasksRegistry();
    }

    private static void configureTasksRegistry() {
        tasksRegistry = new TasksRegistry();
        tasksRegistry.register("json", JsonTaskRepository::new);
    }
}
