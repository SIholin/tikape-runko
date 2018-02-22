
package tikape.runko.database;

import java.util.List;
import tikape.runko.domain.RaakaAine;
import java.util.*;
import java.sql.*;


public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;
    
    public RaakaAineDao(Database database) {
        this.database = database;
        
    }
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        RaakaAine raakaaine = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));

        stmt.close();
        rs.close();

        conn.close();

        return raakaaine;
    }
    
    public List<String> raakaineetannokselle(Integer annosId) throws SQLException {
        String kysely = "SELECT RaakaAine.id, RaakaAine.nimi, AnnosRaakaAine.maara FROM RaakaAine, AnnosRaakaAine\n"
        + "              WHERE raakaaine.id = AnnosRaakaAine.raakaaine_id "
        + "                  AND AnnosRaakaAine.annos_id = ?\n";
        
        List<RaakaAine> raakaaineet = new ArrayList<>();
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setInt(1, annosId);
            ResultSet result = stmt.executeQuery();
            
            while (result.next()) {
                raakaaineet.add(new RaakaAine(result.getInt("id"), result.getString("nimi")));
            }
        }
        


        List<String> raakaineetjamaara = new ArrayList<>();
        for (int i = 0; i< raakaaineet.size(); i++) {
            String maara = raakaaineenmaara(raakaaineet.get(i));
            raakaineetjamaara.add(raakaaineet.get(i).getNimi()+ ", " + maara);
            
        }
        
        return raakaineetjamaara;
    }
    
    public String raakaaineenmaara(RaakaAine raakaaine) throws SQLException {
        String kysely = "SELECT AnnosRaakaAine.maara FROM AnnosRaakaAine, RaakaAine WHERE RaakaAine.id = ? AND AnnosRaakaAine.raakaaine_id = RaakaAine.id";
        
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(kysely);
            stmt.setInt(1, raakaaine.getId());
            ResultSet result = stmt.executeQuery();
            return result.getString("maara");
        }

    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM RaakaAine");
        List<RaakaAine> raakaaineet = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        raakaaineet.add(ra);
        }
        
        stmt.close();
        rs.close();

        conn.close();

        return raakaaineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    @Override
    public RaakaAine saveOrUpdate(RaakaAine object) throws SQLException{
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }
    
    private RaakaAine save(RaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO RaakaAine" + "(nimi)" + "VALUES (?)");
        
        stmt.setString(1, object.getNimi());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
        stmt.setString(1, object.getNimi());
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        RaakaAine ra = new RaakaAine(rs.getInt("id"), rs.getString("nimi"));
        
        stmt.close();
        rs.close();
        conn.close();
        return ra;
        
        
    }
    
    private RaakaAine update(RaakaAine object) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE RaakaAine SET " + "nimi = ? WHERE id = ?");
        stmt.setString(1, object.getNimi());
        stmt.setInt(2, object.getId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return object;
    }
    
}
