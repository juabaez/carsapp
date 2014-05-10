package com.unrc.app;

import com.unrc.app.models.Bike;
import com.unrc.app.models.User;

import static org.javalite.test.jspec.JSpec.the;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author lucho
 */
public class BikeTest {
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
        Bike bike = new Bike();

        the(bike).shouldNotBe("valid");
        the(bike.errors().get("name")).shouldBeEqual("value is missing");
        the(bike.errors().get("brand")).shouldBeEqual("value is missing");
        the(bike.errors().get("year")).shouldBeEqual("value is missing");
        the(bike.errors().get("displacement")).shouldBeEqual("value is missing");

        bike.set("name", "XTZ", "brand", "Yamaha", "year", "2007", "displacement", "250");

        // Everything is good:
        the(bike).shouldBe("valid");
    }
    
    @Test
    public void oneBikeBelongsToUser(){
        User user = new User();
        Bike bike = new Bike();
        
        the(bike).shouldNotBe("valid");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123", "city_postcode", "4800");
        user.saveIt();
        
        bike.set("name", "XTZ", "brand", "Yamaha", "year", "2007", "displacement", "250");
        bike.setParent(user);
        bike.saveIt();
        
        
        the(bike).shouldBe("valid");
    }
}
