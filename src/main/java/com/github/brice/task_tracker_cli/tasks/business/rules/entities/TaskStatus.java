package com.github.brice.task_tracker_cli.tasks.business.rules.entities;

import java.util.Arrays;
import java.util.Objects;

public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in progress"),
    DONE("done");

    private final String label;

    private TaskStatus(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static TaskStatus fromLabel(String label) {
        Objects.requireNonNull(label);
        return Arrays.stream(values())
                .filter(taskStatus -> label.equals(taskStatus.label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown label: " + label));
    }
}
