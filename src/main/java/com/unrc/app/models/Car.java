package com.unrc.app.models;

public class Car extends Vehicle {
    static {
        validatePresenceOf("name","brand","year", "passengers", "user_id");
    }

    @Override
    public boolean saveIt(){
        set("type", "car");
        return super.saveIt();
    }
}
