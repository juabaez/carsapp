package com.unrc.app.models;

import java.util.List;
import org.javalite.activejdbc.Model;

public class Post extends Model {
    static {
        validatePresenceOf("user_id", "vehicle_id", "text", "price");
    }

    public Post text(String s){
        this.set("text", s);
        return this;
    }

    @Override
    public String toString(){
        return this.getString("text");
    }

    public String text(){
        return this.getString("text");
    }

    public String author(){
        User u = User.filter("id", this.getString("user_id")).get(0);
        return u.firstName() + " " + u.lastName();
    }

    public Post price(int i) {
        this.set("price", i);
        return this;
    }

    public int price(){
        return this.getInteger("price");
    }
    
    public String vehicle(){
        return Vehicle.findById(this.get("vehicle_id")).toString();
    }

    public boolean addQuestion(String text, User user) {
        Question question = new Question();
        question.set("question", text);
        question.setParents(user, this);
        return question.saveIt();
    }

    public List<Question> questions(){
        return Question.find("post_id = ?", this.getId());
    }

    public Question question(int i) {
        return Question.findById(i);
    }

    public static List<Post> filter(String... args){
        if ((args.length>0) && (args.length % 2 == 0)) {
            String query = "";
            String attribute;
            String value;
            int i = 0;
            attribute = args[i];
            i++;
            value = args[i];
            i++;
            query += attribute + " = '" + value + "'";
            while (i<args.length-1) {
                attribute = args[i];
                i++;
                value = args[i];
                i++;
                query += " AND " + attribute + " = '" + value + "'";
            }
            return Post.find(query);
        } else return null;
    }
  
    public static List<Post> all(){
        return Post.findAll();
    }
}