package com.github.brice.task_tracker_cli.tasks.infrastructure.file;

import com.github.brice.task_tracker_cli.tasks.infrastructure.dataproviders.file.JSONWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings({"unused", "static-method"})
class JSONWriterTest {
    public static class Alien {
        private final String name;
        private final String planet;

        public Alien(String name, String planet) {
            this.name = name;
            this.planet = planet;
        }

        public String getPlanet() {
            return planet;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void toJSONWithAClass() {
        var writer = new JSONWriter();
        var alien = new Alien("John", "Proxima");
        var json = writer.toJSON(alien);
        var expected1 = """
                {"name": "John", "planet": "Proxima"}\
                """;
        var expected2 = """
                {"planet": "Proxima", "name": "John"}\
                """;
        assertTrue(
                json.equals(expected1) || json.equals(expected2),
                "error: " + json + "\n expects either " + expected1 + " or " + expected2
        );
    }
}