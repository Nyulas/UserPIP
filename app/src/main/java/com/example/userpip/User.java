package com.example.userpip;

public class User {

    private int ID;
    private String Name;
    private int Vote_value;
    private Question Question;

    public User(){ }

    public User(String name,int ID){
        this.Name=name;
        this.ID=ID;
    }

    public User(String Name,int ID, int vote_value, Question question) {
        this.Name = Name;
        this.ID = ID;
        Vote_value = vote_value;
        this.Question = question;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getVote_value() {
        return Vote_value;
    }

    public void setVote_value(int vote_value) {
        Vote_value = vote_value;
    }

    public Question getQuestion() {
        return Question;
    }

    public void setQuestion(Question question) {
        this.Question = question;
    }
}
