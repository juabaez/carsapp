package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name","email","adress", "password", "city_postcode");
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
}
