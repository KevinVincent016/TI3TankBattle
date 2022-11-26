package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import com.example.ti3tankbattle.model.Avatar;
import com.example.ti3tankbattle.model.Bullet;
import com.example.ti3tankbattle.model.PlayerData;
import com.example.ti3tankbattle.model.Vector;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InGameController implements Initializable {

    @FXML
    private Canvas canvas;

    @FXML
    private Label P1name;

    @FXML
    private Label P2name;

    @FXML
    private HBox botLifes;
    @FXML
    private ImageView botLife1;
    @FXML
    private ImageView botLife2;
    @FXML
    private ImageView botLife3;
    @FXML
    private ImageView botLife4;
    @FXML
    private ImageView botLife5;


    @FXML
    private HBox p3Bullets;
    @FXML
    private ImageView p3Bullet1;
    @FXML
    private ImageView p3Bullet2;
    @FXML
    private ImageView p3Bullet3;
    @FXML
    private ImageView p3Bullet4;
    @FXML
    private ImageView p3Bullet5;
    @FXML
    private ImageView p3Bullet6;


    @FXML
    private HBox p1Lifes;
    @FXML
    private ImageView p1Life1;
    @FXML
    private ImageView p1Life2;
    @FXML
    private ImageView p1Life3;
    @FXML
    private ImageView p1Life4;
    @FXML
    private ImageView p1Life5;


    @FXML
    private HBox p1Bullets;
    @FXML
    private ImageView p1Bullet1;
    @FXML
    private ImageView p1Bullet2;
    @FXML
    private ImageView p1Bullet3;
    @FXML
    private ImageView p1Bullet4;
    @FXML
    private ImageView p1Bullet5;
    @FXML
    private ImageView p1Bullet6;


    @FXML
    private HBox p2Lifes;
    @FXML
    private ImageView p2Life1;
    @FXML
    private ImageView p2Life2;
    @FXML
    private ImageView p2Life3;
    @FXML
    private ImageView p2Life4;
    @FXML
    private ImageView p2Life5;


    @FXML
    private HBox p2Bullets;
    @FXML
    private ImageView p2Bullet1;
    @FXML
    private ImageView p2Bullet2;
    @FXML
    private ImageView p2Bullet3;
    @FXML
    private ImageView p2Bullet4;
    @FXML
    private ImageView p2Bullet5;
    @FXML
    private ImageView p2Bullet6;



    private GraphicsContext gc;

    private boolean isRunning = true;

    private Avatar player1;

    private Avatar player2;

    private ArrayList<Avatar> enemies;

    private ArrayList<Bullet> player1Bullets;
    private ArrayList<Bullet> player2Bullets;
    private ArrayList<Bullet> botBullets;

    //Estados de teclas
    //Jugador 1
    private boolean wPressed = false;
    private boolean aPressed = false;
    private boolean sPressed = false;
    private boolean dPressed = false;

    //Jugador 2
    private boolean upPressed = false;
    private boolean leftPressed = false;
    private boolean downPressed = false;
    private boolean rightPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        P1name.setText(PlayerData.getInstance().getPlayer1Name());
        P2name.setText(PlayerData.getInstance().getPlayer2Name());

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMouseClicked(this::onMouseClicked);

        enemies = new ArrayList<>();
        enemies.add(new Avatar(canvas, MainApplication.class.getResource("JugadorBOT.png").getPath(), 449, 70));

        player1Bullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        botBullets = new ArrayList<>();

        player1 = new Avatar(canvas, MainApplication.class.getResource("Jugador1.png").getPath(), 33, 194);
        player2 = new Avatar(canvas, MainApplication.class.getResource("Jugador2.png").getPath(), 538, 192);

        draw();
        drawBot();
    }

    private void onMouseClicked(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getX() + " " + mouseEvent.getY());
    }

    public void onKeyReleased(KeyEvent keyEvent) {
        //Player 1
        if (keyEvent.getCode() == KeyCode.W) {
            wPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.A) {
            aPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.S) {
            sPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.D) {
            dPressed = false;
        }

        //Player 2
        if (keyEvent.getCode() == KeyCode.UP) {
            upPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.LEFT) {
            leftPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.DOWN) {
            downPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            rightPressed = false;
        }
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        //Player 1
        if (keyEvent.getCode() == KeyCode.W) {
            wPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.A) {
            aPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.S) {
            sPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.D) {
            dPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.SPACE){
            if (checkAmmo(p1Bullets)){
                player1Bullets.add(new Bullet(canvas, new Vector ((int)player1.getPosition().x,(int)player1.getPosition().y), new Vector(2*player1.getDirection().x, 2*player1.getDirection().y), MainApplication.class.getResource("bullet.png").getPath()));
                shoot(p1Bullets);
            }
        }
        if (keyEvent.getCode() == KeyCode.R){
            reload(p1Bullets);
        }

        //Player 2
        if (keyEvent.getCode() == KeyCode.UP) {
            upPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.DOWN) {
            downPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            rightPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.K){
            if (checkAmmo(p2Bullets)){
                player2Bullets.add(new Bullet(canvas, new Vector ((int)player2.getPosition().x,(int)player2.getPosition().y), new Vector(2*player2.getDirection().x, 2*player2.getDirection().y), MainApplication.class.getResource("bullet.png").getPath()));
                shoot(p2Bullets);
            }
        }
        if (keyEvent.getCode() == KeyCode.J){
            reload(p2Bullets);
        }
    }

    public void draw() {
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {
                            gc.setFill(Color.GREY);
                            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            player1.draw();
                            player2.draw();
                            for (int i = 0; i < enemies.size(); i++) {
                                enemies.get(i).draw();
                            }

                            for (int i = 0; i < player1Bullets.size(); i++) {
                                player1Bullets.get(i).draw();
                                if (player1Bullets.get(i).pos.x > canvas.getWidth()+20 || player1Bullets.get(i).pos.y > canvas.getHeight()+20 || player1Bullets.get(i).pos.x < -20 || player1Bullets.get(i).pos.y < -20){
                                    player1Bullets.remove(i);
                                }
                            }
                            detectColition();

                            for (int i = 0; i < player2Bullets.size(); i++) {
                                player2Bullets.get(i).draw();
                                if (player2Bullets.get(i).pos.x > canvas.getWidth()+20 || player2Bullets.get(i).pos.y > canvas.getHeight()+20 || player2Bullets.get(i).pos.x < -20 || player2Bullets.get(i).pos.y < -20){
                                    player2Bullets.remove(i);
                                }
                            }
                            detectColition();

                            //Player 1 movement
                            if (wPressed) {
                                player1.moveForward();
                            }
                            if (aPressed) {
                                player1.changeAngle(-3);
                            }
                            if (sPressed) {
                                player1.moveBackward();
                            }
                            if (dPressed) {
                                player1.changeAngle(3);
                            }

                            //Player 2 movement
                            if (upPressed) {
                                player2.moveForward();
                            }
                            if (leftPressed) {
                                player2.changeAngle(-3);
                            }
                            if (downPressed) {
                                player2.moveBackward();
                            }
                            if (rightPressed) {
                                player2.changeAngle(3);
                            }

                        });
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void drawBot(){
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).changeAngle(45);
        }

        final boolean[] flag = {true};
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {
                            for (int i = 0; i < enemies.size(); i++) {

                                Avatar bot = enemies.get(i);

                                bot.moveForward();

                                if (bot.getPosition().y >= 297){
                                    bot.getPosition().y = 296;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().x <= 174){
                                    bot.getPosition().x = 175;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().y <= 71){
                                    bot.getPosition().y = 72;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().x >= 450){
                                    bot.getPosition().x = 449;
                                    bot.changeAngle(90);
                                }

                            }

                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void checkMatch(){
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {

                            if (checkLife(botLifes)){
                                try {
                                    isRunning = false;
                                    MainApplication.showWindow("final-screen.fxml");
                                    Stage currentStage = (Stage)P1name.getScene().getWindow();
                                    currentStage.hide();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }

                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }

    public void shoot(HBox player){
        for(Node node : player.getChildren()){
            ImageView bullet = (ImageView) node;
            if (bullet.isVisible()){
                bullet.setVisible(false);
                break;
            }
        }
    }

    public boolean checkAmmo(HBox player){
        int count = 0;
        for(Node node : player.getChildren()){
            ImageView bullet = (ImageView) node;
            if (!bullet.isVisible()){
                count ++;
            }
        }
        if (count == 6){
            return false;    //False = no ammo
        }else{
            return true;   //True = still have ammo
        }
    }

    public void reload(HBox player){
        for (Node node : player.getChildren()){
            ImageView bullet = (ImageView) node;
            if (!bullet.isVisible()){
                bullet.setVisible(true);
            }
        }
    }

    public void detectColition(){
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < player1Bullets.size(); j++) {
                Bullet b = player1Bullets.get(j);
                Avatar e = enemies.get(i);

                double c1 = b.pos.x - e.getPosition().x;
                double c2 = b.pos.y - e.getPosition().y;
                double distance = Math.sqrt(Math.pow(c1,2) + Math.pow(c2,2));

                if (distance < 25){
                    player1Bullets.remove(j);
                    for(Node node : botLifes.getChildren()){
                        ImageView life = (ImageView) node;
                        if (life.isVisible()){
                            life.setVisible(false);
                            break;
                        }
                    }

                    int count = 0;
                    for(Node node : botLifes.getChildren()){
                        ImageView life = (ImageView) node;
                        if (!life.isVisible()){
                            count ++;
                        }
                    }
                    if (count == 5){
                        enemies.remove(i);
                    }
                    return;
                }
            }
        }
    }

    public boolean checkLife(HBox player) {
        int count = 0;
        for (Node node : player.getChildren()) {
            ImageView life = (ImageView) node;
            if (!life.isVisible()) {
                count++;
            }
        }
        if (count == 5) {
            return false;   //no HP
        } else {
            return true;    //Still have HP
        }
    }

}