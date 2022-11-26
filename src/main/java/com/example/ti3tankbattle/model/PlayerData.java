package com.example.ti3tankbattle.model;

public class PlayerData {

    private static PlayerData instance;

    private String player1Name;
    private String player2Name;

    private PlayerData(){
    }

    public static PlayerData getInstance(){
        if(instance == null){
            instance = new PlayerData();
        }
        return instance;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String name) {
        player1Name = name;
    }

    public String getPlayer2Name(){
        return player2Name;
    }

    public void setPlayer2Name(String name){
        player2Name = name;
    }

}
