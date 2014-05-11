package com.unrc.app;

import com.unrc.app.models.City;
import com.unrc.app.models.Post;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Rate;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RateTest{
    
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("PostTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("PostTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        Vehicle vehicle = new Vehicle();
        Post post = new Post();
        City city = new City();
        Rate rate = new Rate();
        
        city.set("name", "Rio IV", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();
        
        the(rate).shouldNotBe("valid");
        the(rate.errors().get("rate")).shouldBeEqual("value is missing");
        the(rate.errors().get("user_id")).shouldBeEqual("value is missing");
        the(rate.errors().get("post_id")).shouldBeEqual("value is missing");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123");
        user.setParent(city);
        user.saveIt();
        
        vehicle.set("name", "Partner", "brand", "Peugeot", "year", "2011");
        vehicle.setParent(user);
        vehicle.saveIt();
        
        post.setParent(user);
        post.setParent(vehicle);
        post.set("text", "Vendo Peugeot Partner 2011");
        post.saveIt();
        
        rate.set("rate", "10");
        rate.setParents(user, post);
        
        the(rate).shouldBe("valid");
    }
}