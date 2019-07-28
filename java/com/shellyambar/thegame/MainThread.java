package com.shellyambar.thegame;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.shellyambar.thegame.Game.GamePanel;

public class MainThread extends Thread{

    public static final int MAX_FPS =700;
    private double average_FPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running = false;
    public static Canvas canvas;


    public void setRunning(boolean running) {
        this.running = running;
    }

    public  MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        this.gamePanel=gamePanel;
        this.surfaceHolder=surfaceHolder;

    }

    @Override
    public void run() {
        super.run();
        long startTime ;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while (running){

            startTime = System.nanoTime();
            canvas=null;
            try{
                canvas = surfaceHolder.lockCanvas();
                synchronized (canvas)
                {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }finally {
                if(canvas!=null)
                {
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){e.printStackTrace();}
                }
            }
            timeMillis=System.nanoTime()-startTime;
            waitTime = targetTime - timeMillis/1000000;
           // waitTime = 1;
            try{
                if(waitTime>0){this.sleep(waitTime);}
            }catch(Exception e){e.printStackTrace();}
            totalTime+= System.nanoTime()-startTime;
            frameCount++;
            if(frameCount==MAX_FPS)
            {
                average_FPS = 1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                System.out.println(average_FPS);
            }

        }



    }
}
