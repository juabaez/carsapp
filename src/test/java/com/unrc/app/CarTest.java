package com.unrc.app;

import com.unrc.app.models.Car;
import com.unrc.app.models.City;
import com.unrc.app.models.User;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        User user = new User();
        Car car = new Car();
        City city = new City();
        
        city.set("name", "Rio IV", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();
        
        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123");
        user.setParent(city);
        user.saveIt();

        the(car).shouldNotBe("valid");
        the(car.errors().get("name")).shouldBeEqual("value is missing");
        the(car.errors().get("brand")).shouldBeEqual("value is missing");
        the(car.errors().get("year")).shouldBeEqual("value is missing");
        the(car.errors().get("passengers")).shouldBeEqual("value is missing");

        car.set("name", "Ka", "brand", "Ford", "year", "2007", "passengers", "4");
        car.setParent(user);
        
        the(car).shouldBe("valid");
    }
}
