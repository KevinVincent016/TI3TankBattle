package com.example.ti3tankbattle.controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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

    public void saveDataBaseInJson() {

        ArrayList<User> arr = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            arr.add(i,users.get(i));
        }

        try {
            File file = new File("src/main/resources/com/example/ti3tankbattle/database.json");
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(arr);
            fos.write(json.getBytes(StandardCharsets.UTF_8));
            fos.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
