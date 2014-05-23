package com.unrc.app.models;

public class Truck extends Vehicle {
    static {
        validatePresenceOf("name","brand","year", "plate", "max_load", "user_id");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "truck");
        return super.saveIt();
    }
    
    public Truck maxLoad(int i) {
        this.set("max_load", i);
        return this;
    }
    
    public int maxLoad() {
        return this.getInteger("max_load");
    }
}
