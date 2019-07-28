package com.shellyambar.thegame.EnterActivities;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.shellyambar.thegame.Adapters.CardsAdapter;
import com.shellyambar.thegame.Models.CardModel;
import com.shellyambar.thegame.R;

import java.util.ArrayList;
import java.util.List;

public class CardsActivity extends AppCompatActivity {

    private List<CardModel> cardModelList;
    private CardsAdapter cardsAdapter;
    private ViewPager viewPager;
    private Integer[] colors=null;
    private ArgbEvaluator argbEvaluator;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards);


        cardModelList=new ArrayList<>();

        cardModelList.add(new CardModel(getString(R.string.one_title),"",R.drawable.questions));
        cardModelList.add(new CardModel(getString(R.string.two_title),getString(R.string.two_desc),R.drawable.brainy));
        cardModelList.add(new CardModel(getString(R.string.three_title),getString(R.string.three_desc),R.drawable.player_cheer1));
        cardModelList.add(new CardModel(getString(R.string.four_title),getString(R.string.four_desc),R.drawable.books));
        cardModelList.add(new CardModel(getString(R.string.five_title),"",R.drawable.friends));


        cardsAdapter=new CardsAdapter(cardModelList,this);
        viewPager=findViewById(R.id.viewPager);
        viewPager.setAdapter(cardsAdapter);
        viewPager.setPadding(130,0,130,0);
        argbEvaluator=new ArgbEvaluator();
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CardsActivity.this, SignUpActivity.class));
                finish();
            }
        });


        Integer[]colors_temp={
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4),
                getResources().getColor(R.color.color5)

        };
        colors=colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if(position<cardsAdapter.getCount()-1 && position< colors.length -1){
                    viewPager.setBackgroundColor((Integer)argbEvaluator
                            .evaluate(positionOffset,
                                    colors[position],
                                    colors[position+1])
                    );
                }else{
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    public void onBackPressed() {
        finish();
    }
}
