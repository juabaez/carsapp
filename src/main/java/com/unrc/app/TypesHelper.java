package com.unrc.app;

public class TypesHelper {
    public static final Integer valueOf(String s){
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
