package com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JSONReaderTest {

    public static final class Car {
        private String owner;
        private String color;

        public Car(){}

        public Car(String owner, String color) {
            this.owner = owner;
            this.color = color;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Car car)) return false;
            return Objects.equals(owner, car.owner) && Objects.equals(color, car.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(owner, color);
        }
    }

    @Test
    public void parseJSONListOfCar() throws NoSuchFieldException {
        var listOfCar = new Object(){
            List<Car> cars;
        }.getClass().getDeclaredField("cars").getGenericType();

        var reader = new JSONReader();
        reader.addTypeMatcher(listTypeMatcher());
        var cars = reader.parseJSON("""
                [
                    {"owner": "Bob", "color": "red"},
                    {"owner": "Ana", "color": "blue"}
                ]
                """, listOfCar);
        assertEquals(List.of(new Car("Bob", "red"), new Car("Ana", "blue")), cars);
    }

    private static JSONReader.TypeMatcher listTypeMatcher() {
        return type -> Optional.of(type)
                .flatMap(t -> t instanceof ParameterizedType parameterizedType ? Optional.of(parameterizedType): Optional.empty())
                .filter(t -> t.getRawType() == List.class)
                .map(t -> JSONReader.ObjectBuilder.list(t.getActualTypeArguments()[0]));
    }

}