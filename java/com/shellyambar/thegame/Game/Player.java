package com.shellyambar.thegame.Game;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;

import com.shellyambar.thegame.Interfaces.GameObject;
import com.shellyambar.thegame.R;

public class Player implements GameObject {

   private Rect rectangle;
   private int color;
   private AnimationManager animationManager;

   private Animation Idle;
   private Animation Walk_Right;
   private Animation Walk_Left;
   private Animation Climb_Up;
   private Animation Jump;

    public Player(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
        BitmapFactory bf = new BitmapFactory();
        Bitmap stand = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player_back);
        Bitmap walk0= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_stand);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_walk1);
        Bitmap walk2= bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_walk2);

        Bitmap climb_up_1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_climb1);
        Bitmap climb_up_2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_climb2);
        Bitmap jump_1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_fall);
        Bitmap jump_2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_hurt);
        Bitmap jump_3 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.player_cheer1);



        Idle = new Animation(new Bitmap[]{stand},2);
        Walk_Right = new Animation(new Bitmap[]{walk0,walk1,walk2},0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk0 = Bitmap.createBitmap(walk0, 0, 0, walk0.getWidth(), walk0.getHeight(), m, false);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        Walk_Left = new Animation(new Bitmap[]{walk0,walk1,walk2},0.5f);
        Climb_Up = new Animation(new Bitmap[]{climb_up_1,climb_up_2},0.5f);
        Jump = new Animation(new Bitmap[]{jump_1,jump_2,jump_3},0.5f);

        animationManager = new AnimationManager(new Animation[]{Idle,Walk_Right,Walk_Left,Climb_Up,Jump});



    }





    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void Draw(Canvas canvas) {

     //   Paint paint = new Paint();
     //   paint.setColor(color);
      //  canvas.drawRect(rectangle,paint);
        animationManager.Draw(canvas,rectangle);
    }

    @Override
    public void Update() {

        animationManager.Update();
    }

    public void Update(Point point){

        float  oldLeft = rectangle.left;
        float oldTop = rectangle.top;

        rectangle.set(point.x- rectangle.width()/2,point.y-rectangle.height()/2,point.x + rectangle.width()/2,point.y+rectangle.height()/2);


        int state=0;

        if(rectangle.left - oldLeft > 3 ){
            // walk right
            state=1;

        }else if (rectangle.left - oldLeft < -3){
            // walk left
            state=2;
        }
        if(rectangle.top - oldTop < - 3){

            //up climb
            state = 3;

        }else if (rectangle.top - oldTop > 3){

            //down jump
            state = 4;
        }

        animationManager.PlayAnim(state);

        animationManager.Update();


    }
}
