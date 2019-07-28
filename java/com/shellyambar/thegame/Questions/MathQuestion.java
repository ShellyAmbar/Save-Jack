package com.shellyambar.thegame.Questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MathQuestion {

    enum MathOperation
    {
        Mul, Sub, Add
    }

    private static volatile MathQuestion instance;

    public static MathQuestion getInstance() {
        if (instance == null) {
            synchronized (MathQuestion.class) {
                if (instance == null) {
                    instance = new MathQuestion();
                }
            }
        }

        return instance;
    }



    private  int MaxInt ;
    private final int  startInt ;
    private Random rand;
    private int argL;
    private int argR;
    private int result;
    private MathOperation operation;

    private MathQuestion() {

        rand = new Random();
        startInt = 50;
    }


    public void setMaxInt(int numOfLevel) {
        MaxInt=numOfLevel * startInt;
    }

    public int getMaxInt() {
        return MaxInt;
    }

    public void CreateQuestionMath(){
        argL = rand.nextInt(MaxInt) + 1;
        argR = rand.nextInt(MaxInt) + 1;
        operation = setOperation();
        result = setAnswer();
    }

    private MathOperation setOperation() {
        int randomOperation = rand.nextInt(MathOperation.values().length);

        return MathOperation.values()[randomOperation];
    }

    private int setAnswer() {
        int result = 0;

        switch (operation)
        {
            case Mul:
                argL /= 10;
                argR /= 10;
                result = argL * argR;
                break;
            case Sub:
                result = argL - argR;
                break;
            case Add:
                result = argL + argR;
                break;
        }

        return result;
    }

    public ArrayList<String> Options() {
        ArrayList<String> options = new ArrayList<>();

        options.add(Integer.toString(result));

        addWrongOption(options);
        addWrongOption(options);
        addWrongOption(options);

        Collections.shuffle(options);

        return options;
    }

    public String OperationToStr(){
        String operationStr = "";

        switch (operation) {
            case Mul:
                operationStr = "*";
                break;
            case Sub:
                operationStr = "-";
                break;
            case Add:
                operationStr = "+";
                break;
        }

        return operationStr;
    }

    public String Answer(){
        return Integer.toString(result);
    }

    private void addWrongOption(ArrayList<String> options) {
        int number = rand.nextInt(MaxInt) + 1;
        String numberStr = Integer.toString(number);

        if(number == result || options.contains(numberStr)) {
            addWrongOption(options);
        }
        else {
            options.add(numberStr);
        }
    }

    public int getResult() {
        return result;
    }

    public int getArgR() {
        return argR;
    }

    public int getArgL() {
        return argL;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

