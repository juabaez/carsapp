/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.unrc.app.models;

import org.javalite.activejdbc.Model;

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
}
