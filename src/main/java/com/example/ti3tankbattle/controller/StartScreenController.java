package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import com.example.ti3tankbattle.model.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StartScreenController {

    @FXML
    private TextField player1Name;

    @FXML
    private TextField player2Name;

    @FXML
    private Button start;

    @FXML
    void startGame(ActionEvent event) throws IOException {
        if (validateName(player1Name) && validateName(player2Name)){
            PlayerData.getInstance().setPlayer1Name(player1Name.getText());
            PlayerData.getInstance().setPlayer2Name(player2Name.getText());
            MainApplication.showWindow("ingame-screen.fxml");
            Stage currentStage = (Stage)player1Name.getScene().getWindow();
            currentStage.hide();
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Warning");
            alert.setContentText("Nombre muy extenso");
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
