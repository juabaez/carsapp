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
public class Car extends Vehicle {
  static {
      validatePresenceOf("max_capacity");
  }
}
