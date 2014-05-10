/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unrc.app.models;

import org.javalite.activejdbc.Model;
import java.util.List;

/**
 *
 * @author lucho
 */
public class Vehicle extends Model {
  static {
      validatePresenceOf("name","brand","year");
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
  
  public static List<Vehicle> filter(String name, String brand, String year){
      String query = "";
      List<Vehicle> resultado;
      if (name != null) {
          query += "name = '" + name + "' ";
      }
      if (brand != null) {
          query += " AND brand = '" + brand + "'";
      }
      if (year != null) {
          query += " AND year = '" + year + "'";
      }
      resultado = Vehicle.find(query);
      return resultado;
  }
}
