package com.unrc.app.models;

import java.util.LinkedList;
import java.util.List;
import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name", "pass", "email", "address", "city_id");
  }

  @Override
  public String toString() {
      return (this.getString("first_name") + " " + this.getString("last_name"));
  }
  
  public String email() {   
    return (this.getString("email"));
  }
  
  public String adress(){
      return this.getString("adress");
  }
  
  public static User findUser(String email) {
      return User.findFirst("email = ?", email);
 
  }
  
  public static boolean deleteUser(String email) {
      return User.findFirst("email = ?", email).delete();
  }
  
  public static List<User> findFrom(String city){
      City aux = City.findFirst("name = ?", city);
      return User.find("city_id = ?", aux.getId());
  }
}
