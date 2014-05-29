package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {
    static {
        validatePresenceOf("question", "post_id", "user_id");
    }
  
  public boolean addAnswer(String text, User user) {
      Answer answer = new Answer();
      answer.answer(text);
      answer.setParents(user, this);
      return answer.saveIt();
  }
  
  public String question(){
      return this.getString("question");
  }
  
  public Question question(String s){
      this.set("question", s);
      return this;
  }
}
