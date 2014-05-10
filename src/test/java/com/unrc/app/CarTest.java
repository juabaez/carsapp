package com.unrc.app;

import com.unrc.app.models.Car;
import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lucho
 */
public class CarTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("CarTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("CarTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        Car car = new Car();
        
        the(car).shouldNotBe("valid");
        the(car.errors().get("name")).shouldBeEqual("value is missing");
        the(car.errors().get("brand")).shouldBeEqual("value is missing");
        the(car.errors().get("year")).shouldBeEqual("value is missing");
        the(car.errors().get("passengers")).shouldBeEqual("value is missing");

        car.set("user_id", "1", "name", "Partner", "brand", "Peugeot", "year", "2011", "passengers", "4");
        car.saveIt();
                
        the(car).shouldBe("valid");
    }
    
    @Test
    public void oneCarBelongsToUser(){
        User user = new User();
        Car car = new Car();
        
        the(car).shouldNotBe("valid");
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123", "city_postcode", "4800");
        user.saveIt();
        
        car.set("name", "Partner", "brand", "Peugeot", "year", "2011", "passengers", "4");
        car.setParent(user);
        car.saveIt();
        
        
        the(car).shouldBe("valid");
    }
}
