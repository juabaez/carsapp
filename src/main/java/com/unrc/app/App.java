package com.unrc.app;

import com.unrc.app.models.Administrator;
import com.unrc.app.models.Bike;
import com.unrc.app.models.Car;
import com.unrc.app.models.City;
import com.unrc.app.models.Other;
import com.unrc.app.models.Phone;
import com.unrc.app.models.Post;
import com.unrc.app.models.Truck;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import static org.javalite.test.jspec.JSpec.the;
import spark.Spark;
import static spark.Spark.*;

public class App 
{
    public static void main(String[] args)
    { 
        externalStaticFileLocation("./public"); // Static files 
        
        Spark.before((request, response) -> {
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });
        
        Spark.after((request, response) -> {
            Base.close();
        });
        
        
        get("/users", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Usuarios</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(User u : User.findAll()) {
                body += "<tr><td>" + u.toString() + "</td><td>[" + u.email() +"]</td></tr> \n";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/admins", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Administradores</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(Administrator admin : Administrator.findAll()) {
                body += "<tr><td>" + admin.toString() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/cities", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Ciudades</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(City c : City.findAll()) {
                body += "<tr><td>" + c.toString() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/phones", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Telefonos</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(Phone p : Phone.findAll()) {
                body += "<tr><td>" + p.type() + "</td><td>" + p.num() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/vehicles", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Vehiculos</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(Vehicle v : Vehicle.findAll()) {
                body += "<tr><td>" + v.toString() + "</td></tr>";
            }
            body += "</table</span></body> \n";
            return body;
        });
        
        
        get("/posts", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td><b>Publicaciones</b></td><td>Autor</td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(Post p : Post.findAll()) {
                body += "<tr><td>" + p.toString() + "</td><td>" + p.author() + "</td></tr>";
            }
            body += "</table</span></body> \n";
            return body;
        });
        
        
        get("/users/from/:zip", (request, response) -> {
            String zip = request.params(":zip");
            City c = City.findFirst("postcode = ?", zip);
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Usuarios de " + c.toString() + "</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            for(User u : c.getAll(User.class)) {
                body += "<tr><td>" + u.toString() + "</td><td>[" + u.email() +"]</td></tr> \n";
            } 
            body += "</table</span></body> \n";
            return body;
        });
        
        
        post("/users", (request, response) -> {
            String first_name = request.queryParams("first_name");
            String last_name = request.queryParams("last_name");
            String pass = request.queryParams("pass");
            String email = request.queryParams("email");
            String address = request.queryParams("address");
            User u = new User();
            u
                    .firstName(first_name)
                    .lastName(last_name)
                    .email(email)
                    .address(address)
                    .pass(pass)
                    .set("city_id", "1");
            boolean exit = u.saveIt();
            String body;
            if (exit) body = "Usuario correctamente registrado!";
            else body = "El registro no pudo completarse.";
            body += "<input type=button onclick=\"javascript: history.back()\" value=\"Atras\">";
            return body;
        });
        
        
        post("/cities", (request, response) -> {
            String name = request.queryParams("name");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String postcode = request.queryParams("postcode");
            City c = new City();
            c
                    .name(name)
                    .state(state)
                    .country(country)
                    .postcode(postcode);
            boolean exit = c.saveIt();
            String body;
            if (exit) body = "Ciudad correctamente registrada!";
            else body = "El registro no pudo completarse.";
            return body;
        });
        
        
        post("/vehicles", (request, response) -> {
            boolean exit = false;
            String name = request.queryParams("name");
            int year = Integer.getInteger(request.queryParams("year"));
            String brand = request.queryParams("brand");
            String plate = request.queryParams("plate");
            String type = request.queryParams("type");
            switch(type) {
                case "car":
                    int passengers = Integer.getInteger(request.queryParams("passengers"));
                    Car c = new Car();
                    c
                        .passengers(passengers)
                        .name(name)
                        .year(year)
                        .brand(brand)
                        .plate(plate);
                    exit = c.saveIt();
                    break;
                case "bike":
                    int displacement = Integer.getInteger(request.queryParams("displacement"));
                    Bike b = new Bike();
                    b
                        .displacement(displacement)
                        .name(name)
                        .year(year)
                        .brand(brand)
                        .plate(plate);
                    exit = b.saveIt();
                    break;
                case "truck":
                    int max_load = Integer.getInteger(request.queryParams("max_load"));
                    Truck t = new Truck();
                    t
                        .maxLoad(max_load)
                        .name(name)
                        .year(year)
                        .brand(brand)
                        .plate(plate);
                    exit = t.saveIt();
                    break;
                case "other":
                    Other o = new Other();
                    o
                        .name(name)
                        .year(year)
                        .brand(brand)
                        .plate(plate);
                    exit = o.saveIt();
                    break;
            }
            String body;
            if (exit) body = "Vehiculo correctamente registrado!";
            else body = "El registro no pudo completarse.";
            body += "<input type=button onclick=\"javascript: history.back()\" value=\"Atras\">";
            return body;
        });
    }
}
