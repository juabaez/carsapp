package com.unrc.app.models;

import org.javalite.activejdbc.Model;
/**
 *
 * @author lucho
 */
public class City extends Model {
    static {
        validatePresenceOf("name", "state", "country", "postcode");
    }
}
