package com.shellyambar.thegame.Fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.shellyambar.thegame.Adapters.LevelsAdapter;
import com.shellyambar.thegame.Models.LevelsModel;
import com.shellyambar.thegame.Models.UsersModel;
import com.shellyambar.thegame.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AllLevelsFragment extends Fragment {

    private RecyclerView gridView;
    private List<LevelsModel> levelsModelsList;
    private LevelsAdapter levelsAdapter;
    private int indexLimitToUnlock;
    private  int MyTotalScore;
    private RelativeLayout layout;
    private AnimationDrawable animationDrawable;
    public AllLevelsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_levels, container, false);
        gridView = view.findViewById(R.id.gridView);
        layout = view.findViewById(R.id.layout_levels);
        animationDrawable  = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();


        LinearLayoutManager linearLayoutManager=new GridLayoutManager(getContext(),2);
        levelsModelsList = new ArrayList<>();
        levelsAdapter = new LevelsAdapter(getContext(),levelsModelsList);

        gridView.setLayoutManager(linearLayoutManager);
        gridView.setAdapter(levelsAdapter);
        gridView.setHasFixedSize(true);

        //CreateLevelsToList();

        UpDateLocksOfLevelsAndSetToList();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        UpDateLocksOfLevelsAndSetToList();
    }

    @Override
    public void onResume() {
        super.onResume();
        UpDateLocksOfLevelsAndSetToList();
    }

    public void UpDateLocksOfLevelsAndSetToList(){

        DatabaseReference reference = FirebaseDatabase
                .getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UsersModel usersModel = dataSnapshot.getValue(UsersModel.class);
                MyTotalScore = usersModel.getTotalScore();

                if(MyTotalScore<0){
                    MyTotalScore =0;
                }
                int i = 0;

                while(MyTotalScore >= ((i)*10)){
                    i=i+1;
                    indexLimitToUnlock = i;
                }


                levelsModelsList.clear();
                for(int j=1; j<=i+10; j++){
                    LevelsModel levelsModel = new LevelsModel();
                    if(j<= indexLimitToUnlock){
                        levelsModel.setIsOpen("true");
                    }else{
                        levelsModel.setIsOpen("false");
                    }
                    levelsModel.setLevelNum(j);
                    levelsModelsList.add(levelsModel);
                }
                levelsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
