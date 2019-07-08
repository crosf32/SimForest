package fr.crosf32.fxtest.scenes.frontend;

import fr.crosf32.fxtest.controllers.IndexController;
import fr.crosf32.fxtest.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class Home {

    public Scene getScene(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/frontend/index.fxml"));
            Scene scene = new Scene(loader.load(), 800, 500);
            IndexController indexController = loader.getController();
            System.out.println(indexController);
            indexController.setTest(user.getUserName());
            indexController.setGamePlayed(user.getGamePlayed());
            indexController.setGameWon(user.getGameWon());
            indexController.loadGrid();
            System.out.println("lol");
            return scene;
        } catch(Exception e) {
            e.printStackTrace();
        }
       return null;
    }

}
