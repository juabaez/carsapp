package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Phone extends Model {
    static {
        validatePresenceOf("type", "num", "user_id");
    }
}
