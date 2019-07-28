package com.shellyambar.thegame.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.shellyambar.thegame.MainThread;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread mainThread;
    private SceneManager sceneManager;



    public  GamePanel(Context context, int levelNumber ){
        super(context);

        Constants.CURRENT_CONTEXT = context;
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(),this);
        setFocusable(true);
        sceneManager = new SceneManager(levelNumber);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }
    public int GetCurrentScore(){
      return   sceneManager.GetCurrentScore();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        Constants.INIT_TIME= System.currentTimeMillis();
        mainThread = new MainThread(getHolder(),this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    public void DestroyPanel(){
        boolean retry =true;
        while (retry)
        {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            }catch (Exception e){ e.printStackTrace();}
            retry=false;
        }
    }
    @Override

    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry =true;
        while (retry)
        {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            }catch (Exception e){ e.printStackTrace();}
            retry=false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        sceneManager.ReceiveTouch(event);
        return true;
    }





    public void update()
    {
        sceneManager.Update();

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        sceneManager.Draw(canvas);
    }


}
