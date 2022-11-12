package com.example.ti3tankbattle;

import com.example.ti3tankbattle.model.Avatar;
import com.example.ti3tankbattle.model.Vector;
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
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;

    private GraphicsContext gc;

    private boolean isRunning = true;

    private Avatar player1;

    private Avatar player2;

    private Avatar bot;

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

        player1 = new Avatar(canvas, HelloApplication.class.getResource("Jugador1.png").getPath(), 33, 194);
        player2 = new Avatar(canvas, HelloApplication.class.getResource("Jugador2.png").getPath(), 538, 192);
        bot = new Avatar(canvas, HelloApplication.class.getResource("JugadorBOT.png").getPath(), 450, 71);

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

    /*
    public void drawBot(){
        bot.changeAngle(45);
        new Thread(
                () -> {
                    while (bot.getPosition().x == 450.0 && bot.getPosition().y != 297.2741699796952) {
                        Platform.runLater(() -> {
                            bot.draw();
                            bot.moveForward();
                            System.out.println(bot.getPosition().x);
                            System.out.println(bot.getPosition().y);
                            if (bot.getPosition().y == 297.2741699796952){
                                bot.changeAngle(90);
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    while (bot.getPosition().x != 174.22835533724646 && bot.getPosition().y == 297.2741699796952) {
                        Platform.runLater(() -> {
                            bot.draw();
                            bot.moveForward();
                            if (bot.getPosition().x == 174.22835533724646){
                                bot.changeAngle(90);
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    while (bot.getPosition().x == 174.22835533724646 && bot.getPosition().y != 71.0) {
                        Platform.runLater(() -> {
                            bot.draw();
                            bot.moveForward();
                            if (bot.getPosition().y == 71.0){
                                bot.changeAngle(90);
                            }
                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    while (bot.getPosition().x != 450.0 && bot.getPosition().y == 71.0) {
                        Platform.runLater(() -> {
                            bot.draw();
                            bot.moveForward();
                            System.out.println(bot.getPosition().x);
                            if (bot.getPosition().x == 450.0){
                                bot.changeAngle(90);
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
     */

    public void drawBot(){
        bot.changeAngle(45);
        final boolean[] flag = {true};
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {
                            bot.draw();
                            bot.moveForward();

                            if (bot.getPosition().y == 297.2741699796952){
                                bot.changeAngle(90);
                            }else if (bot.getPosition().x == 174.22835533724646){
                                bot.changeAngle(90);
                            }else if (bot.getPosition().y == 71.0){
                                bot.changeAngle(90);
                            }else if (bot.getPosition().x == 450.0){
                                bot.changeAngle(90);
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