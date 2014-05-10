package com.unrc.app.models;

import org.javalite.activejdbc.Model;

/**
 *
 * @author lucho
 */
public class Other extends Vehicle {
    static {
        validatePresenceOf("name","brand","year");
    }

    @Override
    public boolean saveIt(){
        super.set("type", "other");
        return super.saveIt();
    }
}
