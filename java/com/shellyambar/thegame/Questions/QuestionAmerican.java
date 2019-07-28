package com.shellyambar.thegame.Questions;

import java.util.ArrayList;

public abstract class QuestionAmerican {
    private String question;
    private String answer;
    private ArrayList<String> options;

    public QuestionAmerican()
    {
        options = new ArrayList<>();
    }

    public void setQuestion(String question) {

        this.question = question;
    }

    public void setAnswer(String answer) {

        this.answer = answer;
    }

    public void setOptions(ArrayList<String> options) {
        this.options.clear();

        for (String option : options) {
            this.options.add(option);
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public Boolean CheckAnswer(String answer) {
        return getAnswer().equals(answer);
    }

    public abstract void CreateQuestion();
}

