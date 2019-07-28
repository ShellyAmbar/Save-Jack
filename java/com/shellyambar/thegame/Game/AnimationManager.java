package com.shellyambar.thegame.Game;

import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimationManager {

    private Animation[] animations;
    private int AnimationIndex;


    public AnimationManager(Animation[] animations) {
        this.animations = animations;
        AnimationIndex = 0;
    }

    public void PlayAnim(int indexOfAnim){
        for(int i=0;i<animations.length;i++){
            if(i==indexOfAnim){
                if(!animations[indexOfAnim].isPlaying()){
                    animations[i].Play();
                }
            }else{
                animations[i].Stop();
            }
        }
        AnimationIndex =indexOfAnim;
    }
    public void Draw(Canvas canvas, Rect rect){
        if(animations[AnimationIndex].isPlaying()){
            animations[AnimationIndex].Draw(canvas,rect);
        }
    }

    public void Update(){
        if(animations[AnimationIndex].isPlaying()){
            animations[AnimationIndex].Update();
        }
    }
}
