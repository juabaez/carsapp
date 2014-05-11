package com.unrc.app.models;

public class Other extends Vehicle {
    static {
        validatePresenceOf("name", "brand", "year", "user_id", "passengers");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "other");
        return super.saveIt();
    }
}
