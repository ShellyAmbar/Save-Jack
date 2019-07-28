package com.shellyambar.thegame.Obstacles;

import android.graphics.Canvas;

import com.shellyambar.thegame.Game.Constants;
import com.shellyambar.thegame.Game.Player;

import java.util.ArrayList;
import java.util.List;

public class ObstacleManager {
   private List<Obstacle> Obstacles;
   private int ObstacleWidth;
   private int obstacleGap;
   private int obstacleHeight;
   private int color;
   private long startTime;
   private long initTime;
   private  int score =0 ;
   private int numOfLevel;
    public ObstacleManager(int ObstacleWidth, int obstacleGap,int obstacleHeight,int color,int numOfLevel) {
        this.numOfLevel = numOfLevel;
        this.ObstacleWidth = ObstacleWidth;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight=obstacleHeight;
        this.color=color;
        startTime= initTime = System.currentTimeMillis();
        Obstacles= new ArrayList<>();
        PopulateObstacles();
    }

    public Obstacle PlayerCollide(Player player){
        Obstacle obstacleCollided = null;
        for(Obstacle obstacle : Obstacles)
        {
            if(obstacle.PlayerCollide(player))
            {
                obstacleCollided = obstacle ;

            }

        }


        return obstacleCollided ;
    }




    public void PopulateObstacles(){
        int currY = -5* Constants.SCREEN_HEIGHT/4;
        int xStartBomb;
        while (currY < 0 )
        {



                xStartBomb = (int) (Math.random() * (Constants.SCREEN_WIDTH - ObstacleWidth));
                Obstacles.add(new KnowledgeObstacle(obstacleHeight, ObstacleWidth, xStartBomb, currY));

                currY += (obstacleHeight + obstacleGap / numOfLevel);


                xStartBomb = (int) (Math.random() * (Constants.SCREEN_WIDTH - ObstacleWidth));
                Obstacles.add(new CountriesObstacle(obstacleHeight, ObstacleWidth, xStartBomb, currY));

                currY += (obstacleHeight + obstacleGap / numOfLevel);


                xStartBomb = (int) (Math.random() * (Constants.SCREEN_WIDTH - ObstacleWidth));
                Obstacles.add(new OctopusObstacle(obstacleHeight, ObstacleWidth, xStartBomb, currY));


                currY += (obstacleHeight + obstacleGap / numOfLevel);


                xStartBomb = (int) (Math.random() * (Constants.SCREEN_WIDTH - ObstacleWidth));
                Obstacles.add(new JellyFishObstacle(obstacleHeight, ObstacleWidth, xStartBomb, currY));


                currY += (obstacleHeight + obstacleGap / numOfLevel);

                xStartBomb = (int) (Math.random() * (Constants.SCREEN_WIDTH - ObstacleWidth));
                Obstacles.add(new BombObstacle(obstacleHeight, ObstacleWidth, xStartBomb, currY));


                currY += (obstacleHeight + obstacleGap / numOfLevel);




        }
    }
    public void Update(){



        int elapsedTime = numOfLevel*100;



        float speed = (float)(Math.sqrt(1+(elapsedTime)/80000.0f))*Constants.SCREEN_HEIGHT/20000.0f;
        for(Obstacle obstacle : Obstacles){

            obstacle.IncrementHeight(speed* elapsedTime);
        }




        if(Obstacles.get(Obstacles.size()-1).getRectangle().top>=Constants.SCREEN_HEIGHT){
            int xStartBomb = (int)(Math.random()*(Constants.SCREEN_WIDTH - ObstacleWidth));
            Obstacles.add(0,new BombObstacle(obstacleHeight,ObstacleWidth,xStartBomb,Obstacles.get(0).getRectangle().top - obstacleGap/numOfLevel ));
            Obstacles.remove(Obstacles.size()-1);

            xStartBomb = (int)(Math.random()*(Constants.SCREEN_WIDTH - ObstacleWidth));

            Obstacles.add(0,new OctopusObstacle(obstacleHeight,ObstacleWidth,xStartBomb,Obstacles.get(0).getRectangle().top - obstacleGap/numOfLevel - obstacleHeight));

            xStartBomb = (int)(Math.random()*(Constants.SCREEN_WIDTH - ObstacleWidth));

            Obstacles.add(0,new JellyFishObstacle(obstacleHeight,ObstacleWidth,xStartBomb,Obstacles.get(0).getRectangle().top - obstacleGap/numOfLevel - obstacleHeight));

            xStartBomb = (int)(Math.random()*(Constants.SCREEN_WIDTH - ObstacleWidth));

            Obstacles.add(0,new KnowledgeObstacle(obstacleHeight,ObstacleWidth,xStartBomb,Obstacles.get(0).getRectangle().top - obstacleGap/numOfLevel - obstacleHeight));

            xStartBomb = (int)(Math.random()*(Constants.SCREEN_WIDTH - ObstacleWidth));

            Obstacles.add(0,new CountriesObstacle(obstacleHeight,ObstacleWidth,xStartBomb,Obstacles.get(0).getRectangle().top - obstacleGap/numOfLevel - obstacleHeight));

        }


    }

    public void Draw(Canvas canvas) {
        for (Obstacle obstacle : Obstacles) {
            obstacle.Draw(canvas);


        }



    }


}
