package jdbclibrary.database;
import java.sql.*;

/**
 * @author Asem Najee
 * connect with database 
 * singleton pattern
 */
public class Connector{
    
    protected long serialVersionUID = 1L;
    private String url = "jdbc:mysql://localhost:3306/";
    private String user = "root";
    private String password = "";
    private String database = "jdbclibrary";
    private static Connector instance;
    private Connection con;
    
    
    private Connector() throws SQLException {
        url += database;
        con = DriverManager.getConnection(url, user, password);
    }
    
    public static Connector getInstance() throws SQLException {
        return instance == null ? (instance = new Connector()) : instance;
    }
    
    public Connection getConnection(){
        return this.con;
    }
    
}
