package com.shellyambar.thegame.EnterActivities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shellyambar.thegame.R;

public class HelloActivity extends AppCompatActivity {

    private static int WELCOME_TIMEOUT = 5500;

    LinearLayout outerLinearLayout;
    AnimationDrawable animationDrawableBG;
    ImageView welcome1;
    ImageView welcome2;
    LinearLayout inner_layout1;
    LinearLayout inner_layout2;
    Animation inner_layout1_anim;
    Animation inner_layout2_anim;
    ImageView jelly;
    ImageView player;
    AnimationDrawable playerAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        outerLinearLayout = findViewById(R.id.outer_linear_layout);
        animationDrawableBG = (AnimationDrawable) outerLinearLayout.getBackground();
        animationDrawableBG.setEnterFadeDuration(800);
        animationDrawableBG.setExitFadeDuration(800);
        animationDrawableBG.start();
        welcome1 = findViewById(R.id.welcome1_image);
        welcome2 = findViewById(R.id.welcome2_image);
        inner_layout1 = findViewById(R.id.inner_layout1);
        jelly = findViewById(R.id.jelly_image);
        inner_layout2 = findViewById(R.id.inner_layout2);
        player = findViewById(R.id.player_anim);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        inner_layout1_anim = AnimationUtils.loadAnimation(this,R.anim.inner_layout1_anim);
        inner_layout1.setAnimation(inner_layout1_anim);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim);
        welcome1.startAnimation(animation);
        welcome2.startAnimation(animation);

        inner_layout2_anim = AnimationUtils.loadAnimation(this,R.anim.inner_layout2_anim);
        inner_layout2.setAnimation(inner_layout2_anim);
        playerAnim = (AnimationDrawable) player.getDrawable();
        playerAnim.start();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(HelloActivity.this,EnterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);



    }
}
