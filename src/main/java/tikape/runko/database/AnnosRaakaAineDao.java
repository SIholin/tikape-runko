
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;


public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    
    private Database database;
    
    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        AnnosRaakaAine annosraakaaine = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("lisaohje"));

        stmt.close();
        rs.close();

        conn.close();

        return annosraakaaine;
    }
    
    public List<AnnosRaakaAine> findAllByAnnos(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ?");
        stmt.setInt(1, key);
        List<AnnosRaakaAine> annosraakaaineet = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
        AnnosRaakaAine ra = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("lisaohje"));
        annosraakaaineet.add(ra);
        }
        
        stmt.close();
        rs.close();

        conn.close();

        return annosraakaaineet;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine");
        List<AnnosRaakaAine> annosraakaaineet = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            return null;
        }
        while (rs.next()) {
        AnnosRaakaAine ra = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("lisaohje"));
        annosraakaaineet.add(ra);
        }
        
        stmt.close();
        rs.close();

        conn.close();

        return annosraakaaineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
           Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    
    public void deleteRaakaaine(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE raakaaine_id = ?");
        
        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
       
            return save(object);
      
        
    }

    
        private AnnosRaakaAine save(AnnosRaakaAine object) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine" + "(annos_id, raakaaine_id, jarjestys, maara, lisaohje)" + "VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, object.getAnnosId());
        stmt.setInt(2, object.getRaakaaineId());
        stmt.setInt(3, object.getJarjestys());
        stmt.setString(4, object.getMaara());
        stmt.setString(5, object.getLisaohje());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ? AND raakaaine_id = ? AND jarjestys = ? AND maara = ? AND lisaohje = ?");
        stmt.setInt(1, object.getAnnosId());
        stmt.setInt(2, object.getRaakaaineId());
        stmt.setInt(3, object.getJarjestys());
        stmt.setString(4, object.getMaara());
        stmt.setString(5, object.getLisaohje());
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        AnnosRaakaAine ara = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"), rs.getString("lisaohje"));
        
        stmt.close();
        rs.close();
        conn.close();
        return ara;
        
        
    }
    
    private AnnosRaakaAine update(AnnosRaakaAine object) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE AnnosRaakaAine SET " + "jarjestys = ?, maara = ?, lisaohje = ? WHERE annos_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, object.getJarjestys());
        stmt.setString(2, object.getMaara());
        stmt.setString(3, object.getLisaohje());
        stmt.setInt(4, object.getAnnosId());
        stmt.setInt(5, object.getRaakaaineId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return object;
    }
}
