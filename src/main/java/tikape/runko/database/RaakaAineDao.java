
package tikape.runko.database;

import java.sql.SQLException;
import java.util.List;
import tikape.runko.domain.RaakaAine;


public class RaakaAineDao implements Dao<RaakaAine, Integer> {

    private Database database;
    
    public RaakaAineDao(Database database) {
        
        
    }
    @Override
    public RaakaAine findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RaakaAine> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
