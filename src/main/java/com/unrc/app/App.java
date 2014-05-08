package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Hola mundo cruel!");

        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");

        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Monroe");
        user.set("email", "mmonroe@hotmail.com");
        user.saveIt();
        
        System.out.println(user.toString());

        User.createIt("first_name", "Marcelo", "last_name", "Uva", "email", "muva@exa.unrc.edu.ar");

        Base.close();
    }
}
