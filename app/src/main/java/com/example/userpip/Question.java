package com.example.userpip;

public class Question {

    private String Question;
    private boolean State;

    public Question(String question, boolean state) {
        this.Question = question;
        this.State = state;
    }
    public Question(){}

    public void setState(boolean state) {
        State = state;
    }

    public boolean getState(){return State;}

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

}
