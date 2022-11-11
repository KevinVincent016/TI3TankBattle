package com.example.ti3tankbattle;

import com.example.ti3tankbattle.model.Avatar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private boolean leftressed = false;
    private boolean downPressed = false;
    private boolean rightPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);

        player1 = new Avatar(canvas,HelloApplication.class.getResource("Jugador1.png").getPath(),100,100);
        player2 = new Avatar(canvas,HelloApplication.class.getResource("Jugador2.png").getPath(),400,400);

        draw();
    }

    public void onKeyReleased(KeyEvent keyEvent){
        //Player 1
        if (keyEvent.getCode() == KeyCode.W){
            wPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.A){
            aPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.S){
            sPressed = false;
        }
        if (keyEvent.getCode() == KeyCode.D){
            dPressed = false;
        }
    }

    public void onKeyPressed(KeyEvent keyEvent){
        //Player 1
        if (keyEvent.getCode() == KeyCode.W){
            wPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.A){
            aPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.S){
            sPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.D){
            dPressed = true;
        }
    }

    public void draw(){
        new Thread(
                ()->{
                    while (isRunning){
                        Platform.runLater(()->{
                            gc.setFill(Color.GREY);
                            gc.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
                            player1.draw();
                            player2.draw();

                            //Player 1 movement
                            if (wPressed){
                                player1.moveForward();
                            }
                            if (aPressed){
                                player1.changeAngle(-5);
                            }
                            if (sPressed){
                                player1.moveBackward();
                            }
                            if (dPressed){
                                player1.changeAngle(5);
                            }

                            //Player 2 movement

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