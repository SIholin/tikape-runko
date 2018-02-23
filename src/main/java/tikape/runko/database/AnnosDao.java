
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;

public class AnnosDao implements Dao<Annos, Integer> {
    
    private Database database;
    
    public AnnosDao(Database database) {
        this.database = database;
        
    }

    @Override
    public Annos findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Annos annos = new Annos(rs.getInt("id"), rs.getString("nimi"), rs.getString("ohje"));

        stmt.close();
        rs.close();

        conn.close();

        return annos;
    }
    

    @Override
    public List<Annos> findAll() throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Annos");
        List<Annos> annokset = new ArrayList();
        
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
        Annos ra = new Annos(rs.getInt("id"), rs.getString("nimi"), rs.getString("ohje"));
        annokset.add(ra);
        }
        
        stmt.close();
        rs.close();

        conn.close();
        

        return annokset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
