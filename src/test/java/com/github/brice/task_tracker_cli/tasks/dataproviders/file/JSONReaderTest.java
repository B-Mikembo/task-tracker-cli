package com.github.brice.task_tracker_cli.tasks.dataproviders.file;

import com.github.brice.task_tracker_cli.tasks.repositories.file.JSONReader;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {
    @Test
    void parseJSONTypeReference() {
        var reader = new JSONReader();
        reader.addTypeMatcher(listTypeMatcher());
        var list = reader.parseJSON("""
                [
                    1,5,78,4
                ]
                """, new JSONReader.TypeReference<List<Integer>>() {
        });
        assertEquals(List.of(1, 5, 78, 4), list);
    }

    private static JSONReader.TypeMatcher listTypeMatcher() {
        return type -> Optional.of(type)
                .flatMap(t -> t instanceof ParameterizedType parameterizedType ? Optional.of(parameterizedType) : Optional.empty())
                .filter(t -> t.getRawType() == List.class)
                .map(t -> JSONReader.ObjectBuilder.list(t.getActualTypeArguments()[0]));
    }

    @Test
    void parseJSONTypeReferencePrecondition() {
        var reader = new JSONReader();
        assertAll(
                () -> assertThrows(NullPointerException.class, () -> reader.parseJSON(null, new JSONReader.TypeReference<String>() {
                })),
                () -> assertThrows(NullPointerException.class, () -> reader.parseJSON("", (JSONReader.TypeReference<?>) null))
        );
    }

    @SuppressWarnings("unused")
    public static final class Car {
        private String color;
        private String owner;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Car car)) return false;
            return Objects.equals(color, car.color) && Objects.equals(owner, car.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, owner);
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public Car() {
        }

        public Car(String owner, String color) {
            this.owner = owner;
            this.color = color;
        }
    }

    @Test
    void parseJSONListOfCar() {
        var reader = new JSONReader();
        reader.addTypeMatcher(listTypeMatcher());
        var cars = reader.parseJSON("""
                [
                    {"owner": "Bob", "color": "blue"},
                    {"owner": "Ana", "color": "black"}
                ]
                """, new JSONReader.TypeReference<List<Car>>() {
        });
        assertEquals(List.of(new Car("Bob", "blue"), new Car("Ana", "black")), cars);
    }

    @Test
    void addTypeMatcherPreconditions() {
        var reader = new JSONReader();
        assertThrows(NullPointerException.class, () -> reader.addTypeMatcher(null));
    }
}