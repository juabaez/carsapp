package com.unrc.app.models;

public class Other extends Vehicle {
    static {
        validatePresenceOf("name", "brand", "year", "plate", "user_id");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "other");
        return super.saveIt();
    }
}
