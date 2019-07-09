package fr.crosf32.fxtest.scenes.frontend;

import fr.crosf32.fxtest.controller.HomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Home {

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontend/index.fxml"));
            Scene scene = new Scene(loader.load(), 1280, 800);
            HomeController indexController = loader.getController();
            System.out.println(indexController);
            return scene;
        } catch(Exception e) {
            e.printStackTrace();
        }
       return null;
    }

}
