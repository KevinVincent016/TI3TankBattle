package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FinalScreenController implements Initializable {

    @FXML
    private TableColumn<User, String> nameColum;

    @FXML
    private Button newMatch;

    @FXML
    private TableView<User> scoreView;

    @FXML
    private TableColumn<User, Integer> victoriesColum;

    @FXML
    private Label winnerMessage;

    @FXML
    private Label winnerName;

    @FXML
    void newMatch(ActionEvent event) throws IOException {
        //PlayerData.getInstance().saveDataBaseInJson();

        MainApplication.showWindow("start-screen.fxml");
        Stage currentStage = (Stage)newMatch.getScene().getWindow();
        currentStage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        winnerName.setText(PlayerData.getInstance().getWinner());
        winnerMessage.setText("Felicidades " + PlayerData.getInstance().getWinner() + "," + " demostraste ser un buen tanquista");

        nameColum.setCellValueFactory(new PropertyValueFactory<>("userName"));
        victoriesColum.setCellValueFactory(new PropertyValueFactory<>("wins"));

        scoreView.setItems(PlayerData.getInstance().getUsers());
    }
}