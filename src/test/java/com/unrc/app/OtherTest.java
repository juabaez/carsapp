package com.unrc.app;

import com.unrc.app.models.Other;
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
public class OtherTest {
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
        Other other = new Other();

        the(other).shouldNotBe("valid");
        the(other.errors().get("name")).shouldBeEqual("value is missing");
        the(other.errors().get("year")).shouldBeEqual("value is missing");
        the(other.errors().get("brand")).shouldBeEqual("value is missing");

        other.set("name", "737", "brand", "Boeing", "year", "2000");

        // Everything is good:
        the(other).shouldBe("valid");
    }
    
    @Test
    public void oneOtherBelongsToUser(){
        User user = new User();
        Other other = new Other();
        
        the(other).shouldNotBe("valid");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123", "city_postcode", "4800");
        user.saveIt();
        
        other.set("name", "737", "brand", "Boeing", "year", "2000");
        other.setParent(user);
        other.saveIt();
        
        
        the(other).shouldBe("valid");
    }
}
