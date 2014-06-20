package com.unrc.app;

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
import java.util.Set;
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
        Session session = request.session(false);
        if (null != session) {
            Set<String> attrb = request.session(false).attributes();
            if (attrb.contains("user_email") && attrb.contains("user_id"))
                return session;
            else return null;
        }
        else return null;
    }
    
    /** Metodo para convertir una cadena a un entero con un radix 10
     * @param s una cadena que debera ser un valor numero en sistema decimal
     * @return una representacion entera de s si es posible, null en caso contrario
     */
    public static Integer strToInt(String s) {
        Integer num = null;
        if (s.isEmpty()) return num;
        else {
            int i;
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
                    String user_email = request.session(false).attribute("user_email");
                    List<Phone> phones = User.findByEmail(user_email).getAll(Phone.class);

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
                    String user_email = request.session(false).attribute("user_email");
                    List<Vehicle> vehicles = User.findByEmail(user_email).getAll(Vehicle.class);

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
                    String user_email = request.session(false).attribute("user_email");
                    List<Post> posts =  User.findByEmail(user_email).getAll(Post.class);

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
                    attributes.put("user_email", request.session(false).attribute("user_email").toString());
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
        
        // <editor-fold desc="Sparks for vehicle deletion">
        get("/vehicles/del", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    
                    String user_email = request.session(false).attribute("user_email");
                    
                    List<Vehicle> vehicles = User.findByEmail(user_email).getAll(Vehicle.class);

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
                if(v.delete()) {
                    body += "El vehiculo fue correctamente eliminado";
                } else {
                    body += "El vehiclo no pudo ser eliminado";
                }
            } else {
                body += "El vehiculo no fue encontrado en la base de datos!";
            }
            return body;
        });
        // </editor-fold>
        
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
                        int passengers = Integer.getInteger(request.queryParams("passengers"), 4);
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
                        int displacement = Integer.getInteger(request.queryParams("displacement"), 0);
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
                        int max_load = Integer.getInteger(request.queryParams("max_load"), 0);
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
        
        // <editor-fold desc="Sparks for post posts">
        get("/posts/new", 
            (request, response) -> {
                if(null != existsSession(request)) {
                    Map<String, Object> attributes = new HashMap<>();
                    
                    String user_email = request.session(false).attribute("user_email");
                    
                    List<Vehicle> vehicles = User.findByEmail(user_email).getAll(Vehicle.class);

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
        
        // <editor-fold desc="Sparks for user session management">
        post("/login", (request, response) -> {
            String body = "";
            String email = request.queryParams("email");
            String pwd = request.queryParams("pwd");
            User u = User.findByEmail(email);
            if (u != null ? u.pass().equals(pwd) : false) {
                Session session = request.session(true);
                session.attribute("user_email", email);
                session.attribute("user_id", u.getId());
                session.maxInactiveInterval(30*60);
                body += "<body><script type='text/javascript'>";
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
            if(null == existsSession(request)) response.redirect("/login.html");
            else return "Usted ya ha iniciado sesion con el email: " + request.session(false).attribute("user_email");
            return null;
        });
        
        get("/logout", (request, response) -> {
            String body = "";
            Session session = existsSession(request);
            if (null == session) {
                body += "No hay ninguna sesion activa.";
            } else {
                session.invalidate();
                body += "Sesion cerrada correctamente.";
            }
            return body;
        });
        
        get("/sesioninfo", (request, response) -> {
            String body = "";
            Session s;
            if (null != (s = existsSession(request))) {
                body += "Usted ya ha iniciado sesion!";
            }
            else {
                body += "No hay ninguna sesion activa!";
            }
            return body;
        });
        //</editor-fold>
        
        get("/abrir", (r, q) -> {
            ElasticSearch.client();
            return "Servidor abierto";
        });
    }
}