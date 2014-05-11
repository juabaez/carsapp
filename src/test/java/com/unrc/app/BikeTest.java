package com.unrc.app;

import com.unrc.app.models.Bike;
import com.unrc.app.models.User;
import com.unrc.app.models.City;

import static org.javalite.test.jspec.JSpec.the;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BikeTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("BikeTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("BikeTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        Bike bike = new Bike();
        City city = new City();
        
        city.set("name", "Rio IV", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123");
        user.setParent(city);
        user.saveIt();
        
        the(bike).shouldNotBe("valid");
        the(bike.errors().get("name")).shouldBeEqual("value is missing");
        the(bike.errors().get("brand")).shouldBeEqual("value is missing");
        the(bike.errors().get("year")).shouldBeEqual("value is missing");
        the(bike.errors().get("displacement")).shouldBeEqual("value is missing");

        bike.set("name", "XTZ", "brand", "Yamaha", "year", "2007", "displacement", "250");
        bike.setParent(user);
        
        the(bike).shouldBe("valid");
    }
}
