package com.example.questionsapp;


import java.io.Serializable;

public class Question implements Serializable {

    private String question;
    private String typeOfQuestion;

    public Question()
    {
        question = "";
        typeOfQuestion = "";
    }

    public Question(String question, String typeOfQuestion) {
        this.question = question;
        this.typeOfQuestion = typeOfQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }


}
