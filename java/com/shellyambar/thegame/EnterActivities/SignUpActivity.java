package com.shellyambar.thegame.EnterActivities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.shellyambar.thegame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity  {




    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private AutoCompleteTextView nickname;
    private String userId;
    private FirebaseAuth mAuth;
    private LinearLayout linearLayout;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView=findViewById(R.id.password);
        nickname=findViewById(R.id.nickname);
        mAuth = FirebaseAuth.getInstance();
        linearLayout = findViewById(R.id.layout_signUp);
        animationDrawable  = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();


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


    }

    private void userSignUp(final String email,final String password) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = task.getResult().getUser();
                            userId=user.getUid();
                            //get reference to database
                            DatabaseReference reference= FirebaseDatabase
                                    .getInstance().getReference("Users").child(userId);

                            HashMap<String,Object> hashMap=new HashMap<>();
                            hashMap.put("userPhoto","");
                            hashMap.put("userName",nickname.getText().toString());
                            hashMap.put("userId",userId);
                            hashMap.put("totalScore",0);
                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    SetAllLevelsToUser();

                                    Toast.makeText(SignUpActivity.this, "Your data has been SAVED successfully!", Toast.LENGTH_SHORT).show();
                                    updateUI(mAuth.getCurrentUser());

                                }
                            });



                        } else {
                            // If sign in fails, display a message to the user.


                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void SetAllLevelsToUser(){
        DatabaseReference reference2= FirebaseDatabase
                .getInstance().getReference("Users").child(userId).child("levels");
        HashMap<String,Object> hashMap2=new HashMap<>();
        for(int i=1; i<11; i++){
            hashMap2.put("levelNum",i);


            if(i==1){
                hashMap2.put("isOpen","true");
            }else{
                hashMap2.put("isOpen","false");
            }
            reference2.push().setValue(hashMap2);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            finish();

        }
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {

            startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
            finish();

        }else{
            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        finish();
    }


}
