package com.unrc.app.models;

public class Bike extends Vehicle {
    static {
        validatePresenceOf("name", "brand", "year", "displacement", "user_id");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "bike");
        return super.saveIt();
    }
}
