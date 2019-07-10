package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.controller.MainController;
import fr.crosf32.fxtest.sample.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class FxWindowManager {

    public FxWindowManager() {
    }

    public void openMainWindow(int width, int height) {
        FXMLLoader loader = getLoader("/views/main.fxml");
        System.out.println(loader.getClass());
        Scene scene = null;

        try {
            scene = new Scene(loader.load(), 1280, 800);
            System.out.println(scene.getClass());
            MainController mainController = loader.getController();
            mainController.loadGrid(width, height);
            Main.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getLoader(String path) {
        return new FXMLLoader(getClass().getResource(path));
    }
}
