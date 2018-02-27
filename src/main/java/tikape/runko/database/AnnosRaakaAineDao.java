
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
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        AnnosRaakaAine annosraakaaine = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"));

        stmt.close();
        rs.close();

        conn.close();

        return annosraakaaine;
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
        AnnosRaakaAine ra = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"));
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
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE annos_id = ? AND raakaaine_id = ?");

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
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine" + "(annos_id, raakaaine_id, jarjestys, maara)" + "VALUES (?, ?, ?, ?)");
        stmt.setInt(1, object.getAnnosId());
        stmt.setInt(2, object.getRaakaaineId());
        stmt.setInt(3, object.getJarjestys());
        stmt.setString(4, object.getMaara());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ? AND raakaaine_id = ? AND jarjestys = ? AND maara = ?");
        stmt.setInt(1, object.getAnnosId());
        stmt.setInt(2, object.getRaakaaineId());
        stmt.setInt(3, object.getJarjestys());
        stmt.setString(4, object.getMaara());
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        AnnosRaakaAine ara = new AnnosRaakaAine(rs.getInt("annos_id"), rs.getInt("raakaaine_id"), rs.getInt("jarjestys"), rs.getString("maara"));
        
        stmt.close();
        rs.close();
        conn.close();
        return ara;
        
        
    }
    
    private AnnosRaakaAine update(AnnosRaakaAine object) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE AnnosRaakaAine SET " + "jarjestys = ?, maara = ? WHERE annos_id = ? AND raakaaine_id = ?");
        stmt.setInt(1, object.getJarjestys());
        stmt.setString(2, object.getMaara());
        stmt.setInt(3, object.getAnnosId());
        stmt.setInt(4, object.getRaakaaineId());
        
        stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        
        return object;
    }
}
