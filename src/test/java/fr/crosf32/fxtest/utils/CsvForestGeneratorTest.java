package fr.crosf32.fxtest.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.ArrayList;
import java.util.List;

class CsvForestGeneratorTest {

    @Test
    void generateCsv() {
        List<String[]> stats = new ArrayList<>();
        stats.add(new String[]{"test", "test2"});
        CsvForestGenerator gene = new CsvForestGenerator();
        assertDoesNotThrow(() -> gene.generateCsv(stats));
    }

    static void assertDoesNotThrow(Executable executable) {
        assertDoesNotThrow(executable, "must not throw");
    }

    static void assertDoesNotThrow(Executable executable, String message) {
        try {
            executable.execute();
        } catch (Throwable err) {
            fail(message);
        }
    }
}

