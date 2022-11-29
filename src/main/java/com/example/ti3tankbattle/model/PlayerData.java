package com.example.ti3tankbattle.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlayerData {

    private static PlayerData instance;

    private User currentPlayer1;
    private User currentPlayer2;
    private String winnerName;

    private ObservableList<User> users;

    private PlayerData(){
        users = FXCollections.observableArrayList();
    }

    public static PlayerData getInstance(){
        if(instance == null){
            instance = new PlayerData();
        }
        return instance;
    }

    public User getCurrentPlayer1() {
        return currentPlayer1;
    }

    public void setCurrentPlayer1(User player) {
        currentPlayer1 = player;
    }

    public User getCurrentPlayer2(){
        return currentPlayer2;
    }

    public void setCurrentPlayer2(User player){
        currentPlayer2 = player;
    }

    public String getWinner(){
        return winnerName;
    }

    public void setWinner(String name){
        winnerName = name;
    }

    public ObservableList<User> getUsers(){
        return users;
    }

    public void addPlayer(User player){
        users.add(player);
    }

}
