package com.unrc.app.models;

import java.util.List;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public class User extends Model {
  static {
      validatePresenceOf("first_name", "last_name", "pass", "email", "address", "city_id");
  }
  
  public boolean createUser(String first_name, String last_name, String email, String address, String pass, City city) {
      User user = new User();
      user
              .firstName(first_name)
              .lastName(last_name)
              .email(email)
              .address(address)
              .pass(pass)
              .setParent(city);
      return user.saveIt();
  }
  
  public static LazyList<User> findAll(){
      return Model.findAll();
  }
  
  public User firstName(String s) {
      this.set("first_name", s);
      return this;
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
      return (this.getString("first_name") + " " + this.getString("last_name"));
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

    public boolean addVehicle(String type, String... args){
    if ((args.length>0) && (args.length % 2 == 0)) {
        switch(type){
            case "car": 
                Car car = new Car();
                car.set(args);
                car.setParent(this);
                return car.saveIt();  
            case "truck":
                Truck truck = new Truck();
                truck.set(args);
                truck.setParent(this);
                return truck.saveIt();  
            case "bike":
                Bike bike = new Bike();
                bike.set(args);
                bike.setParent(this);
                return bike.saveIt();  
            case "other":
                Other other = new Other();
                other.set(args);
                other.setParent(this);
                return other.saveIt();  
            default:
                Vehicle vehicle = new Vehicle();
                vehicle.set(args);
                vehicle.setParent(this);
                return vehicle.saveIt();
        }
    } else return false;
    }
}
