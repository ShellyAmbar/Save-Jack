package com.shellyambar.thegame.Questions;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shellyambar.thegame.R;

public class AnwerFromLetters {

    private View view;
    private LinearLayout linearLayout;
    private TextView textViewQuestion;
    private String question;
    private String answer;

    private AnwerFromLetters(String question, String answer, Context context) {


        this.question = question;
        this.answer = answer;
    }
}
