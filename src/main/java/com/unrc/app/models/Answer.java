package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Answer extends Model {
    static {
        validatePresenceOf("answer", "question_id");
    }
    
    public Answer answer(String s){
        this.set("answer", s);
        return this;
    }
    
    public String answer(){
        return this.getString("answer");
    }
}
