package com.unrc.app.models;

import org.javalite.activejdbc.Model;

/**
 *
 * @author lucho
 */
public class Truck extends Vehicle {
    static {
        validatePresenceOf("name","brand","year", "max_load");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "truck");
        return super.saveIt();
    }
}
