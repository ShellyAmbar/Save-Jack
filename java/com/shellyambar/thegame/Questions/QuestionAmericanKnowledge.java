package com.shellyambar.thegame.Questions;

public class QuestionAmericanKnowledge extends QuestionAmerican {

    private static volatile QuestionAmericanKnowledge instance;

    public static QuestionAmericanKnowledge getInstance() {
        if (instance == null) {
            synchronized (QuestionAmericanKnowledge.class) {
                if (instance == null) {
                    instance = new QuestionAmericanKnowledge();
                }
            }
        }

        return instance;
    }

    private KnowledgeQuestion knowledgeQuestion;

    private QuestionAmericanKnowledge() {
        knowledgeQuestion = KnowledgeQuestion.getInstance();
    }

    @Override
    public void CreateQuestion() {
        setQuestion(knowledgeQuestion.CreateKnowledgeQuestion());
    }


}

