package com.shellyambar.thegame.Obstacles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.shellyambar.thegame.Game.Constants;
import com.shellyambar.thegame.Game.Player;
import com.shellyambar.thegame.R;

public class CountriesObstacle extends Obstacle {
    private Rect rectangle;

    public CountriesObstacle(int rectHeight, int rectWidth,int startX,int startY) {
        super(rectHeight,rectWidth,startX,startY);

        rectangle = new Rect(startX,startY,startX+ rectWidth,startY+rectHeight);

    }

    @Override
    public void IncrementHeight(float incrementY){
        rectangle.top+=incrementY;
        rectangle.bottom+=incrementY;

    }
    @Override
    public Rect getRectangle() {
        return rectangle;
    }
    @Override
    public void Draw(Canvas canvas) {
        BitmapFactory bf= new BitmapFactory();
        Bitmap bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.globe);
        canvas.drawBitmap(bitmap,null,rectangle,new Paint());
    }
    @Override
    public boolean PlayerCollide(Player player)
    {

        return Rect.intersects(rectangle, player.getRectangle());
    }
    @Override
    public void Update() {

    }

}
