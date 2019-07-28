package com.shellyambar.thegame.Game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.shellyambar.thegame.Interfaces.Scene;

import java.util.ArrayList;

public class SceneManager {
    private  ArrayList<Scene> scenes = new ArrayList<>() ;
    public static int ActiveScene;
    private int numOfLevel;
    public SceneManager(int numOfLevel) {
        this.numOfLevel=numOfLevel;
        this.scenes.add(new GamePlayScene(numOfLevel));
        ActiveScene = 0;
    }
    public void AddNewScene(){
        this.scenes.add(new GamePlayScene(numOfLevel));
        ActiveScene ++;
    }

    public void ReceiveTouch(MotionEvent motionEvent){
        this.scenes.get(ActiveScene).ReceiveTouch(motionEvent);
    }

    public void Update(){

        this.scenes.get(ActiveScene).Update();
    }

    public void Draw(Canvas canvas){
        this.scenes.get(ActiveScene).Draw(canvas);
    }

    public int GetCurrentScore(){
       return this.scenes.get(ActiveScene).GetCurrentScore();
    }


}
