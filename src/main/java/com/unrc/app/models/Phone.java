package com.unrc.app.models;

import org.javalite.activejdbc.Model;

public class Phone extends Model {
    static {
        validatePresenceOf("type", "num", "user_id");
    }
    
    public enum phoneType {
        personal, home, work;
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
}
