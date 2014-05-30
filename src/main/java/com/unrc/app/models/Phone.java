package com.unrc.app.models;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

public class Phone extends Model {
    static {
        validatePresenceOf("type", "num", "user_id");
    }
    
    public enum phoneType {
        personal, home, work;
    }
    
    public static LazyList<Phone> findAll(){
        return Model.findAll();
    }
    
    public Phone type(phoneType t) {
        this.set("type", t.name());
        return this;
    }
    
    public String type(){
        return this.getString("type");
    }
    
    public Phone num(int i) {
        this.set("num", i);
        return this;
    }
    
    public int num(){
        return this.getInteger("num");
    }
    
    public String toString(){
        return this.getString("type") + ": " + this.getString("num");
    }
}
