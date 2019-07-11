package fr.crosf32.fxtest.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private Connection connection;
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public DatabaseConnector(String database, String username, String password) {
        connect(database, username, password);
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

    private void connect(String database, String username, String password) {
        try {
            config.setJdbcUrl( "jdbc:mysql://localhost:3306/" +  database + "?useSSL=false&autoReconnect=true&useUnicode=yes" );
            config.setUsername( username );
            config.setPassword( password );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            ds = new HikariDataSource( config );
        } catch(Exception e) {
            System.err.println("Aucune connexion à la base de donnée possible");
        }


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
