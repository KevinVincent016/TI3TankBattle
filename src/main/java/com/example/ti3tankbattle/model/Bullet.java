package com.example.ti3tankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet {

    private Canvas canvas;
    private GraphicsContext gc;

    private Image bulletStyle;
    public Vector pos;

    public Vector direction;

    public Bullet(Canvas canvas, Vector pos, Vector direction, String imagePath){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.pos = pos;
        this.direction = direction;
        String uri = "file:" + imagePath;
        bulletStyle = new Image(uri);
    }

    public void draw(){
        gc.setFill(Color.YELLOW);
        gc.drawImage(bulletStyle,pos.x-5,pos.y-5,10,10);
        pos.x+= direction.x;
        pos.y+= direction.y;
        double xpos = pos.x;
        double ypos = pos.y;
    }
}
