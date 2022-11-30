package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;


public class StartScreenController implements Initializable {

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

            reproduceSound("src/main/resources/com/example/ti3tankbattle/select.wav");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reproduceSound("src/main/resources/com/example/ti3tankbattle/backgroundsound.wav");
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
