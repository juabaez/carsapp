package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.City;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class UserTest{
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("UserTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("UserTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        City city = new City();
        
        city.set("name", "Rio Cuarto", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();

        the(user).shouldNotBe("valid");
        the(user.errors().get("first_name")).shouldBeEqual("value is missing");
        the(user.errors().get("last_name")).shouldBeEqual("value is missing");
        the(user.errors().get("pass")).shouldBeEqual("value is missing");
        the(user.errors().get("email")).shouldBeEqual("value is missing");
        the(user.errors().get("address")).shouldBeEqual("value is missing");
        the(user.errors().get("city_id")).shouldBeEqual("value is missing");

        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123");
        user.setParent(city);
        
        the(user).shouldBe("valid");
    }
}
