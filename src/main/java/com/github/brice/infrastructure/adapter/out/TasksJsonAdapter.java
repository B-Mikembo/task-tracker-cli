package com.github.brice.infrastructure.adapter.out;

import com.github.brice.Task;
import com.github.brice.application.out.Tasks;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TasksJsonAdapter implements Tasks {
    private final Path filePath;
    private final Gson gson = new Gson();
    private List<Task> tasks;

    public TasksJsonAdapter(String filePath) {
        this.filePath = Path.of(filePath);
        tasks = loadTasks();
    }

    private List<Task> loadTasks() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
                return new ArrayList<>();
            }
            var json = Files.readString(filePath);
            return gson.fromJson(json.isEmpty() ? "[]" : json, new TypeToken<>() {
            }.getType());
        } catch (IOException e) {
            throw new RuntimeException("Error reading tasks from file", e);
        }
    }

    @Override
    public Task save(Task task) {
        if(task.id() == 0) {
            var newTask = new Task()
        }
        return null;
    }
}
