package fr.crosf32.fxtest.handler;

import fr.crosf32.fxtest.SlimForest;
import fr.crosf32.fxtest.controller.MainController;
import fr.crosf32.fxtest.entity.Config;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.sample.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.util.List;

public class FxWindowManager {

    private SlimForest simForest;

    public FxWindowManager() {
        simForest = SlimForest.getInstance();
    }

    public void openMenu() {
        FXMLLoader loader = getLoader("/views/home.fxml");
        Scene scene = getScene(loader);

        Main.getPrimaryStage().setScene(scene);
    }

    public void openMainWindow(int width, int height, boolean random) {
        FXMLLoader loader = getLoader("/views/main.fxml");
        Scene scene = getScene(loader);

        MainController mainController = loader.getController();
        mainController.loadGrid(width, height, random);
        Main.getPrimaryStage().setScene(scene);
    }

    public void openMainWindowFromDatabase(int num) {
        simForest.loadConfig(num);
        Config c = simForest.getLoadedConfig();
        List<Vegetal> vegetals = simForest.getVegetalsFromConfig(num);

        FXMLLoader loader = getLoader("/views/main.fxml");
        Scene scene = getScene(loader);

        MainController mainController = loader.getController();
        mainController.loadGridFromDatabase(c, vegetals);

        Main.getPrimaryStage().setScene(scene);
    }

    private Scene getScene(FXMLLoader loader) {
        Scene scene = null;

        try {
            scene = new Scene(loader.load(), 1280, 800);
            String css = getClass().getResource("/css/main.css").toExternalForm();
            scene.getStylesheets().add(css);
        } catch(Exception e) {
            System.out.println("Le fichier n'a pas été trouvé ou n'est pas utilisable.");
            return null;
        }

        return scene;
    }

    private FXMLLoader getLoader(String path) {
        return new FXMLLoader(getClass().getResource(path));
    }
}
