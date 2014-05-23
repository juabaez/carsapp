package com.unrc.app;

import com.unrc.app.models.City;
import org.javalite.activejdbc.Base;

import com.unrc.app.models.User;
import java.util.List;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Car;
import org.javalite.activejdbc.Model;
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
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        Base.openTransaction();
        /********************************************************************/

        City city = new City();
        city.set("name", "Rio Cuarto", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();
        
        User user = new User();
        user
            .firstName("Lucho")
            .lastName("Rocha")
            .email("rocha.lucho@gmail.com")
            .pass("123456")
            .address("Peru 725");
        user.setParent(city);
        user.saveIt();
        
        Car car = new Car();
        car.set("name", "Ka", "brand", "Ford", "year", "2007", "passengers", "4");
        car.setParent(user);
        car.saveIt();
        
        
        City city2 = new City();
        city2.set("name", "Rio Tercero", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city2.saveIt();
        
        User user2 = new User();
        user2.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example2222@email.com", "address", "Street One 123");
        user2.setParent(city2);
        user2.saveIt();
        
        Car car2 = new Car();
        car2.set("name", "Ka222", "brand", "Ford222", "year", "2007", "passengers", "4");
        car2.setParent(user2);
        car2.saveIt();
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        List<Vehicle> lista = Vehicle.filter("brand", "Ford");
        System.out.println(lista.toString());
        System.out.println(Vehicle.vehiclesFrom("Rio Tercero"));
        
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        
              get("/hello", (request, response) -> {
         return "Hello World!";
      });
        /********************************************************************/
        Base.rollbackTransaction();
        Base.close();
    }
    
}
