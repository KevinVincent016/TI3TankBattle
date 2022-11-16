package com.example.ti3tankbattle;

import com.example.ti3tankbattle.model.Avatar;
import com.example.ti3tankbattle.model.Bullet;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;

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
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);
        canvas.setOnMouseClicked(this::onMouseClicked);

        enemies = new ArrayList<>();
        enemies.add(new Avatar(canvas, HelloApplication.class.getResource("JugadorBOT.png").getPath(), 449, 70));

        player1Bullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        botBullets = new ArrayList<>();

        player1 = new Avatar(canvas, HelloApplication.class.getResource("Jugador1.png").getPath(), 33, 194);
        player2 = new Avatar(canvas, HelloApplication.class.getResource("Jugador2.png").getPath(), 538, 192);

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
            player1Bullets.add(new Bullet(canvas, (int)player1.getPosition().x, (int)player1.getPosition().y, HelloApplication.class.getResource("bullet.png").getPath()));
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
            player2Bullets.add(new Bullet(canvas, (int)player2.getPosition().x, (int)player2.getPosition().y, HelloApplication.class.getResource("bullet.png").getPath()));
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
                                if (player1Bullets.get(i).x > canvas.getWidth()+20 || player1Bullets.get(i).y > canvas.getHeight()+20 || player1Bullets.get(i).x < -20 || player1Bullets.get(i).y < -20){
                                    player1Bullets.remove(i);
                                }
                            }

                            for (int i = 0; i < player2Bullets.size(); i++) {
                                player2Bullets.get(i).draw();
                                if (player2Bullets.get(i).x > canvas.getWidth()+20 || player2Bullets.get(i).y > canvas.getHeight()+20 || player2Bullets.get(i).x < -20 || player2Bullets.get(i).y < -20){
                                    player2Bullets.remove(i);
                                }
                            }

                            //Player 1 movement
                            if (wPressed) {
                                player1.moveForward();
                            }
                            if (aPressed) {
                                player1.changeAngle(-5);
                            }
                            if (sPressed) {
                                player1.moveBackward();
                            }
                            if (dPressed) {
                                player1.changeAngle(5);
                            }

                            //Player 2 movement
                            if (upPressed) {
                                player2.moveForward();
                            }
                            if (leftPressed) {
                                player2.changeAngle(-5);
                            }
                            if (downPressed) {
                                player2.moveBackward();
                            }
                            if (rightPressed) {
                                player2.changeAngle(5);
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

}