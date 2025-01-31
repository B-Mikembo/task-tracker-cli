package com.github.brice.task_tracker_cli.tasks.infrastructure.entrypoints.cli;

import com.github.brice.task_tracker_cli.tasks.infrastructure.config.TasksRegistry;
import com.github.brice.task_tracker_cli.tasks.domain.entities.Task;
import com.github.brice.task_tracker_cli.tasks.application.services.AddTaskService;
import com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file.JsonTaskRepository;
import com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.inmemory.InMemoryTaskRepository;
import com.github.brice.task_tracker_cli.tasks.infrastructure.entrypoints.cli.resource.TaskResponse;

public class TaskTrackerCLI {

    private static TasksRegistry tasksRegistry;

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Missing command");
        }
        configureRegistries();
        var addTaskService = new AddTaskService(tasksRegistry.create("memory"));
        switch (args[0]) {
            case "add" -> {
                if(args.length < 2) {
                    throw new IllegalStateException("Missing description to add task");
                }
                var taskResponse = TaskResponse.fromDomain(addTaskService.execute(new Task(args[1])));
                System.out.printf("Task added successfully (ID: %s)", taskResponse.id());
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
        tasksRegistry.register("memory", InMemoryTaskRepository::new);
    }
}
