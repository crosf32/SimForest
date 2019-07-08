package fr.crosf32.fxtest.scenes.frontend;

import fr.crosf32.fxtest.controller.IndexController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Home {

    public Scene getScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontend/index.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            IndexController indexController = loader.getController();
            System.out.println(indexController);
            indexController.loadGrid();
            return scene;
        } catch(Exception e) {
            e.printStackTrace();
        }
       return null;
    }

}
