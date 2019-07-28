package com.shellyambar.thegame.Questions;

public class QuestionAmericanMath extends QuestionAmerican {

    private static volatile QuestionAmericanMath instance;

    public static QuestionAmericanMath getInstance() {
        if (instance == null) {
            synchronized (QuestionAmericanMath.class) {
                if (instance == null) {
                    instance = new QuestionAmericanMath();
                }
            }
        }

        return instance;
    }


    private MathQuestion mathQuestion;

    public MathQuestion getMathQuestion() {
        return mathQuestion;
    }

    private QuestionAmericanMath() {
        mathQuestion = MathQuestion.getInstance();
    }

    @Override
    public void CreateQuestion(){
        mathQuestion.CreateQuestionMath();

        setQuestion(mathAmericanQuestionStr());
        setOptions(mathQuestion.Options());
        setAnswer(mathQuestion.Answer());
    }

    private String mathAmericanQuestionStr() {
        return String.format("%d %s %d = ?", mathQuestion.getArgL(),
                mathQuestion.OperationToStr(), mathQuestion.getArgR());
    }
}


