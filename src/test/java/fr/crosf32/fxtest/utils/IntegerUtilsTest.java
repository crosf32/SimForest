package fr.crosf32.fxtest.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerUtilsTest {

    @Test
    void getSafeInt() {
        assertEquals(IntegerUtils.getSafeInt("test"), -1);
        assertEquals(IntegerUtils.getSafeInt("5"), 5);
    }
}