package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Question extends Model {
    static {
        validatePresenceOf("question", "post_id", "user_id");
    }
  
  public boolean addAnswer(String text, User user) {
      Answer answer = new Answer();
      answer.set("question", text);
      answer.setParents(user, this);
      return answer.saveIt();
  }
}
