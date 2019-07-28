package com.shellyambar.thegame.Game;

import android.support.annotation.NonNull;

import com.shellyambar.thegame.Models.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ScoresHandler {



    private int myCurrentScore=0;
    public ScoresHandler(){


    }



//after each level we add the score to all the scores
    public void addCurrentScoreToMyTotalScore(final int currentScore) {
        final DatabaseReference reference = FirebaseDatabase
                .getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersModel usersModel = dataSnapshot.getValue(UsersModel.class);
                myCurrentScore = usersModel.getTotalScore();

                int myTotalScore =  myCurrentScore + currentScore;
                if(myTotalScore<0){myTotalScore = 0;}
                HashMap<String,Object> hashMap = new HashMap<>();
                hashMap.put("totalScore",myTotalScore);

                //add back to database the new score
                reference.updateChildren(hashMap);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






    }





}
