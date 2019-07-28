package com.shellyambar.thegame.Obstacles;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.shellyambar.thegame.Interfaces.GameObject;
import com.shellyambar.thegame.Game.Player;

public  abstract class Obstacle implements GameObject {

    private int rectHeight;
    private int startX;
    private int startY;
    private int rectWidth;
    private Rect rectangle;

    public Obstacle(int rectHeight,int rectWidth, int startX, int startY) {
        this.rectHeight = rectHeight;
        this.startX = startX;
        this.startY = startY;
        this.rectWidth = rectWidth;

    }


    public abstract Rect getRectangle();

    public abstract void IncrementHeight(float incrementY);
    public abstract boolean PlayerCollide(Player player);

    @Override
    public void Draw(Canvas canvas) {}

    @Override
    public void Update() { }

}
