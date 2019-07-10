package fr.crosf32.fxtest.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHandler {

    private ScheduledExecutorService asyncExecutor = Executors.newScheduledThreadPool(2, new LegendThreadFactory());
    private DatabaseConnector connector;

    public DatabaseHandler(String database, String username, String password) {
        this.connector = new DatabaseConnector(database, username, password);
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

   /* public CompletableFuture<Optional<HashMap<Integer, >>> getArticles() {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM `article`")) {

                    try (ResultSet rs = ps.executeQuery()) {

                        List<Order> articles = new ArrayList<>();

                        while(rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            int price = rs.getInt("price");

                            articles.add(new Order());
                        }
                        rs.close();
                        return Optional.of(articles);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
                return Optional.empty();
            }
        });
    }*/


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
