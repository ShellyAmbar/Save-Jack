package com.shellyambar.thegame.Models;

import java.util.List;

public class QuestionAnswerModel {
    String Question = "";
    String rightAnswer = "";
   List<String> AllAnswers;

    public QuestionAnswerModel(String question, String rightAnswer, List<String> allAnswers) {
        Question = question;
        this.rightAnswer = rightAnswer;
        this.AllAnswers = allAnswers ;

    }


    public QuestionAnswerModel() {
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public List<String> getAllAnswers() {
        return AllAnswers;
    }

    public void setAllAnswers(List<String> allAnswers) {
        AllAnswers = allAnswers;
    }
}
