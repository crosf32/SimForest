package fr.crosf32.fxtest.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindowDialogUtilsTest {

    @Test
    void getConnexionDialog() {
        assertNotNull(WindowDialogUtils.getConnexionDialog());
    }

    @Test
    void getConfigNumberDialog() {
        assertNotNull(WindowDialogUtils.getConfigNumberDialog(3));
    }
}