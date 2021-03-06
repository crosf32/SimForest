package fr.crosf32.fxtest.sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(event -> System.exit(0));

        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));

        Scene scene = new Scene(root);

        String css = getClass().getResource("/css/main.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icons/app_icon.png")));

        primaryStage.setTitle("SimForest");

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    static public Stage getPrimaryStage() {
        return Main.primaryStage;
    }
}
