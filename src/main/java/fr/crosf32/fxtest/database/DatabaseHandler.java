package fr.crosf32.fxtest.database;

import fr.crosf32.fxtest.entity.Config;
import fr.crosf32.fxtest.entity.Forest;
import fr.crosf32.fxtest.entity.Vegetal;
import fr.crosf32.fxtest.enums.VegetalState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseHandler {

    private ScheduledExecutorService asyncExecutor = Executors.newScheduledThreadPool(2, new LegendThreadFactory());
    private DatabaseConnector connector;

    public DatabaseHandler(String database, String username, String password) {
        this.connector = new DatabaseConnector(database, username, password);
    }

    public void updateConfig(int num, int delay, int maxTime, int width, int height, Forest f) {
        updateConfigParameters(num, delay, maxTime, width, height);
        destroyCells(num);
        f.getCells().stream().filter(vegetal -> vegetal.getState() != VegetalState.EMPTY).forEach(vegetal -> saveCell(num, vegetal));
    }

    public CompletableFuture<Void> destroyCells(int num) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("DELETE FROM cell WHERE cell.num = ?")) {
                    ps.setInt(1, num);

                    ps.execute();
                }
        } catch(Exception e) {
            e.printStackTrace();
        }
    });
}

    public void saveConfig(int num, int delay, int maxTime, int width, int height, Forest f) {
        saveConfigParameters(delay, maxTime, width, height);
        f.getCells().stream().filter(vegetal -> vegetal.getState() != VegetalState.EMPTY).forEach(vegetal -> saveCell(num, vegetal));
    }

    public CompletableFuture<Void> saveCell(int num, Vegetal cell) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO cell VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, num);
                    ps.setInt(2, cell.getRow());
                    ps.setInt(3, cell.getCol());
                    ps.setInt(4, cell.getState().ordinal());

                    ps.execute();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> saveConfigParameters( int delay, int maxTime, int width, int height) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("INSERT INTO config (delay, maxtime, width, height) VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, delay);
                    ps.setInt(2, maxTime);
                    ps.setInt(3, width);
                    ps.setInt(4, height);
                    ps.execute();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompletableFuture<Void> updateConfigParameters(int num, int delay, int maxTime, int width, int height) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                 PreparedStatement ps = getPreparedConfigStatement(connection, "UPDATE config SET `delay` = ?, `maxtime` = ?, `width` = ?, `height` = ? WHERE config.num = ?", delay, maxTime, width, height, num);
                    if(ps != null) {
                        ps.execute();
                    }
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    };

    private PreparedStatement getPreparedConfigStatement(Connection connection, String sql, int delay, int maxTime, int width, int height, int num) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, delay);
            ps.setInt(2, maxTime);
            ps.setInt(3, width);
            ps.setInt(4, height);
            ps.setInt(5, num);

            return ps;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public CompletableFuture<Optional<Config>> getConfig(int num) {
     return makeFuture(() -> {
         try (Connection connection = connector.getConnection()) {
             try (PreparedStatement ps = connection.prepareStatement("SELECT c.num, c.delay, c.maxtime, c.width, c.height FROM config as c WHERE c.num = ?")) {
                 ps.setInt(1, num);
                 try (ResultSet rs = ps.executeQuery()) {
                     if(rs.next()) {
                         return Optional.of(new Config(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
                     }
                 }
             }
         } catch(Exception e) {
             e.printStackTrace();
         }

         return Optional.empty();
     });
    }

    public CompletableFuture<Optional<List<Vegetal>>> getVegetalsFromConfig(int num) {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("SELECT c.num, c.row, c.col, c.state FROM cell as c WHERE c.num = ?")) {
                    ps.setInt(1, num);
                    try (ResultSet rs = ps.executeQuery()) {
                        List<Vegetal> vegetals = new ArrayList<>();

                        while(rs.next()) {
                            Vegetal veg = new Vegetal(rs.getInt(2), rs.getInt(3)).setState(VegetalState.values()[rs.getInt(4)]);
                            vegetals.add(veg);
                        }
                        return Optional.of(vegetals);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            return Optional.empty();
        });
    }

    public CompletableFuture<Integer> getNumberOfConfigs() {
        return makeFuture(() -> {
            try (Connection connection = connector.getConnection()) {
                try (PreparedStatement ps = connection.prepareStatement("SELECT COUNT(c.num) FROM config as c")) {
                    try (ResultSet rs = ps.executeQuery()) {
                        if(rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }

            return 0;
        });
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
