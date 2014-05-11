package com.unrc.app;

import com.unrc.app.models.User;
import com.unrc.app.models.City;
import com.unrc.app.models.Post;
import com.unrc.app.models.Vehicle;
import com.unrc.app.models.Question;

import static org.javalite.test.jspec.JSpec.the;
import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class QuestionTest {
    @Before
    public void before(){
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_test", "root", "");
        System.out.println("QuestionTest setup");
        Base.openTransaction();
    }

    @After
    public void after(){
        System.out.println("QuestionTest tearDown");
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public void shouldValidateMandatoryFields(){
        User user = new User();
        Vehicle vehicle = new Vehicle();
        Post post = new Post();
        City city = new City();
        Question question = new Question();
        
        city.set("name", "Rio IV", "state", "Cordoba", "country", "Argentina", "postcode", "5800");
        city.saveIt();
        
        the(question).shouldNotBe("valid");
        the(question.errors().get("question")).shouldBeEqual("value is missing");
        the(question.errors().get("post_id")).shouldBeEqual("value is missing");
        the(question.errors().get("user_id")).shouldBeEqual("value is missing");
        
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
        
        question.set("question", "Me lo vendes?");
        question.setParents(user, post);
        question.saveIt();
        
        the(question).shouldBe("valid");
    }
}
