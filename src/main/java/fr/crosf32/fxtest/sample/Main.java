package fr.crosf32.fxtest.sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));

        Scene scene = new Scene(root);

        String css = getClass().getResource("/css/main.css").toExternalForm();
        System.out.println(css);
        scene.getStylesheets().add(css);

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));

        primaryStage.setTitle("SimForest");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
