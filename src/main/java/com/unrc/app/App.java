package com.unrc.app;

import com.unrc.app.models.Administrator;
import com.unrc.app.models.Bike;
import com.unrc.app.models.Car;
import com.unrc.app.models.City;
import com.unrc.app.models.Other;
import com.unrc.app.models.Phone;
import com.unrc.app.models.Phone.PhoneType;
import com.unrc.app.models.Post;
import com.unrc.app.models.Truck;
import com.unrc.app.models.User;
import com.unrc.app.models.Vehicle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.Spark;
import static spark.Spark.*;

public class App {
    /**
     * Metodo auxiliar para controlar si un 
     * usuario ya inicio sesion en el sitio.
     * @param request the user request
     * @return iff session exists, returns the session
     * else, returns null.
     */
    public static Session existsSession(Request request) {
        return request.session(false);
    }
    
    /**
     * Metodo auxiliar utilizado para comprobar
     * si una sesion es de un usuario administrator
     * Es necesitada para poder ocultar y mostrar ciertas
     * opciones en las templates moustache
     * @param session the possible administrator session
     * @return true if exists an admin with the email of 
     * the current user, else return false.
     */
    public static int sessionLevel(Session session){
        if (null!=session) {
            Boolean isWebmaster = session.attribute("email").toString().equals("admin");
            Boolean isAdmin = Administrator.findByEmail(session.attribute("email")) != null;
            Boolean isUser = User.findByEmail(session.attribute("email")) != null;
            if (isWebmaster) return 3;
            if (isAdmin) return 2;
            if (isUser) return 1;
            return 0;
        } else {
            return 0;
        }
    }
    
    /** Metodo para convertir una cadena a un entero con un radix 10
     * @param s una cadena que debera ser un valor numero en sistema decimal
     * @return una representacion entera de s si es posible, null en caso contrario
     */
    public static Integer strToInt(String s) {
        Integer num = null;
        if (s.isEmpty()) return num;
        else {
            Integer i;
            num = 0;
            for(i = 0; i < s.length(); i++) {
                if (((int)s.charAt(i) >= 48) && ((int)s.charAt(i) <= 57)) {
                    num = (num*10) + ((int)s.charAt(i) - 48);
                } else {
                    num = null;
                    break;
                }
            }
            return num; 
        }
    }
    
