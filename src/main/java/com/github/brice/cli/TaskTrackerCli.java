package com.github.brice.cli;

import com.github.brice.cli.controller.TaskController;
import com.github.brice.cli.model.TaskFile;
import com.github.brice.cli.view.View;

public class TaskTrackerCli {
    public static void main(String[] args) {
        var taskController = new TaskController(new View(), new TaskFile());
        taskController.run(args);
    }
}
