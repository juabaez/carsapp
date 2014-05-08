package com.unrc.app;

import org.javalite.activejdbc.Base;

import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;

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

        //Alternativa 1 para crear un usuario y guardarlo en la base de datos
        User user = new User();
        user.set("first_name", "Marilyn");
        user.set("last_name", "Monroe");
        user.set("email", "mmonroe@hotmail.com");
        user.set("adress", "Peru 725");
        user.saveIt();
        user.delete();

        //Alternativa 2 para crear un usuario y guardarlo en la base de datos
        User.createIt("first_name", "Marcelo", "last_name", "Uva", "email", "muva@exa.unrc.edu.ar", "adress", "Paraguay 123");
        User.deleteUser("muva@exa.unrc.edu.ar");
        
        //Lo crea en la base de datos y ademas lo instancia
        Vehicle vehicle = Vehicle.createIt("name","Partner","brand","Peugeot","year","2011");
        vehicle.delete();
        
        Base.close();
    }
    
}
