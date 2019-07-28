package com.shellyambar.thegame.Questions;

import java.util.Random;

public class KnowledgeQuestion {

    private static volatile KnowledgeQuestion instance;

    public static KnowledgeQuestion getInstance() {
        if (instance == null) {
            synchronized (KnowledgeQuestion.class) {
                if (instance == null) {
                    instance = new KnowledgeQuestion();
                }
            }
        }

        return instance;
    }

    private Random rand;
    private final int knowledgeAmount = 20;

    private KnowledgeQuestion() {
        rand = new Random();
    }

    public String CreateKnowledgeQuestion(){

        return "q"+Integer.toString(rand.nextInt(knowledgeAmount)+1);
    }
}