    public static void main(String[] args) { 
        externalStaticFileLocation("./public"); // Static files 
        
        // <editor-fold desc="Spark filters (for db connection)">
        Spark.before((request, response) -> {
            if (!Base.hasConnection()) 
                Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/carsapp_development", "root", "");
        });
        
        Spark.after((request, response) -> {
            if (Base.hasConnection()) 
            Base.close();
        });
        //</editor-fold>
        
        // <editor-fold desc="Sparks GET for all models">
        get("/index", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                Session session = existsSession(request);
                int sessionLevel = sessionLevel(session);
                attributes.put("perms", sessionLevel);
                if (sessionLevel > 0) {
                    attributes.put("email", session.attribute("email"));
                }
                return new ModelAndView(attributes, "./moustache/index.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/users", 
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<User> users = User.all();
                
                attributes.put("users", users);
                
                return new ModelAndView(attributes, "./moustache/users.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/cities",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<City> cities = City.all();
                
                attributes.put("cities", cities);
                
                return new ModelAndView(attributes, "./moustache/cities.moustache");
            },
            new MustacheTemplateEngine()
        );
         
        get("/phones",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Phone> phones = Phone.all();
                
                attributes.put("phones", phones);
                
                return new ModelAndView(attributes, "./moustache/phones.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/myphones",
            (request, response) -> {
                if (null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    String email = request.session(false).attribute("email");
                    List<Phone> phones = User.findByEmail(email).getAll(Phone.class);

                    attributes.put("phones", phones);

                    return new ModelAndView(attributes, "./moustache/myphones.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        get("/vehicles",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Vehicle> vehicles = (List<Vehicle>) Vehicle.all();
                
                attributes.put("vehicles", vehicles);
                
                return new ModelAndView(attributes, "./moustache/vehicles.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/myvehicles",
            (request, response) -> {
                if (null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    String email = request.session(false).attribute("email");
                    List<Vehicle> vehicles = User.findByEmail(email).getAll(Vehicle.class);

                    attributes.put("vehicles", vehicles);

                    return new ModelAndView(attributes, "./moustache/myvehicles.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        
        get("/posts",
            (request, response) -> {
                Map<String, Object> attributes = new HashMap<>();
                List<Post> posts = Post.all();
                
                attributes.put("posts", posts);
                
                return new ModelAndView(attributes, "./moustache/posts.moustache");
            },
            new MustacheTemplateEngine()
        );
        
        get("/myposts",
            (request, response) -> {
                if (null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    String email = request.session(false).attribute("email");
                    List<Post> posts =  User.findByEmail(email).getAll(Post.class);

                    attributes.put("posts", posts);

                    return new ModelAndView(attributes, "./moustache/myposts.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        get("/users/new", 
            (request, response) -> {
                if (null == existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    List<City> cities = City.all();
                    
                    attributes.put("cities_count", cities.size());
                    attributes.put("cities", cities);
                    
                    return new ModelAndView(attributes, "./moustache/newuser.moustache");
                } else {
                    Map<String, Object> attributes = new HashMap<>();
                    attributes.put("email", request.session(false).attribute("email").toString());
                    return new ModelAndView(attributes, "./moustache/alreadylogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        ); 
        //</editor-fold>
        
        // <editor-fold desc="Sparks for user register">
        post("/users", (request, response) -> {
            String first_name = request.queryParams("first_name");
            String last_name = request.queryParams("last_name");
            String pass = request.queryParams("pass");
            String email = request.queryParams("email");
            String address = request.queryParams("address");
            String postcode = request.queryParams("postcode");
            
            String body = "";
            
            if (!((first_name.equals(""))
                || (last_name.equals(""))
                || (pass.equals(""))
                || (email.equals(""))
                || (address.equals("")))) {
                City c = City.findByPostCode(postcode);
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
        //</editor-fold>
        
        // <editor-fold desc="Sparks for city register">
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
            
            if (!((name.equals(""))
                || (state.equals(""))
                || (country.equals(""))
                || (null == strToInt(postcode)))) {
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
        // </editor-fold>
        
        // <editor-fold desc="Sparks for vehicle register">
        get("/vehicles/new", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    return new ModelAndView(null, "./moustache/newvehicle.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        post("/vehicles", (request, response) -> {
            String name = request.queryParams("name");
            String brand = request.queryParams("brand");
            String year = request.queryParams("year");
            String plate = request.queryParams("plate");
            String type = request.queryParams("type");
            Integer user_id = request.session(false).attribute("user_id");
            
            String body = "";
            boolean exit = (name.equals("") || brand.equals("") || (null == strToInt(year)) || plate.equals(""));
            
            if (!exit){
                switch(type) {
                    case "car":
                        Integer passengers = strToInt(request.queryParams("passengers"));
                        Car c = new Car();
                        c
                            .passengers(passengers)
                            .name(name)
                            .year(year)
                            .brand(brand)
                            .plate(plate)
                            .setParent(User.findById(user_id));
                        exit = c.saveIt();
                        break;
                    case "bike":
                        Integer displacement = strToInt(request.queryParams("displacement"));
                        Bike b = new Bike();
                        b
                            .displacement(displacement)
                            .name(name)
                            .year(year)
                            .brand(brand)
                            .plate(plate)
                            .setParent(User.findById(user_id));
                        exit = b.saveIt();
                        break;
                    case "truck":
                        Integer max_load = strToInt(request.queryParams("max_load"));
                        Truck t = new Truck();
                        t
                            .maxLoad(max_load)
                            .name(name)
                            .year(year)
                            .brand(brand)
                            .plate(plate)
                            .setParent(User.findById(user_id));
                        exit = t.saveIt();
                        break;
                    case "other":
                        Other o = new Other();
                        o
                            .name(name)
                            .year(year)
                            .brand(brand)
                            .plate(plate)
                            .setParent(User.findById(user_id));
                        exit = o.saveIt();
                        break;
                }
                if (exit == true) body = "Vehiculo correctamente registrado!";
                else body = "El vehiculo no pudo ser cargado en la base de datos.";
            } else {
                body += "El registro no pudo completarse porque algun campo estaba vacio!";
            }
            return body;
        });
        // </editor-fold>
        
        // <editor-fold desc="Sparks for phones register">
        get("/phones/new", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    return new ModelAndView(null, "./moustache/newphone.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
                
        post("/phones", (request, response) -> {
            String type = request.queryParams("type");
            PhoneType phonetype = PhoneType.personal;
            String num = request.queryParams("number");
            Integer user_id = request.session(false).attribute("user_id");
            
            String body = "";
            
            switch(type) {
                case "home":
                    phonetype = PhoneType.home;
                    break;
                case "personal":
                    phonetype = PhoneType.personal;
                    break;
                case "work":
                    phonetype = PhoneType.work;
                    break;
            }
            
            if (null != (strToInt(num))) {
                User u = User.findById(user_id);
                Phone p = new Phone();
                p
                    .num(num)
                    .type(phonetype)
                    .setParent(u);
                
                if (p.saveIt()) body += "Telefono correctamente registrado!";
                else body += "El servidor denego el registro con los datos indicados.";
            } else {
                body += "El registro no pudo completarse porque un campo estaba vacio";
            }
            return body;
        });
        //</editor-fold>
        
        // <editor-fold desc="Sparks for administrators register">
        get("/administrators/new", 
            (request, response) -> {
                Session s = existsSession(request);
                if(null != s ? s.attribute("email").equals("admin") : false) {
                    return new ModelAndView(null, "./moustache/newadmin.moustache");
                } else {
                    if (null == s) {
                        return new ModelAndView(null, "./moustache/notlogged.moustache");
                    } else {
                        return new ModelAndView(null, "./moustache/notadmin.moustache");
                    }
                }
            },
            new MustacheTemplateEngine()
        );
        
        post("/administrators",
                (request, response) -> {
                    String body = "";
                    String email = request.queryParams("email");
                    String pass = request.queryParams("pass");
                    if (null == email || null == pass) {
                        body += "<body><script type='text/javascript'>";
                        body += "alert('No puede haber ningun campo vacio.'); document.location = '/';";
                        body += "</script></body>";
                    }else{
                        Administrator admin = new Administrator();
                        admin
                                .email(email)
                                .pass(pass)
                                .saveIt();
                            body += "<body><script type='text/javascript'>";
                            body += "alert('Administrador correctamente registrado!.'); document.location = '/';";
                            body += "</script></body>";
                    }
                    return body;
        });
        //</editor-fold>
        
        // <editor-fold desc="Sparks for post posts">
        get("/posts/new", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    
                    String email = request.session(false).attribute("email");
                    
                    List<Vehicle> vehicles = User.findByEmail(email).getAll(Vehicle.class);

                    attributes.put("vehicles", vehicles);

                    return new ModelAndView(attributes, "./moustache/newpost.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        post("/posts", (request, response) -> {
            String price = request.queryParams("price");
            String text = request.queryParams("text");
            String vehicle_id = request.queryParams("vehicle_id");
            Integer user_id = request.session(false).attribute("user_id");
            
            User u = User.findById(user_id);
            Vehicle v = Vehicle.findById(vehicle_id);
            
            String body = "";
            boolean exit = text.equals("") || null == strToInt(price) || null == u || null == v;
            
            if (!exit) {
                Post p = new Post();
                p
                    .price(price)
                    .text(text)
                    .setParents(u, v);
                if (p.saveIt()) body += "Anuncio correctamente publicado!";
                else body += "El anuncio no pudo ser publicado.";
            } else {
                body += "Algunos de los campos esta vacio...";
            }
            return body;
        });
        //</editor-fold>
        
        // <editor-fold desc="Sparks for vehicle deletion">
        get("/vehicles/del", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    
                    String email = request.session(false).attribute("email");
                    
                    List<Vehicle> vehicles = User.findByEmail(email).getAll(Vehicle.class);

                    attributes.put("vehicles", vehicles);

                    return new ModelAndView(attributes, "./moustache/vehicledel.moustache");
                } else {
                    return new ModelAndView(null, "./moustache/notlogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        delete("/vehicles/:id", (request, response) -> {
            String body = "";
            
            Vehicle v = Vehicle.findById(request.params(":id"));
            
            if (null != v) {
                List<Post> posts = v.getAll(Post.class);
                posts.stream().forEach((p) -> {
                    p.deleteCascade();
                });
                v.deleteCascade();
                body += "El vehiculo fue correctamente eliminado";
            } else {
                body += "El vehiculo no fue encontrado en la base de datos!";
            }
            return body;
        });
        // </editor-fold>
        
        // <editor-fold desc="Sparks for user session management">
        post("/login", (request, response) -> {
            String body = "";
            String email = request.queryParams("email");
            String pwd = request.queryParams("pwd");
            User u = User.findByEmail(email);
            if (u != null ? u.pass().equals(pwd) : false) {
                Session session = request.session(true);
                session.attribute("email", email);
                session.attribute("user_id", u.getId());
                session.maxInactiveInterval(60);
                body += "<body><script type='text/javascript'>";
                body += "alert('Bienvenido " + u + "!'); document.location = '/index';";
                body += "</script></body>";
                return body;
            } else {
                Administrator a = Administrator.findByEmail(email);
                if (a != null ? a.pass().equals(pwd) : false) {
                    Session session = request.session(true);
                    session.attribute("email", email);
                    session.maxInactiveInterval(60);
                    body += "<body><script type='text/javascript'>";
                    body += "alert('Bienvenido administrador'); document.location = '/index';";
                    body += "</script></body>";
                    return body;
                }else{
                    body += "<body><script type='text/javascript'>";
                    body += "alert('El email indicado no existe o la contraseña no coincide.'); document.location = '/index';";
                    body += "</script></body>";
                    return body;
                }
            }
        });
         
        get("/login",
            (request, response) -> {
                if(null == existsSession(request)) { 
                    return new ModelAndView(null, "./moustache/login.moustache");
                } else {
                    Map<String, Object> attributes = new HashMap<>();
                    String email = existsSession(request).attribute("email");
                    attributes.put("email", email);
                    return new ModelAndView(null, "./moustache/alreadylogged.moustache");
                }
            },
            new MustacheTemplateEngine()
        );
        
        get("/logout", (request, response) -> {
            String body = "";
            Session session = existsSession(request);
            if (null == session) {
                    body += "<body><script type='text/javascript'>";
                    body += "alert('No hay ninguna sesion abierta.'); document.location = '/index';";
                    body += "</script></body>";
            } else {
                session.invalidate();
                    body += "<body><script type='text/javascript'>";
                    body += "alert('Adios! Regresa pronto.'); document.location = '/index';";
                    body += "</script></body>";
            }
            return body;
        });
        
        get("/sesioninfo", (request, response) -> {
            String body = "";
            Session s;
            if (null != (s = existsSession(request))) {
                body += "Usted ya ha iniciado sesion!";
                body += " -- " + s.attribute("email");
            }
            else {
                body += "No hay ninguna sesion activa!";
            }
            return body;
        });
        //</editor-fold>
        
        //<editor-fold desc="Spark for manual open Elastic Search">
        get("/abrir", (r, q) -> {
            ElasticSearch.client();
            return "Servidor abierto";
        });
        //</editor-fold>
        
        // <editor-fold desc="Sparks for administrator deletion">
        delete("/administrator/:email", (request, response) -> {
            String body = "";
            Administrator admin = Administrator.findByEmail(request.params(":email"));
            if(null != admin){
                admin.delete();                
                body += "El administrador fue correctamente eliminado";
            } else {
                body += "El administrador no fue encontrado en la base de datos!";
            }
            return body;
        });
        //</editor-fold>
        
    }
}
