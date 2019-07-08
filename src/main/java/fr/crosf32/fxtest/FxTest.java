package fr.crosf32.fxtest;

import fr.crosf32.fxtest.storage.database.DatabaseHandler;
import fr.crosf32.fxtest.sample.Main;

public class FxTest {

    private static DatabaseHandler databaseHandler;

    public FxTest() {
        this.databaseHandler = new DatabaseHandler();

        new Thread(() -> javafx.application.Application.launch(Main.class)).start();
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
