/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unrc.app;

import com.unrc.app.models.Vehicle;

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
public class VehicleTest {
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
        Vehicle vehicle = new Vehicle();

        the(vehicle).shouldNotBe("valid");
        the(vehicle.errors().get("name")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("brand")).shouldBeEqual("value is missing");
        the(vehicle.errors().get("year")).shouldBeEqual("value is missing");

        vehicle.set("name", "Partner", "brand", "Peugeot", "year", "2011");

        // Everything is good:
        the(vehicle).shouldBe("valid");
    }
    
}
