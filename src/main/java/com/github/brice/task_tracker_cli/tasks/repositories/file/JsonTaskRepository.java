package com.github.brice.task_tracker_cli.tasks.repositories.file;

import com.github.brice.task_tracker_cli.tasks.business.repositories.TaskRepository;
import com.github.brice.task_tracker_cli.tasks.business.rules.entities.Task;

import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.joining;

public class JsonTaskRepository implements TaskRepository {
    private static final String FILE_PATH = "tasks.json";
    private final List<JsonTask> jsonTasks;
    private final long nextTaskId;
    private final JSONReader reader = new JSONReader();

    public JsonTaskRepository() {
        reader.addTypeMatcher(listTypeMatcher());
        jsonTasks = readJsonFile();
        nextTaskId = jsonTasks.stream()
                .mapToLong(JsonTask::getId)
                .max().orElse(0L) + 1;
    }

    private static JSONReader.TypeMatcher listTypeMatcher() {
        return type -> Optional.of(type)
                .flatMap(t -> t instanceof ParameterizedType parameterizedType ? Optional.of(parameterizedType) : Optional.empty())
                .filter(t -> t.getRawType() == List.class)
                .map(t -> JSONReader.ObjectBuilder.list(t.getActualTypeArguments()[0]));
    }

    private List<JsonTask> readJsonFile() {
        createFileIfNecessary();
        try (var bufferReader = new BufferedReader(new FileReader(FILE_PATH))) {
            var jsonContent = new StringBuilder();
            var line = "";
            while ((line = bufferReader.readLine()) != null) {
                jsonContent.append(line);
            }
            return reader.parseJSON(jsonContent.toString(), new JSONReader.TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException("issue when reading json file content");
        }
    }

    private void createFileIfNecessary() {
        try {
            var path = Paths.get(FILE_PATH);
            var file = Files.exists(path);
            if (!file) {
                Files.createFile(path);
                try (var bufferWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    bufferWriter.write("[]");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("issue when checking if storage file exists");
        }
    }

    @Override
    public Task findById(long taskId) {
        return jsonTasks.stream()
                .filter(jsonTask -> jsonTask.getId() == taskId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Task with id " + taskId + " doesn't exists")).toDomain();
    }

    @Override
    public Task save(Task task) {
        if (task.id() == 0L) {
            task = new Task(nextTaskId, task.description(), task.status(), task.createdAt(), task.updatedAt());
        }
        var jsonTask = JsonTask.fromDomain(task);
        var updateJsonTasks = new ArrayList<>(jsonTasks);
        var finalTask = task;
        updateJsonTasks.removeIf(t -> finalTask.id() == t.getId());
        updateJsonTasks.add(jsonTask);
        writeJsonFile(updateJsonTasks);
        return task;
    }

    private void writeJsonFile(List<JsonTask> jsonTasks) {
        try (var bufferWriter = new BufferedWriter(new FileWriter(FILE_PATH))) {
            var jsonContent = jsonTasks.stream()
                    .map(JsonTask::toJson)
                    .collect(joining(",", "[", "]"));
            bufferWriter.write(jsonContent);
        } catch (IOException e) {
            throw new IllegalStateException("issue when writing in file");
        }
    }
}
