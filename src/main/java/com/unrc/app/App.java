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
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.Cookie;
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
            if (!Base.hasConnection()) 
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });
        
        Spark.after((request, response) -> {
            if (Base.hasConnection()) 
            Base.close();
        });
        
        get("/users", 
            (request, response) -> {
                String p = request.queryParams("page");
                String upp = request.queryParams("upp");
                
                int page = p != null ? Integer.valueOf(p) : 1;
                int usersPerPage = upp != null? Integer.valueOf(upp) : 10000;
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
        
        get("/admins", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Administrator> admins = Administrator.all();
                
                attributes.put("admins_count", admins.size());
                attributes.put("admins", admins);
                
                return new ModelAndView(attributes, "./moustache/admins.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/cities",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<City> cities = City.all();
                
                attributes.put("cities_count", cities.size());
                attributes.put("cities", cities);
                
                return new ModelAndView(attributes, "./moustache/cities.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/phones",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Phone> phones = Phone.all();
                
                attributes.put("phones_count", phones.size());
                attributes.put("phones", phones);
                
                return new ModelAndView(attributes, "./moustache/phones.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/vehicles",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Vehicle> vehicles = (List<Vehicle>) Vehicle.all();
                
                attributes.put("vehicles_count", vehicles.size());
                attributes.put("vehicles", vehicles);
                
                return new ModelAndView(attributes, "./moustache/vehicles.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/posts",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Post> posts = Post.all();
                
                attributes.put("posts_count", posts.size());
                attributes.put("posts", posts);
                
                return new ModelAndView(attributes, "./moustache/posts.moustache");
            },
            new MustacheTemplateEngine()
        );
          
        
        /**
         * 
         * 
         */
        get("/users/new", 
            (request, response) -> {
                if (!yaInicio(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    List<City> cities = City.all();
                    
                    attributes.put("cities_count", cities.size());
                    attributes.put("cities", cities);
                    
                    return new ModelAndView(attributes, "./moustache/newuser.moustache");
                } else {
                    response.redirect("/sesioninfo");
                    return null;
                }
            },
            new MustacheTemplateEngine()
        ); 
        
        post("/users", (request, response) -> {
            String first_name = request.queryParams("first_name");
            String last_name = request.queryParams("last_name");
            String pass = request.queryParams("pass");
            String email = request.queryParams("email");
            String address = request.queryParams("address");
            String body = "";
            
            if ((first_name.equals(""))
                || (last_name.equals(""))
                || (pass.equals(""))
                || (email.equals(""))
                || (address.equals(""))) {
                City c = City.findByPostCode(request.queryParams("postcode"));
                User u = new User();
                u
                    .firstName(first_name)
                    .lastName(last_name)
                    .email(email)
                    .address(address)
                    .pass(pass)
                    .setParent(c);
                if (u.saveIt()) body += "Usuario correctamente registrado!";
                else body += "El servidor denego el registro con los datos indicados.";
            } else {
                body += "El registro no pudo completarse porque un campo estaba vacio";
            }
            return body;
        });
        
        
        get("/cities/new", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<City> cities = City.all();
                
                attributes.put("cities_count", cities.size());
                attributes.put("cities", cities);
                
                return new ModelAndView(attributes, "./moustache/newcity.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        post("/cities", (request, response) -> {
            String body = "";
            String name = request.queryParams("name");
            String state = request.queryParams("state");
            String country = request.queryParams("country");
            String postcode = request.queryParams("postcode");
            
            if ((name.equals(""))
                || (state.equals(""))
                || (country.equals(""))
                || (postcode.equals(""))) {
                    City c = new City();
                    c
                        .name(name)
                        .state(state)
                        .country(country)
                        .postcode(postcode);
                    if (c.saveIt()) body += "Ciudad correctamente registrada!";
                    else body += "El servidor denego el registro con los datos indicados.";
            } else {
                body += "El registro no pudo completarse porque algun campo estaba vacio!";
            }
            return body;
        });
        
        
        get("/vehicles/new", 
            (request, response) -> {
                if(yaInicio(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    
                    attributes.put("user_id", request.session(false).attribute("user_id").toString());

                    return new ModelAndView(attributes, "./moustache/newvehicle.moustache");
                } else {
                    response.redirect("/notlogged.html");
                    return null;
                }
            },
            new MustacheTemplateEngine()
        );
        
        post("/vehicles", (request, response) -> {
            String name = request.queryParams("name");
            String brand = request.queryParams("brand");
            String year = request.queryParams("year");
            Integer user_id = request.session(false).attribute("user_id");
            String body = "";
            boolean exit;
            
            if ((name.equals("")) || (price.equals(9999))) {
                
                exit = v.saveIt();
                if (!exit) body = "Vehiculo correctamente registrado!";
                else body = "El vehiculo no pudo ser cargado en la base de datos.";
            }
            return body;
        });
        get("/posts/new", 
            (request, response) -> {
                if(yaInicio(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    List<Vehicle> vehicles = (List<Vehicle>) Vehicle.all();

                    attributes.put("vehicles_count", vehicles.size());
                    attributes.put("vehicles", vehicles);
                    attributes.put("user_id", request.session(false).attribute("user_id").toString());

                    return new ModelAndView(attributes, "./moustache/newpost.moustache");
                } else {
                    response.redirect("/notlogged.html");
                    return null;
                }
            },
            new MustacheTemplateEngine()
        );
        
        post("/posts", (request, response) -> {
            Integer price = Integer.getInteger(request.queryParams("price"), 9999);
            String text = request.queryParams("text");
            String vehicle_id = request.queryParams("vehicle_id");
            String user_id = request.session(false).attribute("user_id");
            String body = "";
            boolean exit;
            
            if ((text.equals("")) || (price.equals(9999))) {
                Post p = new Post();
                p
                    .price(price)
                    .text(body)
                    .setParents(User.findById(user_id), Vehicle.findById(vehicle_id));
                exit = p.saveIt();
                if (!exit) body = "Anuncio correctamente publicado!";
                else body = "El anuncio no pudo ser publicado.";
            }
            return body;
        });
        
        
        /**
         * Metodos para manejar
         * la sesion del usuario
         */
        post("/login", (request, response) -> {
            String body = "<body><script>";
            String email = request.queryParams("email");
            String pwd = request.queryParams("pwd");
            User u = User.findByEmail(email);
            if (u != null ? u.pass().equals(pwd) : false) {
                Session session = request.session(true);
                session.attribute("user_email", email);
                session.attribute("user_id", u.getId());
                session.maxInactiveInterval(30*60);
                response.cookie("user_email", email, session.maxInactiveInterval());
                body += "alert('Bienvenido " + u + "!'); document.location = '/';";
                body += "</script></body>";
                return body;
            } else {
                body += "El email indicado no existe o la contraseña no coincide.";
                return body;
            }
        });
        
        get("/login", (request, response) -> {
            response.status(200);
            if(!yaInicio(request)) response.redirect("/login.html");
            else return "Usted ya ha iniciado sesion con el email: " + request.session(false).attribute("user_email");
            return null;
        });
        
        get("/logout", (request, response) -> {
            String body = "";
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
            String body = "";
            if (yaInicio(request)) {
                body += "Usted ya ha iniciado sesion como: " + request.attribute("user_email").toString();
            }
            else {
                body += "No hay ninguna sesion activa!";
            }
            return body;
        });
    }
    
    
    /**
     * Metodo auxiliar para controlar si un 
     * usuario ya inicio sesion en el sitio.
     */
    public static boolean yaInicio(Request request) {
        Session session = request.session(false);
        if (session != null) {
            Set<String> attrb = request.session(false).attributes();
            return attrb.contains("user_email") && attrb.contains("user_id");
        }
        else return false;
    }
}
