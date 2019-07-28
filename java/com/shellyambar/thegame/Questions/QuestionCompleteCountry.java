package com.shellyambar.thegame.Questions;

public class QuestionCompleteCountry extends QuestionComplete {
    private static volatile QuestionCompleteCountry instance;

    public static QuestionCompleteCountry getInstance() {
        if (instance == null) {
            synchronized (QuestionCompleteCountry.class) {
                if (instance == null) {
                    instance = new QuestionCompleteCountry();
                }
            }
        }

        return instance;
    }

    private CountryQuestion countryQuestion;

    private QuestionCompleteCountry() {
        countryQuestion = CountryQuestion.getInstance();
    }

    @Override
    public void CreateQuestion() {
        countryQuestion.CreateCountryQuestion();

        setQuestion(countryQuestion.getCapital());
        setOptions(countryQuestion.Options());
        setAnswer(countryQuestion.getCountry());
    }
}
