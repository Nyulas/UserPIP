package com.example.userpip;

import java.io.Serializable;

public class User implements Serializable {

    private int ID;
    private String Name;
    private String Vote_value;
    //private Question Question;

    public User(){ }

    public User(String name,int ID){
        this.Name=name;
        this.ID=ID;
    }

    public User(String Name,int ID, String vote_value){//, Question question) {
        this.Name = Name;
        this.ID = ID;
        Vote_value = vote_value;
        //this.Question = question;
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

    public String getVote_value() {
        return Vote_value;
    }

    public void setVote_value(String vote_value) {
        Vote_value = vote_value;
    }

    /*public Question getQuestion() {
        return Question;
    }

     */

    /*public void setQuestion(Question question) {
        this.Question = question;
    }
     */
}
