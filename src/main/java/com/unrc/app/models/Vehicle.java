package com.unrc.app.models;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public class Vehicle extends Model {
  static {
      validatePresenceOf("name", "brand", "year", "user_id");
  }
  
  @Override
  public String toString() {
      return (this.getString("name") + " " + this.getString("brand") + "(" + this.getString("year")+ ")");
  } 
  
  @Override
  public boolean saveIt(){
      if (this.get("type") == null) {
          this.set("type", "other");
      }
      return super.saveIt();
  }
  
  public String name(){
      return this.getString("name");
  }
  
  public String brand(){
      return this.getString("brand");
  }
  
  public int year(){
      return this.getInteger("year");
  }
  
  public static List<Vehicle> filter(String... args){
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
          return Vehicle.find(query);
      } else return null;
  }
  
  public static List<Vehicle> vehiclesFrom(String city) {
      City aux = City.findFirst("name = ?", city);
      List<User> users = aux.getAll(User.class);
      List<Vehicle> vehicles = new LinkedList();
      for(User u : users) {
          vehicles.addAll(u.getAll(Vehicle.class));
      }
      return vehicles;
  }
}
