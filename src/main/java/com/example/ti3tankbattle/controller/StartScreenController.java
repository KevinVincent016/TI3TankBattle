package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;


public class StartScreenController{

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    void startGame(ActionEvent event) throws IOException {
        if (validateName(player1Name) && validateName(player2Name) && !player1Name.getText().equals(player2Name.getText())){
            if (PlayerData.getInstance().getUsers().isEmpty()){
                User player = new User(player1Name.getText(),0);
                PlayerData.getInstance().addPlayer(player);
                PlayerData.getInstance().setCurrentPlayer1(player);
            }else {
                for (int i = 0; i < PlayerData.getInstance().getUsers().size(); i++) {
                    if (PlayerData.getInstance().getUsers().get(i).getUserName().equals(player1Name.getText())) {
                        PlayerData.getInstance().setCurrentPlayer1(PlayerData.getInstance().getUsers().get(i));
                        break;
                    } else {
                        User player = new User(player1Name.getText(), 0);
                        PlayerData.getInstance().addPlayer(player);
                        PlayerData.getInstance().setCurrentPlayer1(player);
                        break;
                    }
                }
            }
            for (int i = 0; i < PlayerData.getInstance().getUsers().size(); i++) {
                if (PlayerData.getInstance().getUsers().get(i).getUserName().equals(player2Name.getText())){
                    PlayerData.getInstance().setCurrentPlayer2(PlayerData.getInstance().getUsers().get(i));
                    break;
                }else{
                    User player = new User(player2Name.getText(),0);
                    PlayerData.getInstance().setCurrentPlayer2(player);
                    PlayerData.getInstance().addPlayer(player);
                    break;
                }
            }
            MainApplication.showWindow("ingame-screen.fxml");
            Stage currentStage = (Stage)player1Name.getScene().getWindow();
            currentStage.hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Warning");
            alert.setContentText("Nombre muy extenso o Nombre repetido");
            alert.show();
        }
    }

    public boolean validateName(TextField name){
        if (name.getText().length() > 10){
            return false;
        }else{
            return true;
        }
    }
}
