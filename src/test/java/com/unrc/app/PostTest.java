package com.unrc.app;

import com.unrc.app.models.Post;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostTest{
    
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
        Post post = new Post();

        the(post).shouldNotBe("valid");
        the(post.errors().get("user_id")).shouldBeEqual("value is missing");
        the(post.errors().get("vehicle_id")).shouldBeEqual("value is missing");
        the(post.errors().get("text")).shouldBeEqual("value is missing");

        post.set("user_id", "1", "vehicle_id", "1", "text", "sample content.");

        // Everything is good:
        the(post).shouldBe("valid");
    }
    
    @Test
    public void onePostBelongsToUser(){
        User user = new User();
        Vehicle vehicle = new Vehicle();
        Post post = new Post();
        
        the(post).shouldNotBe("valid");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123", "city_postcode", "4800");
        user.saveIt();
        
        vehicle.set("name", "Partner", "brand", "Peugeot", "year", "2011");
        vehicle.setParent(user);
        vehicle.saveIt();
        
        post.setParent(user);
        post.setParent(vehicle);
        post.set("text", "Vendo Peugeot Partner 2011");
        post.saveIt();
        
        the(post).shouldBe("valid");
    }
}
