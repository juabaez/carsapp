package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Post extends Model {
  static {
      validatePresenceOf("user_email", "vehicle_id", "text");
  }
}