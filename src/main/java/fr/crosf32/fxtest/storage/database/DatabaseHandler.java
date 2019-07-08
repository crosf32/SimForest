package fr.crosf32.fxtest.storage.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHandler {

    private ScheduledExecutorService asyncExecutor = Executors.newScheduledThreadPool(2, new LegendThreadFactory());
    private DatabaseConnector connector;

    public DatabaseHandler() {
        this.connector = new DatabaseConnector();
    }

    private <T> CompletableFuture<T> makeFuture(Callable<T> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return supplier.call();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        }, asyncExecutor);
    }

    private CompletableFuture<Void> makeFuture(ThrowingRunnable r) {
        return CompletableFuture.runAsync(() -> {
            try {
                r.run();
            } catch (Exception e) {
                if (e instanceof RuntimeException) {
                    throw (RuntimeException) e;
                }
                throw new CompletionException(e);
            }
        }, asyncExecutor);
    }

    public CompletableFuture<Void> addGamePlayed(String userName, int gamePlayed) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET `game_played` = ? WHERE `username` = ?")) {
                    ps.setInt(1, gamePlayed);
                    ps.setString(2, userName);

                    ps.executeUpdate();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> addGameWon(String userName, int gameWon) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("UPDATE `user` SET `game_won` = ? WHERE `username` = ?")) {
                    ps.setInt(1, gameWon);
                    ps.setString(2, userName);

                    ps.executeUpdate();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    private class LegendThreadFactory implements ThreadFactory {

        private AtomicInteger count = new AtomicInteger();

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "fr.crosf32.fxtest.entity.database-" + count.getAndIncrement());
        }
    }

    public interface ThrowingRunnable {

        void run() throws Exception;

    }

    public DatabaseConnector getConnector() {
        return connector;
    }
}
