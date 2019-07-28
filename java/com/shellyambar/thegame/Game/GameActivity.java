package com.shellyambar.thegame.Game;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.shellyambar.thegame.MainActivity;
import com.shellyambar.thegame.R;

public class GameActivity extends Activity {

    private GamePanel gamePanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            getWindow().setNavigationBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        }


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH=displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT=displayMetrics.heightPixels;

        String level_num = getIntent().getStringExtra("level_num");
        int levelNumber =Integer.parseInt(level_num) ;
        //get and set score to database
        gamePanel = new GamePanel(this,levelNumber);
        setContentView(gamePanel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        gamePanel.DestroyPanel();
        ScoresHandler scoresHandler = new ScoresHandler();
        scoresHandler.addCurrentScoreToMyTotalScore(gamePanel.GetCurrentScore());
        finish();
    }


}
