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
            int aine = Integer.parseInt(req.queryParams("raakaAine"));
            int drinkki = Integer.parseInt(req.queryParams("drinkki"));
//            List<RaakaAine> jotain = raakaaineDao.findAll();
//         
//            List<Annos> tama = annosDao.findAll();
//            
//            int aine = -1;
//            
//            int k = 0;
//            while (k < jotain.size()) {
//                if (jotain.get(k).getNimi() == j) {
//                    aine = jotain.get(k).getId();
//                    break;
//                }
//                
//                k ++;
//            }
//            
//              int drinkki = -1;
//            
//            int p = 0;
//            while (k < tama.size()) {
//                if (tama.get(k).getNimi() == i) {
//                    drinkki = tama.get(k).getId();
//                    break;
//                }
//                
//                p ++;
//            }
            String maara = req.queryParams("maara");
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
       
            System.out.println(aine);
            System.out.println(drinkki);
            System.out.println(maara);
            System.out.println(jarjestys);
            
            annosraakaainedao.saveOrUpdate(new AnnosRaakaAine(drinkki, aine, jarjestys, maara));
             res.redirect("/annokset");
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
                
                

       
        
        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());

        }
}
