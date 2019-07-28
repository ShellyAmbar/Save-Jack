package com.shellyambar.thegame.EnterActivities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shellyambar.thegame.MainActivity;
import com.shellyambar.thegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInActivity extends AppCompatActivity  {


    private EditText mEmailView;
    private EditText mPasswordView;
    private LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Set up the login form.
        mEmailView =  findViewById(R.id.email);
        mPasswordView=findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        linearLayout = findViewById(R.id.layout_signIn);
        animationDrawable  = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();


        if (mAuth!=null){
            mAuth.signOut();
        }


        final Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoYo.with(Techniques.Flash)
                        .duration(800)
                        .repeat(1)
                        .playOn(mEmailSignInButton);
                userSignUp(mEmailView.getText().toString(),mPasswordView.getText().toString());
            }
        });


        final Button forgot_pass = findViewById(R.id.forgot_pass);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Flash)
                        .duration(800)
                        .repeat(1)
                        .playOn(forgot_pass);
                Intent intent = new Intent(LogInActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

    }

    private void userSignUp(final String email,final String password) {

        mAuth = FirebaseAuth.getInstance();


        if(email.isEmpty()|| password.isEmpty()){
            Toast.makeText(this, getResources().getString(R.string.error_login), Toast.LENGTH_SHORT).show();
        }else{

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                FirebaseUser user = task.getResult().getUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LogInActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                            // ...
                        }
                    });
        }

    }



    private void updateUI(FirebaseUser currentUser) {
        if (currentUser !=null){

            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();

        }else{
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed() {
        finish();
    }




}


