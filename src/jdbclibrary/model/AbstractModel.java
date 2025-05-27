package jdbclibrary.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.querybuilder.Builder;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class AbstractModel {
    /**
     * table name in the database
     */
    protected String table;
    
    /**
     * connection instance for the database statements
     */
    private Connection conn;

    public AbstractModel(String table) throws SQLException {
        this.table = table;
        this.conn = Connector.getInstance().getConnection();
    }
    
    public boolean create(HashMap<String, String> data){
        String sql = Builder.insert().into(table).values(data).toString();
        
        return false;
    }
    
    
}
