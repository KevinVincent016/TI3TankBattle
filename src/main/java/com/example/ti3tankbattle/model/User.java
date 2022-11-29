package com.example.ti3tankbattle.model;

public class User {
    private String userName;
    private int wins;

    public User(){

    }

    public User(String userName, int wins) {
        this.userName = userName;
        this.wins = wins;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}