package com.unrc.app.models;

import com.unrc.app.ElasticSearch;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Model;

public class User extends Model {
    static {
        validatePresenceOf("first_name", "last_name", "pass", "email", "address", "city_id");
    }
  
    public static List<User> all(){
        return User.findAll();
    }
    
    @Override
    public boolean saveIt(){
        return super.saveIt();
    }

    public User firstName(String s) {
        this.set("first_name", s);
        return this;
    }
  
    @Override
    public void afterCreate(){
        super.afterCreate();
        Map<String, Object> json = new HashMap<>();
        json.put("name", this.toString());
        json.put("email", this.get("email"));

        ElasticSearch.client().prepareIndex("users", "user", this.getId().toString())
                    .setSource(json)
                    .execute()
                    .actionGet();
    }

    public User lastName(String s) {
        this.set("last_name", s);
        return this;
    }

    public User email(String s) {
        this.set("email", s);
        return this;
    }

    public User address(String s) {
        this.set("address", s);
        return this;
    }

    public User pass(String s) {
        this.set("pass", s);
        return this;
    }

    @Override
    public String toString() {
        return (this.firstName() + " " + this.lastName());
    }

    public String firstName(){
        return this.getString("first_name");
    }

    public String lastName(){
        return this.getString("last_name");
    }

    public String email() {   
      return this.getString("email");
    }

    public String address(){
        return this.getString("adress");
    }

    public static User findByEmail(String email) {
        return User.findFirst("email = ?", email);
    }

    public static List<User> filter(String... args){
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
            return User.find(query);
        } else return null;
    }

    public String pass() {
        return this.getString("pass");
    }
}
