package com.shellyambar.thegame.Interfaces;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    public void Update();
    public void  Draw(Canvas canvas);
    public void Terminate();
    public void ReceiveTouch(MotionEvent motionEvent);
    public int GetCurrentScore();
}
