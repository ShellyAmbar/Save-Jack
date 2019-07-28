package com.shellyambar.thegame.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Animation {
    private Bitmap[] Frames;
    private int frameIndex;
    private float frameTime;
    private boolean isPlaying = false;
    private long lastFrame;


    public Animation(Bitmap[] frames, float animTime) {
        Frames = frames;
        frameIndex = 0;
        this.frameTime = animTime/Frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void Play(){
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    public void Stop(){
        isPlaying = false;

    }

    public void Draw(Canvas canvas, Rect destination){
        if(!isPlaying){
            return;
        }
        ScaleRatio(destination);
        canvas.drawBitmap(Frames[frameIndex],null,destination,new Paint());
    }

    private void ScaleRatio(Rect rect) {
        float whRatio = (float)(Frames[frameIndex].getWidth())/Frames[frameIndex].getHeight();
        if(rect.width()>rect.height()){
            rect.left = rect.right - (int)(rect.height()*whRatio);
        }else{
            rect.top = rect.bottom - (int)(rect.width()*(1/whRatio));
        }



    }

    public void Update(){

        if(!isPlaying){
            return;
        }
        if(System.currentTimeMillis() - lastFrame > frameTime*1000){
            frameIndex++;
            frameIndex = frameIndex >= Frames.length ? 0 :  frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }


}
