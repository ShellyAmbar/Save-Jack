package com.shellyambar.thegame.Fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shellyambar.thegame.Adapters.ScoresAdapter;
import com.shellyambar.thegame.Models.ScoresModel;
import com.shellyambar.thegame.Models.UsersModel;
import com.shellyambar.thegame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AllScoresFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ScoresModel> scoresModelList;
    private ScoresAdapter scoresAdapter;
    private RelativeLayout layout;
    private AnimationDrawable animationDrawable;
    public AllScoresFragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View view = inflater.inflate(R.layout.fragment_all_scores, container, false);
       recyclerView = view.findViewById(R.id.recycler);
        layout = view.findViewById(R.id.layout_scores);
        animationDrawable  = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        scoresModelList = new ArrayList<>();
        scoresAdapter = new ScoresAdapter(getContext(),scoresModelList );
        recyclerView.setAdapter(scoresAdapter);
        recyclerView.setHasFixedSize(true);

        getAllUsersScoresFromDatabase();
       return view;

    }




    @Override
    public void onStart() {
        super.onStart();
        getAllUsersScoresFromDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllUsersScoresFromDatabase();
    }

    public void getAllUsersScoresFromDatabase(){

        DatabaseReference reference = FirebaseDatabase
                .getInstance().getReference("Users");
        reference.orderByChild("totalScore").startAt(0).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                scoresModelList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    UsersModel usersModel = snapshot.getValue(UsersModel.class);
                    int totalScoreOfUser = usersModel.getTotalScore();
                    String userName = usersModel.getUserName();
                    String userUrl =  usersModel.getUserPhoto();
                    ScoresModel scoresModel = new ScoresModel();
                    scoresModel.setUserName(userName);
                    scoresModel.setUserScore(totalScoreOfUser);
                    scoresModel.setUserPhoto(userUrl);
                    scoresModelList.add(scoresModel);
                }
                Collections.reverse(scoresModelList);

                scoresAdapter.SetNewArrayOfScores(scoresModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
