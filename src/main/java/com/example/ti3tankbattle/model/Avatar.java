package com.example.ti3tankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Avatar {

    private Canvas canvas;
    private GraphicsContext gc;
    private Image tankStyle;
    private Vector position;
    private Vector direction;

    public Avatar(Canvas canvas, String imagePath, int posX, int posY){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        String uri = "file:" + imagePath;
        tankStyle = new Image(uri);
        position = new Vector(posX, posY);
        direction = new Vector(5,5);
    }

    public void draw(){
        gc.save();
        gc.translate(position.x, position.y);
        gc.rotate(90 + direction.getAngle());
        gc.drawImage(tankStyle,-25,-25,50,50);
        gc.restore();
    }

    public void setPosition(double x, double y){
        position.x = (int)x - 25;
        position.y = (int)y - 25;
    }

    public void changeAngle(double a){
        double amplitude = direction.getAmplitude();
        double angle = direction.getAngle();
        angle += a;
        direction.x = amplitude * Math.cos(Math.toRadians(angle));
        direction.y = amplitude * Math.sin(Math.toRadians(angle));
    }

    public void moveForward(){
        position.x += direction.x;
        position.y += direction.y;
    }

    public void moveBackward(){
        position.x -= direction.x;
        position.y -= direction.y;
    }

}
