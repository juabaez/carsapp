package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.City;
import com.unrc.app.models.Phone;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;

public class PhoneTest{
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
        Phone phone = new Phone();
        
        the(phone).shouldNotBe("valid");
        the(phone.errors().get("type")).shouldBeEqual("value is missing");
        the(phone.errors().get("num")).shouldBeEqual("value is missing");
        the(phone.errors().get("user_id")).shouldBeEqual("value is missing");
        
        city.set("name", "Rio Cuarto", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();

        user.set("first_name", "John", "last_name", "Doe", "pass", "12345", "email", "example@email.com", "address", "Street One 123");
        user.setParent(city);
        user.saveIt();
        
        phone.set("type", "Móvil", "num", "3585123456");
        phone.setParent(user);
        
        the(phone).shouldBe("valid");
    }
}
