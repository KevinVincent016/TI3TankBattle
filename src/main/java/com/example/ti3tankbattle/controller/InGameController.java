package com.example.ti3tankbattle.controller;

import com.example.ti3tankbattle.MainApplication;
import com.example.ti3tankbattle.model.*;
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
    private HBox p3Bullets;


    @FXML
    private HBox p1Lifes;

    @FXML
    private HBox p1Bullets;


    @FXML
    private HBox p2Lifes;

    @FXML
    private HBox p2Bullets;

    private GraphicsContext gc;

    private boolean isRunning = true;

    private Avatar player1;

    private Avatar player2;

    private ArrayList<Avatar> enemies;
    private ArrayList<Wall> walls;
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
        P1name.setText(PlayerData.getInstance().getCurrentPlayer1().getUserName());
        P2name.setText(PlayerData.getInstance().getCurrentPlayer2().getUserName());

        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);

        enemies = new ArrayList<>();
        enemies.add(new Avatar(canvas, MainApplication.class.getResource("JugadorBOT.png").getPath(), 449, 70));

        player1Bullets = new ArrayList<>();
        player2Bullets = new ArrayList<>();
        botBullets = new ArrayList<>();
        walls = new ArrayList<>();

        player1 = new Avatar(canvas, MainApplication.class.getResource("Jugador1.png").getPath(), 33, 194);
        player2 = new Avatar(canvas, MainApplication.class.getResource("Jugador2.png").getPath(), 538, 192);
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 100, 80));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 100, 130));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 100, 180));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 450, 80));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 450, 130));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 450, 180));
        walls.add(new Wall(canvas, MainApplication.class.getResource("wall1.png").getPath(), 280, 130));

        draw();
        drawBot();
        checkMatch();
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
        if (keyEvent.getCode() == KeyCode.SPACE) {
            if (checkAmmo(p1Bullets) && checkLife(p1Lifes)) {
                player1Bullets.add(new Bullet(canvas, new Vector((int) player1.getPosition().x, (int) player1.getPosition().y), new Vector(2 * player1.getDirection().x, 2 * player1.getDirection().y), MainApplication.class.getResource("bullet.png").getPath()));
                shoot(p1Bullets);
            }
        }
        if (keyEvent.getCode() == KeyCode.R) {
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
        if (keyEvent.getCode() == KeyCode.K) {
            if (checkAmmo(p2Bullets) && checkLife(p2Lifes)) {
                player2Bullets.add(new Bullet(canvas, new Vector((int) player2.getPosition().x, (int) player2.getPosition().y), new Vector(2 * player2.getDirection().x, 2 * player2.getDirection().y), MainApplication.class.getResource("bullet.png").getPath()));
                shoot(p2Bullets);
            }
        }
        if (keyEvent.getCode() == KeyCode.J) {
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

                            for (int i = 0; i < walls.size(); i++) {
                                walls.get(i).draw();
                            }

                            for (int i = 0; i < enemies.size(); i++) {
                                enemies.get(i).draw();
                            }

                            for (int i = 0; i < player1Bullets.size(); i++) {
                                player1Bullets.get(i).draw();
                                if (player1Bullets.get(i).pos.x > canvas.getWidth() + 20 ||
                                        player1Bullets.get(i).pos.y > canvas.getHeight() + 20 ||
                                        player1Bullets.get(i).pos.x < -20 || player1Bullets.get(i).pos.y < -20) {
                                    player1Bullets.remove(i);
                                }
                            }
                            detectColition();

                            for (int i = 0; i < player2Bullets.size(); i++) {
                                player2Bullets.get(i).draw();
                                if (player2Bullets.get(i).pos.x > canvas.getWidth() + 20 ||
                                        player2Bullets.get(i).pos.y > canvas.getHeight() + 20 ||
                                        player2Bullets.get(i).pos.x < -20 || player2Bullets.get(i).pos.y < -20) {
                                    player2Bullets.remove(i);
                                }
                            }
                            detectColition();

                            //Player 1 movement
                            boolean flag = true;
                            if (wPressed) {
                                for (Wall wall : walls) {
                                    if (wall.rectangle.intersects(player1.getPosition().x + player1.getDirection().x - 15,
                                            player1.getPosition().y + player1.getDirection().y - 15, 30, 30)) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    if (player1.getPosition().x > canvas.getWidth() - 15) {
                                        player1.getPosition().x = player1.getPosition().x - 10;
                                    } else if (player1.getPosition().y > canvas.getHeight() - 10) {
                                        player1.getPosition().y = player1.getPosition().y - 10;
                                    } else if (player1.getPosition().x < 15) {
                                        player1.getPosition().x = player1.getPosition().x + 10;
                                    } else if (player1.getPosition().y < 15) {
                                        player1.getPosition().y = player1.getPosition().y + 10;
                                    } else {
                                        player1.moveForward();
                                    }
                                }
                            }

                            if (aPressed) {
                                player1.changeAngle(-3);
                            }
                            if (sPressed) {
                                for (Wall wall : walls) {
                                    if (wall.rectangle.intersects(player1.getPosition().x - player1.getDirection().x - 15,
                                            player1.getPosition().y - player1.getDirection().y - 15, 30, 30)) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    if (player1.getPosition().x > canvas.getWidth() - 15) {
                                        player1.getPosition().x = player1.getPosition().x - 10;
                                    } else if (player1.getPosition().y > canvas.getHeight() - 10) {
                                        player1.getPosition().y = player1.getPosition().y - 10;
                                    } else if (player1.getPosition().x < 15) {
                                        player1.getPosition().x = player1.getPosition().x + 10;
                                    } else if (player1.getPosition().y < 15) {
                                        player1.getPosition().y = player1.getPosition().y + 10;
                                    } else {
                                        player1.moveBackward();
                                    }
                                }
                            }
                            if (dPressed) {
                                player1.changeAngle(3);
                            }

                            //Player 2 movement
                            if (upPressed) {
                                for (Wall wall : walls) {
                                    if (wall.rectangle.intersects(player2.getPosition().x + player2.getDirection().x - 15,
                                            player2.getPosition().y + player2.getDirection().y - 15, 30, 30)) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    if (player2.getPosition().x > canvas.getWidth() - 15) {
                                        player2.getPosition().x = player2.getPosition().x - 10;
                                    } else if (player2.getPosition().y > canvas.getHeight() - 10) {
                                        player2.getPosition().y = player2.getPosition().y - 10;
                                    } else if (player2.getPosition().x < 15) {
                                        player2.getPosition().x = player2.getPosition().x + 10;
                                    } else if (player2.getPosition().y < 15) {
                                        player2.getPosition().y = player2.getPosition().y + 10;
                                    } else {
                                        player2.moveForward();
                                    }
                                }
                            }
                            if (leftPressed) {
                                player2.changeAngle(-3);
                            }
                            if (downPressed) {
                                for (Wall wall : walls) {
                                    if (wall.rectangle.intersects(player2.getPosition().x - player2.getDirection().x - 15,
                                            player2.getPosition().y - player2.getDirection().y - 15, 30, 30)) {
                                        flag = false;
                                    }
                                }
                                if (flag) {
                                    if (player2.getPosition().x > canvas.getWidth() - 15) {
                                        player2.getPosition().x = player2.getPosition().x - 10;
                                    } else if (player2.getPosition().y > canvas.getHeight() - 10) {
                                        player2.getPosition().y = player2.getPosition().y - 10;
                                    } else if (player2.getPosition().x < 15) {
                                        player2.getPosition().x = player2.getPosition().x + 10;
                                    } else if (player2.getPosition().y < 15) {
                                        player2.getPosition().y = player2.getPosition().y + 10;
                                    } else {
                                        player2.moveBackward();
                                    }
                                }
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

    public void drawBot() {
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

                                if (bot.getPosition().y >= 297) {
                                    bot.getPosition().y = 296;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().x <= 177) {
                                    bot.getPosition().x = 178;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().y <= 71) {
                                    bot.getPosition().y = 72;
                                    bot.changeAngle(90);
                                }
                                if (bot.getPosition().x >= 423) {
                                    bot.getPosition().x = 422;
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

    public void checkMatch() {
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {

                            if (checkLife(p1Lifes) && !checkLife(p2Lifes) && !checkLife(botLifes)){
                                try {
                                    isRunning = false;
                                    PlayerData.getInstance().setWinner(PlayerData.getInstance().getCurrentPlayer1().getUserName());
                                    String currentWinner = PlayerData.getInstance().getWinner();
                                    for (int i = 0; i < PlayerData.getInstance().getUsers().size(); i++) {
                                        if (PlayerData.getInstance().getUsers().get(i).getUserName().equals(currentWinner)){
                                            int wins = PlayerData.getInstance().getUsers().get(i).getWins() + 1;
                                            PlayerData.getInstance().getUsers().get(i).setWins(wins);
                                            break;
                                        }
                                    }
                                    MainApplication.showWindow("final-screen.fxml");
                                    Stage currentStage = (Stage) P1name.getScene().getWindow();
                                    currentStage.hide();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            if (!checkLife(p1Lifes) && checkLife(p2Lifes) && !checkLife(botLifes)){
                                try {
                                    isRunning = false;
                                    PlayerData.getInstance().setWinner(PlayerData.getInstance().getCurrentPlayer2().getUserName());
                                    for (int i = 0; i < PlayerData.getInstance().getUsers().size(); i++) {
                                        String currentWinner = PlayerData.getInstance().getWinner();
                                        if (PlayerData.getInstance().getUsers().get(i).equals(currentWinner)){
                                            int wins = PlayerData.getInstance().getUsers().get(i).getWins() + 1;
                                            PlayerData.getInstance().getUsers().get(i).setWins(wins);
                                            break;
                                        }
                                    }
                                    MainApplication.showWindow("final-screen.fxml");
                                    Stage currentStage = (Stage) P1name.getScene().getWindow();
                                    currentStage.hide();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            if (!checkLife(p1Lifes) && !checkLife(p2Lifes) && checkLife(botLifes)){
                                try {
                                    isRunning = false;
                                    PlayerData.getInstance().setWinner("BOT won");
                                    MainApplication.showWindow("final-screen.fxml");
                                    Stage currentStage = (Stage) P1name.getScene().getWindow();
                                    currentStage.hide();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            if(!checkLife(p1Lifes) && !checkLife(p2Lifes) && !checkLife(botLifes)){
                                try {
                                    isRunning = false;
                                    PlayerData.getInstance().setWinner("Draw");
                                    MainApplication.showWindow("final-screen.fxml");
                                    Stage currentStage = (Stage) P1name.getScene().getWindow();
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

    public void shoot(HBox player) {
        for (Node node : player.getChildren()) {
            ImageView bullet = (ImageView) node;
            if (bullet.isVisible()) {
                bullet.setVisible(false);
                break;
            }
        }
    }

    public boolean checkAmmo(HBox player) {
        int count = 0;
        for (Node node : player.getChildren()) {
            ImageView bullet = (ImageView) node;
            if (!bullet.isVisible()) {
                count++;
            }
        }
        if (count == 6) {
            return false;    //False = no ammo
        } else {
            return true;   //True = still have ammo
        }
    }

    public void reload(HBox player) {
        for (Node node : player.getChildren()) {
            ImageView bullet = (ImageView) node;
            if (!bullet.isVisible()) {
                bullet.setVisible(true);
            }
        }
    }

    public void detectColition() {
        if (enemies.isEmpty() != true) {
            for (int j = 0; j < player1Bullets.size(); j++) {
                Bullet b = player1Bullets.get(j);
                Avatar e = enemies.get(0);

                double c1 = b.pos.x - e.getPosition().x;
                double c2 = b.pos.y - e.getPosition().y;
                double distance = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));

                if (distance < 35) {
                    player1Bullets.remove(j);
                    for (Node node : botLifes.getChildren()) {
                        ImageView life = (ImageView) node;
                        if (life.isVisible()) {
                            life.setVisible(false);
                            break;
                        }
                    }

                    int count = 0;
                    for (Node node : botLifes.getChildren()) {
                        ImageView life = (ImageView) node;
                        if (!life.isVisible()) {
                            count++;
                        }
                    }
                    if (count == 5) {
                        enemies.remove(0);
                    }
                    return;
                }
            }
            for (int j = 0; j < player2Bullets.size(); j++) {
                Bullet b = player2Bullets.get(j);
                Avatar e = enemies.get(0);

                double c1 = b.pos.x - e.getPosition().x;
                double c2 = b.pos.y - e.getPosition().y;
                double distance = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));

                if (distance < 35) {
                    player2Bullets.remove(j);
                    for (Node node : botLifes.getChildren()) {
                        ImageView life = (ImageView) node;
                        if (life.isVisible()) {
                            life.setVisible(false);
                            break;
                        }
                    }

                    int count = 0;
                    for (Node node : botLifes.getChildren()) {
                        ImageView life = (ImageView) node;
                        if (!life.isVisible()) {
                            count++;
                        }
                    }
                    if (count == 5) {
                        enemies.remove(0);
                    }
                    return;
                }
            }
        }
        for (int j = 0; j < player1Bullets.size(); j++) {
            Bullet b = player1Bullets.get(j);
            Avatar e = player2;

            double c1 = b.pos.x - e.getPosition().x;
            double c2 = b.pos.y - e.getPosition().y;
            double distance = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));

            if (distance < 35) {
                player1Bullets.remove(j);
                for (Node node : p2Lifes.getChildren()) {
                    ImageView life = (ImageView) node;
                    if (life.isVisible()) {
                        life.setVisible(false);
                        break;
                    }
                }

                int count = 0;
                for (Node node : p2Lifes.getChildren()) {
                    ImageView life = (ImageView) node;
                    if (!life.isVisible()) {
                        count++;
                    }
                }
                if (count == 5) {
                    player2.setPosition(canvas.getWidth() + 625, canvas.getHeight() + 625);
                }
                return;
            }
        }
        for (int j = 0; j < player2Bullets.size(); j++) {
            Bullet b = player2Bullets.get(j);
            Avatar e = player1;

            double c1 = b.pos.x - e.getPosition().x;
            double c2 = b.pos.y - e.getPosition().y;
            double distance = Math.sqrt(Math.pow(c1, 2) + Math.pow(c2, 2));

            if (distance < 35) {
                player2Bullets.remove(j);
                for (Node node : p1Lifes.getChildren()) {
                    ImageView life = (ImageView) node;
                    if (life.isVisible()) {
                        life.setVisible(false);
                        break;
                    }
                }

                int count = 0;
                for (Node node : p1Lifes.getChildren()) {
                    ImageView life = (ImageView) node;
                    if (!life.isVisible()) {
                        count++;
                    }
                }
                if (count == 5) {
                    player1.setPosition(canvas.getWidth() + 625, canvas.getHeight() + 625);
                }
                return;
            }
        }
        for (int i = 0; i < player1Bullets.size(); i++) {
            for (int j = 0; j < walls.size(); j++) {
                try {
                    Bullet b = player1Bullets.get(i);
                    Wall w = walls.get(j);
                    if (b.circle.intersects(w.rectangle.getBoundsInParent())) {
                        player1Bullets.remove(i);
                        walls.remove(j);

                    }
                } catch (Exception e) {

                }
            }
        }
        for (int i = 0; i < player2Bullets.size(); i++) {
            for (int j = 0; j < walls.size(); j++) {
                try {
                    Bullet b = player2Bullets.get(i);
                    Wall w = walls.get(j);
                    if (b.circle.intersects(w.rectangle.getBoundsInParent())) {
                        player2Bullets.remove(i);
                        walls.remove(j);

                    }
                } catch (Exception e) {

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