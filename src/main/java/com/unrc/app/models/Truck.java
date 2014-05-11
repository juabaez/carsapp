package com.unrc.app.models;

public class Truck extends Vehicle {
    static {
        validatePresenceOf("name","brand","year", "max_load", "user_id");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "truck");
        return super.saveIt();
    }
}
