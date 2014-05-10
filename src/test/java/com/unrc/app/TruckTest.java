package com.unrc.app;

import com.unrc.app.models.Truck;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;

import static org.javalite.test.jspec.JSpec.the;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author lucho
 */
public class TruckTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("VehicleTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("VehicleTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Truck truck = new Truck();

        the(truck).shouldNotBe("valid");
        the(truck.errors().get("name")).shouldBeEqual("value is missing");
        the(truck.errors().get("year")).shouldBeEqual("value is missing");
        the(truck.errors().get("brand")).shouldBeEqual("value is missing");
        the(truck.errors().get("max_load")).shouldBeEqual("value is missing");

        truck.set("name", "Actross", "brand", "Volvo", "year", "2012", "max_load", "50");

        // Everything is good:
        the(truck).shouldBe("valid");
    }
    
    @Test
    public void oneTruckBelongsToUser(){
        User user = new User();
        Truck truck = new Truck();
        
        the(truck).shouldNotBe("valid");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123", "city_postcode", "4800");
        user.saveIt();
        
        truck.set("name", "Actross", "brand", "Volvo", "year", "2012", "max_load", "50");
        truck.setParent(user);
        the(truck).shouldBe("valid");
        
        truck.saveIt();
        
        the(truck.parent(User.class).getId()).shouldEqual(user.getId());
    }
}
