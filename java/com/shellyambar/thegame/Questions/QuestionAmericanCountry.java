package com.shellyambar.thegame.Questions;

public class QuestionAmericanCountry extends QuestionAmerican {

    private static volatile QuestionAmericanCountry instance;

    public static QuestionAmericanCountry getInstance() {
        if (instance == null) {
            synchronized (QuestionAmericanCountry.class) {
                if (instance == null) {
                    instance = new QuestionAmericanCountry();
                }
            }
        }

        return instance;
    }

    private CountryQuestion countryQuestion;

    private QuestionAmericanCountry() {
        countryQuestion = CountryQuestion.getInstance();
    }

    @Override
    public void CreateQuestion() {
        countryQuestion.CreateCountryQuestion();

        setQuestion(countryQuestion.getCountry());
        setOptions(countryQuestion.Options());
        setAnswer(countryQuestion.getCapital());
    }
}
