package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {
      validatePresenceOf("user_id", "vehicle_id", "text");
  }
  
  public boolean addQuestion(String text) {
      Question question = new Question();
      question.set("question", text);
      question.setParents(this.parent(User.class), this.parent(Vehicle.class));
      return question.saveIt();
  }
}