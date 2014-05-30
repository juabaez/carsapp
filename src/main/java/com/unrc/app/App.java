package com.unrc.app;

import com.unrc.app.models.Administrator;
import com.unrc.app.models.City;
import com.unrc.app.models.Phone;
import com.unrc.app.models.Post;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import java.util.List;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import static org.javalite.test.jspec.JSpec.the;
import spark.Spark;
import static spark.Spark.*;

/**
 * Hello world!
 *
 */
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
    }
}
