package fr.crosf32.fxtest.storage.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private Connection connection;
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public DatabaseConnector() {
        connect();
    }

    public void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        try {
            return !getConnection().isClosed();
        } catch(SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void connect() {
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/fxtest?useSSL=false&autoReconnect=true&useUnicode=yes" );
        config.setUsername( "root" );
        config.setPassword( "" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );

        /*try {
            Class.forName("com.mysql.jdbc.Driver");
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ());

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fxtest?useSSL=false&autoReconnect=true&useUnicode=yes", "root", "");

            System.out.println("La connexion a bien été établie");
        } catch(Exception e) {
            e.printStackTrace();
        }*/
    }

    public Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
