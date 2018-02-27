package tikape.runko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.Database;
import tikape.runko.database.RaakaAineDao;

import tikape.runko.domain.Annos;

import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
        Spark.port(Integer.valueOf(System.getenv("PORT")));
     }
        
        Connection connection = DriverManager.getConnection("jdbc:sqlite:drinkkitietokanta.db");

        PreparedStatement statement = connection.prepareStatement("SELECT 1");

        ResultSet resultSet = statement.executeQuery();
        
        if (resultSet.next()) {
            System.out.println("Hei tietokantamaailma!");
        } else {
            System.out.println("Yhteyden muodostaminen epäonnistui.");
        }
        
        Database database = new Database("jdbc:sqlite:drinkkitietokanta.db");
        database.init();

        
        
        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao raakaaineDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosraakaainedao = new AnnosRaakaAineDao(database);
        
        
        
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("annosraakaaineet", annosraakaainedao.findAll());
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        
//        
        Spark.get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("raakaaineet", raakaaineDao.findAll());

            return new ModelAndView(map, "annokset");
            
        }, new ThymeleafTemplateEngine());
        
        Spark.post("/uusi", (req, res) -> {
             
            String nimi = req.queryParams("nimi");
            String ohje = req.queryParams("ohje");
            
            if (nimi.isEmpty()) {
                res.redirect("/annokset");
                return "";
            }
            
            List<Annos> annokset = annosDao.findAll();
            for (int i = 0; i<annokset.size(); i++) {
                if (nimi.equals(annokset.get(i).getNimi())) {
                    res.redirect("/annokset");
                    return "";
                }
            }
            
            annosDao.saveOrUpdate(new Annos(null, nimi, ohje));
          
           
            
            
            res.redirect("/annokset");
            return "";
        });
        
        
        Spark.post("/poista/:id", (req, res) -> {
        
            int i = Integer.parseInt(req.params(":id"));

            annosDao.delete(i);
            

            res.redirect("/annokset");
            return "";
        });

          
        Spark.post("/lisaaminen", (req, res) -> {
            String j = req.queryParams("raakaAine");
            String i = req.queryParams("drinkki");
            List<RaakaAine> raakaaineet = raakaaineDao.findAll();
         
            List<Annos> drinkit = annosDao.findAll();
            
            int aine = -1;
            
            int k = 0;
            while (k < raakaaineet.size()) {
                if (raakaaineet.get(k).getNimi().equals(j)) {
                    aine = raakaaineet.get(k).getId();
                    break;
                }
                
                k ++;
            }
            
              int drinkki = -1;
            
            int p = 0;
            while (p < drinkit.size()) {
                if (drinkit.get(p).getNimi().equals(i)) {
                    drinkki = drinkit.get(p).getId();
                    break;
                }
                
                p ++;
            }
            String maara = req.queryParams("maara");
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String lisaohje = req.queryParams("lisaohje");
       

            annosraakaainedao.saveOrUpdate(new AnnosRaakaAine(drinkki, aine, jarjestys, maara, lisaohje));
             res.redirect("/annokset");

<<<<<<< HEAD

          
=======
>>>>>>> f4e5be302e7366e7628c1484b64640a599a2dcfa
            return "";
        });
        
      
        
        
        Spark.get("/raakaaineet", (req, res) -> {
            HashMap map = new HashMap();
            map.put("raakaaineet", raakaaineDao.findAll());
            
            return new ModelAndView(map, "raakaaineet");
            
            
        }, new ThymeleafTemplateEngine());


        Spark.post("/raakaaineet", (req, res) -> {
            
           
                
            
            String nimi = req.queryParams("nimi");
            
            if (nimi.isEmpty()) {
                res.redirect("/raakaaineet");
                return "";
            }
            
            List<RaakaAine> raakaaineet = raakaaineDao.findAll();
            for (int i = 0; i< raakaaineet.size(); i++) {
                if (nimi.equals(raakaaineet.get(i).getNimi())) {
                    res.redirect("/raakaaineet");
                    return "";
                }
                    
            }
            
            
            raakaaineDao.saveOrUpdate(new RaakaAine(null, nimi));
          
           
            
            
            res.redirect("/raakaaineet");
            return "";
        });
//        

        Spark.post("/delete/:id", (req, res) -> {
            
            int i = Integer.parseInt(req.params(":id"));

            raakaaineDao.delete(i);
            

            res.redirect("/raakaaineet");
            return "";
        });


        Spark.post("/raakaaineet", (req, res) -> {
            
            String nimi = req.queryParams("nimi");
            List<RaakaAine> raakaaineett = raakaaineDao.findAll();
            int i = 0;
            while(i < raakaaineett.size()) {
                if (raakaaineett.get(i).getNimi().equals(nimi)) {
                    raakaaineDao.delete(raakaaineett.get(i).getId());
                }
                i ++;
            }
            
            res.redirect("/raakaaineet");
            return "";
        });

      
        
     
        get("/:id", (req, res) -> {

            HashMap map = new HashMap<>();
            Integer annosId = Integer.parseInt(req.params(":id"));
            map.put("annos", annosDao.findOne(annosId));
            map.put("raakaaineet", raakaaineDao.raakaineetannokselle(annosId));
            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
                
        
        get("/raakaaine/:id", (req, res) -> {

            HashMap map = new HashMap<>();
            Integer raakaaineId = Integer.parseInt(req.params(":id"));
            map.put("raakaaine", raakaaineDao.findOne(raakaaineId));
            map.put("annokset", annosDao.annoksetRaakaaineelle(raakaaineId));
            return new ModelAndView(map, "raakaaine");
        }, new ThymeleafTemplateEngine());
                

       
        
        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());

        }
}
