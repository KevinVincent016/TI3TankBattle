package com.example.ti3tankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall {
    private Canvas canvas;
    private GraphicsContext gc;
    private Image wallStyle;
    private Vector pos;
    public Rectangle rectangle;

    public Wall(Canvas canvas, String imagePath, int posX, int posY){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        String uri = "file:" + imagePath;
        wallStyle = new Image(uri);
        pos = new Vector(posX, posY);
    }

    public void draw(){
        gc.setFill(Color.RED);
        gc.fillRect(pos.x, pos.y, 50,50);
        gc.drawImage(wallStyle, pos.x, pos.y,50,50);
        rectangle = new Rectangle(pos.x,pos.y,50,50);
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector pos) {
        this.pos = pos;
    }
}
