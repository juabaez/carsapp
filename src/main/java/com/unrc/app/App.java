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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import org.javalite.activejdbc.Base;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
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
        
        get("/users", 
            (request, response) -> {
                String p = request.queryParams("page");
                String upp = request.queryParams("upp");
                
                int page = p != null ? Integer.valueOf(p) : 0;
                int usersPerPage = upp != null? Integer.valueOf(upp) : 0;
                int min = (page - 1) * usersPerPage + 1;
                int max = page * usersPerPage;
                
                Map<String, Object> attributes = new HashMap<>();
                List<User> users = User.fromXtoY(min, max);
                
                attributes.put("users_count", users.size());
                attributes.put("users", users);
                
                return new ModelAndView(attributes, "./moustache/users.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/newuser", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<City> cities = City.all();
                
                attributes.put("cities_count", cities.size());
                attributes.put("cities", cities);
                
                return new ModelAndView(attributes, "./moustache/newuser.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/newcity", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<City> cities = City.all();
                
                attributes.put("cities_count", cities.size());
                attributes.put("cities", cities);
                
                return new ModelAndView(attributes, "./moustache/newcity.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        
        get("/admins", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Administradores</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            List<Administrator> admins = Administrator.all();
            for(Administrator admin : admins) {
                body += "<tr><td>" + admin.toString() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/cities", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Ciudades</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            List<City> cities = City.all();
            for(City c : cities) {
                body += "<tr><td>" + c.toString() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/phones", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Telefonos</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            List<Phone> phones = Phone.all();
            for(Phone p : phones) {
                body += "<tr><td>" + p.type() + "</td><td>" + p.num() + "</td></tr>";
            }
            body += "</span></table></body> \n";
            return body;
        });
        
        
        get("/vehicles", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td colspan=\"3\"><b>Vehiculos</b></td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            List<Vehicle> vehicles = (List<Vehicle>) Vehicle.all();
            for(Vehicle v : (List<Vehicle>)vehicles) {
                body += "<tr><td>" + v.toString() + "</td></tr>";
            }
            body += "</table</span></body> \n";
            return body;
        });
        
        
        get("/posts", (request, response) -> {
            String body = "<table border=\"0\"> \n";
            body += "<tr><td><b>Publicaciones</b></td><td>Autor</td></tr> \n";
            body += "<span style=\"color:blue\"> \n";
            List<Post> posts = Post.all();
            for(Post p : posts) {
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
            System.out.println("Trying to do a post with: ");
            String first_name = request.queryParams("first_name");
            System.out.println("first_name");
            System.out.println(first_name);
            String last_name = request.queryParams("last_name");
            System.out.println("last_name");
            System.out.println(last_name);
            String pass = request.queryParams("pass");
            System.out.println("pass");
            System.out.println(pass);
            String email = request.queryParams("email");
            System.out.println("email");
            System.out.println(email);
            String address = request.queryParams("address");
            System.out.println("address");
            System.out.println(address);
            String body = "<!DOCTYPE html><body><script>";
            Boolean exit = false, error = false;
            if ((first_name == null)
                || (last_name == null)
                || (pass == null)
                || (email == null)
                || (address == null)) error = true;
            if (!error) {
                City c = City.findByPostCode(request.queryParams("postcode"));
                User u = new User();
                u
                    .firstName(first_name)
                    .lastName(last_name)
                    .email(email)
                    .address(address)
                    .pass(pass)
                    .setParent(c);
                try {
                    exit = u.saveIt();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (exit) body += "alert('Usuario correctamente registrado!'); window.history.back(-1);";
                else body += "alert('El registro no pudo completarse!'); window.history.back(-1);";
            } else {
                body += "alert('El registro no pudo completarse porque un campo estaba vacio.'); window.history.back(-1);";
            }
            body += "</script></body>";
            return body;
        });
        
        post("/login", (request, response) -> {
            String body = "<!DOCTYPE html><body><script>";
            String email = request.queryParams("email");
            String pwd = request.queryParams("pwd");
            boolean allOk = false;
            User u = User.findByEmail(email);
            if (u != null ? u.pass().equals(pwd) : false) allOk = true;
            if(allOk){
                Session session = request.session(true);
                session.attribute("email", email);
                session.maxInactiveInterval(30*60);
                response.cookie("email", email, session.maxInactiveInterval());
                body += "alert('Bienvenido " + u.firstName() + "!');  top.location='/';";
                body += "</script></body>";
                return body;
            }else{
                body += "alert('El email indicado no existe o la contraseña no coincide.!'); window.history.back(-1);";
                body += "</script></body>";
                return body;
            }
        });
        
        get("/login", (request, response) -> {
            if(!sesionExists(request)) {
                response.redirect("login.html");
            }
            return "Usted ya ha iniciado sesion!";
        });
        
        get("/logout", (request, response) -> {
            String body = "";
            Cookie[] cookies = request.raw().getCookies();
            for(Cookie co : cookies) {
                if(co.getName().equals("user")){
                    response.removeCookie(co.getName());
                }
            }
            Session session = request.session(false);
            if (session == null) {
                body += "No hay ninguna sesion activa.";
            } else {
                session.invalidate();
                body += "Sesion cerrada correctamente.";
            }
            return body;
        });
        
        get("/sesioninfo", (request, response) -> {
            String body = "<!DOCTYPE html>";
            Session session = request.session(false);
            if (sesionExists(request)) {
                Set<String> attrb = request.session(false).attributes();
                for (String s : attrb) {
                    body += "[" + s + "]:" + request.session().attribute(s) + "<br>";
                }
            }
            else {
                body += "<body><script>";
                body += "alert('No hay ninguna sesion activa!'); top.location='/';";
                body += "</script></body>";
            }
            return body;
        });
        
        get("/cookiesinfo", (request, response) -> {
            String body = "<!DOCTYPE html>";
            Cookie[] cookies = request.raw().getCookies();
            for(Cookie co : cookies) {
                body += "[" + co.getName() + "]: " + co.getValue() + "<br />";
            }
            return body;
        });
        
    
        post("/cities", (request, response) -> {
            String body = "<body><script>";
            Boolean exit = false, error = false;
            String name = request.queryParams("name");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String postcode = request.queryParams("postcode");
            if ((name == null)
                || (state == null)
                || (country == null)
                || (postcode == null)) error = true;
            if (!error) {
                City c = new City();
                c
                    .name(name)
                    .state(state)
                    .country(country)
                    .postcode(postcode);
                try {
                    exit = c.saveIt();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (exit) body += "alert('Ciudad correctamente registrada!'); window.history.back(-1);";
                else body += "alert('El registro no pudo completarse!'); window.history.back(-1);";
            } else {
                body += "alert('El registro no pudo completarse porque algun campo estaba vacio!'); window.history.back(-1);";
            }
            body += "</script></body>";
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
    
    public static boolean sesionExists(Request r) {
        return r.session(false) != null; 
    }
}
