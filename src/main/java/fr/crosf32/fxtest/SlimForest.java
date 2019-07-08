package fr.crosf32.fxtest;

import fr.crosf32.fxtest.sample.Main;

public class SlimForest {

    public SlimForest() {
       // this.databaseHandler = new DatabaseHandler();

        System.out.println("start");

        new Thread(() -> javafx.application.Application.launch(Main.class)).start();
    }
}
