package com.shellyambar.thegame;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.List;

public class TextViewCelebrate extends android.support.v7.widget.AppCompatTextView {
    private List<Integer> listOfColors ;



    public TextViewCelebrate(Context context) {
        super(context);
        listOfColors = new ArrayList<>();
        listOfColors.add(Color.BLUE);
        listOfColors.add(Color.RED);
        listOfColors.add(Color.GREEN);
        listOfColors.add(Color.MAGENTA);
        listOfColors.add(Color.YELLOW);
        listOfColors.add(Color.CYAN);

    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
    }

    public void ColorfulText()
    {

        int i= -1;


        for(char c : this.getText().toString().toCharArray()){

            i++;
            if(i == listOfColors.size() ){
                i=0;
            }


            Spannable lastSpannableChar = new SpannableString(String.valueOf(c));

            ForegroundColorSpan fcs = new ForegroundColorSpan(listOfColors.get(i));
            lastSpannableChar.setSpan(fcs, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    }



    }
}
