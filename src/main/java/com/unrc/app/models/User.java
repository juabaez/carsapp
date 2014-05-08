package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name","email","adress");
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
}
