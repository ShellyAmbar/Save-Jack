package com.shellyambar.thegame.Fragments;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
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

import com.shellyambar.thegame.Adapters.AllUsersAdapter;
import com.shellyambar.thegame.Models.UsersModel;
import com.shellyambar.thegame.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AllUsersFragment extends Fragment {

    private RecyclerView gridView;
    private List<UsersModel> usersModelList;
    private AllUsersAdapter allUsersAdapter;
    private RelativeLayout layout;
    private AnimationDrawable animationDrawable;
    public AllUsersFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_all_users, container, false);
        gridView = view.findViewById(R.id.gridView);
        layout = view.findViewById(R.id.layout_users);
        animationDrawable  = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(1300);
        animationDrawable.setExitFadeDuration(1300);
        animationDrawable.start();


        LinearLayoutManager linearLayoutManager=new GridLayoutManager(getContext(),2);
        usersModelList = new ArrayList<>();
        allUsersAdapter = new AllUsersAdapter(getContext(),usersModelList);

        gridView.setHasFixedSize(true);
        gridView.setAdapter(allUsersAdapter);
        gridView.setLayoutManager(linearLayoutManager);

        GetAllUsers getAllUsers = new GetAllUsers();
        getAllUsers.execute();

        return view;
    }

    public class GetAllUsers extends AsyncTask<Void,Void,Void>{


        @Override
        protected Void doInBackground(Void... voids) {
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference reference= FirebaseDatabase.getInstance()
                    .getReference("Users");


            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    usersModelList.clear();
                    for(DataSnapshot snapshot :  dataSnapshot.getChildren())
                    {
                        UsersModel usersModel = snapshot.getValue(UsersModel.class);
                        usersModelList.add(usersModel);
                    }
                    Collections.reverse(usersModelList);
                    allUsersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }


    }

    private void SetAllUsersToList() {

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference= FirebaseDatabase.getInstance()
                .getReference("Users");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                usersModelList.clear();
                for(DataSnapshot snapshot :  dataSnapshot.getChildren())
                {
                    UsersModel usersModel = snapshot.getValue(UsersModel.class);
                    usersModelList.add(usersModel);
                }

                allUsersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
