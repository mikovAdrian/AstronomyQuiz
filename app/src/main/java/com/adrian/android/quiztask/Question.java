package com.adrian.android.quiztask;

/**
 * Created by vanina on 26.04.17.
 */

public class Question {
    private int mId;
    private String mQuestion;
    private String mAnswer;
    private String mOptionOne;
    private String mOptionTow;
    private String mOptionThree;

    public Question(){

    }

    public Question(String question, String answer, String optionOne, String optionTow, String optionThree) {
        mQuestion = question;
        mAnswer = answer;
        mOptionOne = optionOne;
        mOptionTow = optionTow;
        mOptionThree = optionThree;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getOptionOne() {
        return mOptionOne;
    }

    public void setOptionOne(String optionOne) {
        mOptionOne = optionOne;
    }

    public String getOptionTow() {
        return mOptionTow;
    }

    public void setOptionTow(String optionTow) {
        mOptionTow = optionTow;
    }

    public String getOptionThree() {
        return mOptionThree;
    }

    public void setOptionThree(String optionThree) {
        mOptionThree = optionThree;
    }
}
