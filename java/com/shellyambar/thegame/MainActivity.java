package com.shellyambar.thegame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shellyambar.thegame.Adapters.PageAdapter;
import com.shellyambar.thegame.Models.UsersModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button buttonStartPlaying;
    private ViewPager viewPagerUp;
    private ViewPager viewPagerDown;
    private TabLayout tableLayout;
    private PageAdapter pageAdapter;
    private TabItem tabItemUsers;
    private TabItem tabItemAllScores;
    private TabItem tabItemLevels;
    private TextView text_welcome;
    private CircleImageView image_user;
    private Context context;
    private boolean isMute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // viewPagerUp=findViewById(R.id.view_pager_up);
        viewPagerDown=findViewById(R.id.view_pager_down);
        tableLayout = findViewById(R.id.tabLayout);
        tabItemLevels=findViewById(R.id.all_levels);
        tabItemAllScores=findViewById(R.id.all_scores);
        tabItemUsers=findViewById(R.id.all_users);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tableLayout.getTabCount());
        viewPagerDown.setAdapter(pageAdapter);
        isMute =true;
        context = getApplicationContext();
        tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPagerDown.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryLight));
                        tableLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryLight));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryLight));
                        }

                        break;

                    case 1:
                        toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        tableLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimaryDark));
                        }
                        break;
                    case 2:
                        toolbar.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                        tableLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                        }
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPagerDown.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));



        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

         View hView = navigationView.getHeaderView(0);


        text_welcome = hView.findViewById(R.id.text_welcome);
        image_user = hView.findViewById(R.id.image_user);
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    UsersModel usersModel =  dataSnapshot.getValue(UsersModel.class);

                    if(!usersModel.getUserName().isEmpty()){
                        text_welcome.setText(getResources().getString(R.string.welcome) + " " +usersModel.getUserName().toUpperCase());
                    }
                    if(!usersModel.getUserPhoto().isEmpty()){

                        Glide.with(MainActivity.this).load(usersModel.getUserPhoto()).into(image_user);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            Toast.makeText(this, "Sorry, You are not logged-in to the game.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {


        Exitdialog();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.nav_logout:
                Exitdialog();
                break;
            case R.id.nav_volume:

                if(isMute){
                    Intent intent = new Intent(MainActivity.this, MyMusicService.class);
                    stopService(intent);


                }else{

                    Intent intent = new Intent(MainActivity.this, MyMusicService.class);
                    startService(intent);
                }
                isMute = !isMute;

                break;


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Exitdialog(){

        final AlertDialog.Builder alertDialog=
                new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogCustomText);
        alertDialog.setTitle(R.string.exit_line );


        final TextView textView =new TextView(MainActivity.this);
        textView.setText("");
        textView.setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);




        LinearLayout linearLayout=new LinearLayout(MainActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        linearLayout.addView(textView,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                        ,ViewGroup.LayoutParams.WRAP_CONTENT,0));
        alertDialog.setView(linearLayout);

        alertDialog.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });
        alertDialog.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //check answer and update score;
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(MainActivity.this, MyMusicService.class);
                stopService(intent);
                FirebaseAuth.getInstance().signOut();
                dialog.dismiss();
                finish();

            }
        });

        final AlertDialog alert = alertDialog.create();
        alert.show();
        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText("" + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext


            }

            public void onFinish() {

                alert.dismiss();

            }

        }.start();


        alert.getButton(DialogInterface.BUTTON_NEUTRAL).setBackgroundResource(R.drawable.button_cancel);
        alert.getButton(DialogInterface.BUTTON_NEUTRAL).setPadding(10,5,10,5);
        alert.getButton(DialogInterface.BUTTON_NEUTRAL).setTextColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));

        alert.getButton(DialogInterface.BUTTON_POSITIVE).setBackgroundResource(R.drawable.button_light);
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.WHITE);

    }
}
