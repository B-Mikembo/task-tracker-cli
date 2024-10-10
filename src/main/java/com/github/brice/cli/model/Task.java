package com.github.brice.cli.model;

import java.time.LocalDate;

public record Task(
        int id,
        String description,
        Status status,
        LocalDate createdAt,
        LocalDate updateAt
) {
    public Task(int id, String description) {
        this(id, description, Status.TODO, LocalDate.now(), LocalDate.now());
    }
}
