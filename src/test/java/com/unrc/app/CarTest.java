/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import com.unrc.app.models.Car;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.javalite.test.jspec.JSpec.the;
import static org.junit.Assert.assertEquals;

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
        the(car.errors().get("max_capacity")).shouldBeEqual("value is missing");

        car.set("name", "Ka", "brand", "Ford", "year", "2007", "max_capacity", "4");

        // Everything is good:
        the(car).shouldBe("valid");
    }
    
}
