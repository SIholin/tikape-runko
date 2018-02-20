
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;


public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    
    private Database database;
    
    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
