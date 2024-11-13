package com.github.brice;

import java.time.LocalDateTime;

public record Task(
        int id,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public Task(int id, String description) {
        this(id, description, "todo", LocalDateTime.now(), LocalDateTime.now());
    }
}
