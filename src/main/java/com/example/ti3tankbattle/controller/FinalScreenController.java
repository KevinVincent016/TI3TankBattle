package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
        reproduceSound("src/main/resources/com/example/ti3tankbattle/select.wav");
        MainApplication.showWindow("start-screen.fxml");
        Stage currentStage = (Stage)newMatch.getScene().getWindow();
        currentStage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reproduceSound("src/main/resources/com/example/ti3tankbattle/endmatch.wav");

        winnerName.setText(PlayerData.getInstance().getWinner());
        winnerMessage.setText("Felicidades " + PlayerData.getInstance().getWinner() + "," + " demostraste ser un buen tanquista");

        nameColum.setCellValueFactory(new PropertyValueFactory<>("userName"));
        victoriesColum.setCellValueFactory(new PropertyValueFactory<>("wins"));

        scoreView.setItems(PlayerData.getInstance().getUsers());
    }

    public void reproduceSound(String path){
        File musicPath = new File(path);

        if(musicPath.exists()){

            try {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }
}