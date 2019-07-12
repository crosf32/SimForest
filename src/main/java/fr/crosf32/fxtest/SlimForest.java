package fr.crosf32.fxtest;

import fr.crosf32.fxtest.database.DatabaseHandler;
import fr.crosf32.fxtest.entity.Config;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.handler.FxWindowManager;
import fr.crosf32.fxtest.sample.Main;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class SlimForest {

    private static SlimForest instance;

    private FxWindowManager fxWindowManager;

    private static DatabaseHandler handler;

    private Config loadedConfig;

    public SlimForest() {
        instance = this;

        new Thread(() -> javafx.application.Application.launch(Main.class)).start();
        fxWindowManager = new FxWindowManager();
    }

    public void loadConfig(int num) {
        try {
            handler.getConfig(num).get().ifPresent(config -> this.loadedConfig = config);
        } catch (Exception e) {
            System.out.println("La config n'a pas été trouvé");
            e.printStackTrace();
        }
    }

    public void saveNewConfig(int delay, int maxTime, int width, int height, Forest f) {
        try {
            int num = (handler.getNumberOfConfigs().get()+1);
            handler.saveConfig(num, delay, maxTime, width, height, f);
        } catch(Exception e) {
            System.out.println("Error save new config");
        }
    }

    public void saveConfig(int num, int delay, int maxTime, int width, int height, Forest f) {
        handler.updateConfig(num, delay, maxTime, width, height, f);
    }

    public List<Vegetal> getVegetalsFromConfig(int num) {
        try {
            return handler.getVegetalsFromConfig(num).get().get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Impossible de récupérer les différentes trucs");
            e.printStackTrace();
        }

        return null;
    }

    public DatabaseHandler loadDatabase(String database, String username, String password) {
        DatabaseHandler databaseHandler = new DatabaseHandler(database, username, password);
        setHandler(databaseHandler);
        return databaseHandler;
    }

    public FxWindowManager getFxWindowManager() {
        return fxWindowManager;
    }

    public static SlimForest getInstance() {
        return instance;
    }

    public static DatabaseHandler getHandler() {
        return handler;
    }

    public void setHandler(DatabaseHandler handler) {
        this.handler = handler;
    }

    public Config getLoadedConfig() {
        return loadedConfig;
    }
}
