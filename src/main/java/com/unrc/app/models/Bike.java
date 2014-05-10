package com.unrc.app.models;

import org.javalite.activejdbc.Model;
/**
 *
 * @author lucho
 */
public class Bike extends Vehicle {
    static {
        validatePresenceOf("name","brand","year", "displacement");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "bike");
        return super.saveIt();
    }
}
