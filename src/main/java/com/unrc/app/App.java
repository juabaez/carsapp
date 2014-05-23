package com.unrc.app;

import com.unrc.app.models.City;
import com.unrc.app.models.User;
import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import static org.javalite.test.jspec.JSpec.the;
import spark.Spark;
import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        System.out.println("Hola mundo cruel!");
        /********************************************************************/   
//            
//        Spark.before(null);
//        Spark.after(null);
        
        get("/hello", (request, response) -> {
            String ua = "lucho";
            return ua;
      });
        
        get("/users", (request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
            String body = "<b><h2>Usuarios:</h2></b><br>";
            for(User u : User.findAll()) {
                body += "<font color=\"blue\">" + u.toString() + "\temail:" + u.email() +"</font><br>";
            }
            Base.close();
            return body;
      });
        
        
        
        
        /********************************************************************/
    }
    
}
