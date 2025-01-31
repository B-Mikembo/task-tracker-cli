package com.github.brice.task_tracker_cli.tasks.domain.entities;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.github.brice.task_tracker_cli.tasks.domain.entities.TaskStatus.TODO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {
    @Nested
    class DescriptionConstructor {
        @Test
        void givenBuyMilkDescription_whenCreateNewInstanceTask_thenNewTaskDescriptionIsBuyMilk() {
            var expectedDescription = "Buy milk";
            var actualTask = new Task(expectedDescription);
            assertEquals(expectedDescription, actualTask.description());
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void givenEmptyDescription_whenCreateNewInstanceTask_thenThrowIllegalArgumentException(String description) {
            assertThrows(IllegalArgumentException.class, () -> new Task(description));
        }

        @ParameterizedTest
        @NullSource
        void givenNullDescription_whenCreateNewInstanceTask_thenThrowNullPointerException(String description) {
            assertThrows(NullPointerException.class, () -> new Task(description));
        }
    }

    @Nested
    class AllArgsConstructor {
        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void givenEmptyOrBlankId_whenCreateNewInstance_thenThrowIllegalArgumentException(String id) {
            assertThrows(IllegalArgumentException.class, () -> new Task(UUID.fromString(id), "description", TODO, LocalDateTime.now(), LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullSource
        void givenNullId_whenCreateNewTaskInstance_thenThrowNullPointerException(UUID id){
            assertThrows(NullPointerException.class, () -> new Task(id, "description", TODO, LocalDateTime.now(), LocalDateTime.now()));
        }

        @ParameterizedTest
        @EmptySource
        @ValueSource(strings = {" ", "\t", "\n"})
        void givenEmptyOrBlankDescription_whenCreateNewInstance_thenThrowIllegalArgumentException(String description) {
            assertThrows(IllegalArgumentException.class, () -> new Task(UUID.randomUUID(), description, TODO, LocalDateTime.now(), LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullSource
        void givenNullDescription_whenCreateNewTaskInstance_thenThrowNullPointerException(String description){
            assertThrows(NullPointerException.class, () -> new Task(UUID.randomUUID(), description, TODO, LocalDateTime.now(), LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullSource
        void givenNullStatus_whenCreateNewTaskInstance_thenThrowNullPointerException(TaskStatus status){
            assertThrows(NullPointerException.class, () -> new Task(UUID.randomUUID(), "description", status, LocalDateTime.now(), LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullSource
        void givenNullCreatedAt_whenCreateNewTaskInstance_thenThrowNullPointerException(LocalDateTime createdAt){
            assertThrows(NullPointerException.class, () -> new Task(UUID.randomUUID(), "description", TODO, createdAt, LocalDateTime.now()));
        }

        @ParameterizedTest
        @NullSource
        void givenNullUpdatedAt_whenCreateNewTaskInstance_thenThrowNullPointerException(LocalDateTime updatedAt){
            assertThrows(NullPointerException.class, () -> new Task(UUID.randomUUID(), "description", TODO, LocalDateTime.now(), updatedAt));
        }
    }
}