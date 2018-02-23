
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
        if (!rs.next()) {
            return null;
        }
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
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Annos WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

    @Override
    public Annos saveOrUpdate(Annos object) throws SQLException {
        if (object.getId() == null) {
            return save(object);
        } else {
            return update(object);
        }
    }
    
    private Annos save(Annos object) throws SQLException {
        
         Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Annos" + "(nimi, ohje)" + "VALUES (?, ?)");
        
        stmt.setString(1, object.getNimi());
        stmt.setString(2, object.getOhje());
        stmt.executeUpdate();
        stmt.close();
        
        stmt = conn.prepareStatement("SELECT * FROM Annos WHERE nimi = ? AND ohje = ?");
        stmt.setString(1, object.getNimi());
        stmt.setString(2, object.getOhje());
        
        ResultSet rs = stmt.executeQuery();
        rs.next();
        
        Annos annos = new Annos(rs.getInt("id"), rs.getString("nimi"), rs.getString("ohje"));
        
        stmt.close();
        rs.close();
        conn.close();
        return annos;
        
    }
    
    private Annos update(Annos object) throws SQLException {
                Connection conn = database.getConnection();

        PreparedStatement stmt = conn.prepareStatement("UPDATE Annos SET"

                + " nimi = ?, ohje = ? WHERE id = ?");

        stmt.setString(1, object.getNimi());

        stmt.setString(2, object.getOhje());

        stmt.setInt(3, object.getId());

 

        stmt.executeUpdate();

 

        stmt.close();

        conn.close();

 

        return object;
    }
}
