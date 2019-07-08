package fr.crosf32.fxtest.storage.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import fr.crosf32.fxtest.entity.Article;
import fr.crosf32.fxtest.entity.User;

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

    public CompletableFuture<Optional<User>> getUser(String userName) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("SELECT `password`, `game_played`, `game_won` FROM `user` WHERE `username` = ?")) {
                    ps.setString(1, userName);

                    try (ResultSet rs = ps.executeQuery()) {
                        if(rs.next()) {
                            String pass = rs.getString("password");
                            int gamePlayed = rs.getInt("game_played");
                            int gameWon = rs.getInt("game_won");

                            return Optional.of(new User(userName, pass, gamePlayed, gameWon));
                        } else {
                            System.out.println("NULL x)");
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return Optional.empty();
        });
    }

    public CompletableFuture<Optional<List<Article>>> getArticles() {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM `article`")) {

                    try (ResultSet rs = ps.executeQuery()) {

                        List<Article> articles = new ArrayList<>();

                        while(rs.next()) {
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            int price = rs.getInt("price");

                            articles.add(new Article(id, name, price));
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
