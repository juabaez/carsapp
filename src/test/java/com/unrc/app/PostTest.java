package com.unrc.app;

import com.unrc.app.models.Post;

import org.javalite.activejdbc.Base;
import static org.javalite.test.jspec.JSpec.the;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PostTest{
    
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
        Post post = new Post();

        the(post).shouldNotBe("valid");
        the(post.errors().get("user_email")).shouldBeEqual("value is missing");
        the(post.errors().get("vehicle_id")).shouldBeEqual("value is missing");
        the(post.errors().get("text")).shouldBeEqual("value is missing");

        post.set("user_email", "example@mail.com", "vehicle_id", "1", "text", "sample content.");

        // Everything is good:
        the(post).shouldBe("valid");
    }
}
