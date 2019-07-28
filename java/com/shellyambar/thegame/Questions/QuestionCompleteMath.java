package com.shellyambar.thegame.Questions;
public class QuestionCompleteMath extends QuestionComplete {

    private static volatile QuestionCompleteMath instance;

    public static QuestionCompleteMath getInstance() {
        if (instance == null) {
            synchronized (QuestionCompleteMath.class) {
                if (instance == null) {
                    instance = new QuestionCompleteMath();
                }
            }
        }

        return instance;
    }

    private MathQuestion mathQuestion;

    private QuestionCompleteMath() {
        mathQuestion = MathQuestion.getInstance();
    }

    public MathQuestion getMathQuestion() {
        return mathQuestion;
    }

    @Override
    public void CreateQuestion() {
        mathQuestion.CreateQuestionMath();

        setQuestion(startQuestionCompleteStr());
        setQuestionEnd(endQuestionCompleteStr());

        mathQuestion.setResult(mathQuestion.getArgR());

        setOptions(mathQuestion.Options());
        setAnswer(mathQuestion.Answer());
    }

    private String startQuestionCompleteStr() {
        return String.format("%d %s", mathQuestion.getArgL(),
                mathQuestion.OperationToStr());
    }

    private String endQuestionCompleteStr() {
        return String.format("= %d", mathQuestion.getResult());
    }
}
