package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Administrator extends Model {
    static {
        validatePresenceOf("pass", "email");
    }
    
    public boolean createAdmin(String email, String pass){
        Administrator admin = new Administrator();
        admin
                .email("email")
                .pass("pass");
        return admin.saveIt();
    }
    
    public Administrator email(String s){
        this.set("email", s);
        return this;
    }
    
    public Administrator pass(String s){
        this.set("pass", s);
        return this;
    }
    
    public String email(){
        return this.get("email").toString();
    }
    
    public String pass(){
        return this.get("pass").toString();
    }
    
    public static boolean deleteUser(String email) {
        return User.findFirst("email = ?", email).delete();
    }
    
    public Post editPost(Post post, String... args){
        if ((args.length>2) && (args.length % 2 == 0)) {
            post.set(args);
        }
        return post;
    }
}
