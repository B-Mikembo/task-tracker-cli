package com.github.brice.cli.controller;

import com.github.brice.cli.model.TaskFile;
import com.github.brice.cli.view.View;

import java.util.Arrays;

public class TaskController {

    private View view;
    private TaskFile taskFile;

    public TaskController(View view, TaskFile taskFile) {
        this.view = view;
        this.taskFile = taskFile;
    }

    public void run(String[] args) {
        if (args.length < 1) {
            throw new IllegalStateException("At least one parameter is mandatory");
        }
        var action = Action.actionByLabel(args[0]);
        switch (action) {
            case ADD -> {
                if (args.length < 2) {
                    throw new IllegalStateException("Missing task name");
                }
                addTask(args[1]);
            }
            default -> throw new IllegalArgumentException("Unknown action: " + action);
        }
    }

    private void addTask(String taskName) {
        System.out.println("You want add task with name: " + taskName);
    }

    enum Action {
        ADD("add");

        private final String label;

        Action(String label) {
            this.label = label;
        }

        public static Action actionByLabel(String label) {
            return Arrays.asList(values()).stream()
                    .filter(action -> action.label.equals(label))
                    .findFirst()
                    .orElse(null);
        }

        public String label() {
            return label;
        }
    }
}
