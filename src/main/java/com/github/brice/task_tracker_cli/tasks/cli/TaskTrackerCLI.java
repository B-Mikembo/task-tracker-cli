package com.github.brice.task_tracker_cli.tasks.cli;

import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;
import com.github.brice.task_tracker_cli.tasks.business.rules.services.AddTaskService;
import com.github.brice.task_tracker_cli.tasks.business.rules.services.UpdateTaskService;
import com.github.brice.task_tracker_cli.tasks.cli.resource.TaskRequest;
import com.github.brice.task_tracker_cli.tasks.cli.resource.TaskResponse;
import com.github.brice.task_tracker_cli.tasks.config.TasksRegistry;
import com.github.brice.task_tracker_cli.tasks.repositories.file.JsonTaskRepository;
import com.github.brice.task_tracker_cli.tasks.repositories.inmemory.InMemoryTaskRepository;

public class TaskTrackerCLI {

    private static TasksRegistry tasksRegistry;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Missing command");
        }
        configureRegistries();
        var addTaskService = new AddTaskService(tasksRegistry.create("json"));
        switch (args[0]) {
            case "add" -> {
                if (args.length < 2) {
                    throw new IllegalStateException("Missing description to add task");
                }
                var taskResponse = TaskResponse.fromDomain(addTaskService.execute(new Task(args[1])));
                System.out.printf("Task added successfully (ID: %s)", taskResponse.id());
            }
            case "update" -> {
                if (args.length < 2) throw new IllegalStateException("Missing updated task id");
                if (args.length < 3) throw new IllegalStateException("Missing new description for updated task");
                var taskId = args[1];
                var taskRequest = new TaskRequest(args[2]);
                var updateTaskService = new UpdateTaskService(tasksRegistry.create("json"));
                updateTaskService.execute(Long.parseLong(taskId), taskRequest.toDomain());
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
