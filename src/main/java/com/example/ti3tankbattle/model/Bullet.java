package com.example.ti3tankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet {

    private Canvas canvas;
    private GraphicsContext gc;

    private Image bulletStyle;
    public int x,y;

    public Bullet(Canvas canvas, int x, int y, String imagePath){
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.x = x;
        this.y = y;
        String uri = "file:" + imagePath;
        bulletStyle = new Image(uri);
    }

    public void draw(){
        gc.setFill(Color.YELLOW);
        gc.drawImage(bulletStyle,x-5,y-5,10,10);
        x+=10;
    }

}
