package com.shellyambar.thegame.EnterActivities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shellyambar.thegame.MyMusicService;
import com.shellyambar.thegame.R;

public class EnterActivity extends AppCompatActivity {
    private Button login;
    private Button signup;
    private LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        linearLayout = findViewById(R.id.layout_enter);
        animationDrawable  = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();

        Intent intent = new Intent(EnterActivity.this, MyMusicService.class);
        startService(intent);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Swing)
                        .duration(800)
                        .repeat(1)
                        .playOn(login);
                startActivity(new Intent(EnterActivity.this, LogInActivity.class));

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EnterActivity.this, CardsActivity.class));

                YoYo.with(Techniques.Shake)
                        .duration(800)
                        .repeat(1)
                        .playOn(signup);

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EnterActivity.this, MyMusicService.class);
        stopService(intent);
    }
}
