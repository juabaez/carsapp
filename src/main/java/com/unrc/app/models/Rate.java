package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Rate extends Model {
    static {
        validatePresenceOf("rate", "post_id", "user_id");
    }
}
