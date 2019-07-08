package fr.crosf32.fxtest;

import fr.crosf32.fxtest.storage.database.DatabaseHandler;

public class SlimForest {

    private static DatabaseHandler databaseHandler;

    public SlimForest() {
        this.databaseHandler = new DatabaseHandler();

        System.out.println("start");

   /*     new Thread(() -> javafx.application.Application.launch(Main.class)).start();*/
    }

    public static DatabaseHandler getDatabaseHandler() {
        return databaseHandler;
    }
}
