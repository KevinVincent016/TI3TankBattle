package com.example.ti3tankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet {

    private Canvas canvas;
    private GraphicsContext gc;

    private Image bulletStyle;
    public Vector pos;
    public Vector direction;
    public Circle circle;

    public Bullet(Canvas canvas, Vector pos, Vector direction, String imagePath){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.pos = pos;
        this.direction = direction;
        String uri = "file:" + imagePath;
        bulletStyle = new Image(uri);
        circle = new Circle(pos.x, pos.y, 5);
    }

    public void draw(){
        gc.drawImage(bulletStyle,pos.x-5,pos.y-5,10,10);
        circle = new Circle(pos.x,pos.y,10);
        pos.x+= direction.x;
        pos.y+= direction.y;
        double xpos = pos.x;
        double ypos = pos.y;
    }
}
