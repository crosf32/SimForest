package fr.crosf32.fxtest.entity;

public class User {

    private String userName;
    private String password;
    private final int gamePlayed;
    private final int gameWon;

    public User(String userName, String password, int gamePlayed, int gameWon) {
        this.userName = userName;
        this.password = password;
        this.gamePlayed = gamePlayed;
        this.gameWon = gameWon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGameWon() {
        return gameWon;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }
}
