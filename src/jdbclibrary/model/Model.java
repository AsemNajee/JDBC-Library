 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package jdbclibrary.model;

import jdbclibrary.database.DatabaseInsertable;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.querybuilder.SQLBuilder;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public abstract class Model implements DatabaseInsertable {

    /**
     * table name in the database
     */
    protected String table;
    
    /**
     * connection instance for the database statements
     */
    private Connection conn;
    
    /**
     * store name of columns in database which able to insert
     */
    protected LinkedHashMap<String, String> insertableColumns;
    
    /**
     * store name of columns in database which able to update
     */
    protected LinkedHashMap<String, String> updateableColumns;
    
    /**
     * store name of columns in database which able to update
     */
    protected LinkedHashMap<String, String> selectColumns;
    
    /**
     * initial the model and define the table name
     * @param table is the table name
     * @param insertable
     * @param updateable
     * @param selectCols
     */
    public Model(String table,LinkedHashMap<String, String> insertable, LinkedHashMap<String, String> updateable, LinkedHashMap<String, String> selectCols) throws SQLException {
        this.table = table;
        this.conn = Connector.getInstance().getConnection();
        this.insertableColumns = insertable;
        this.updateableColumns = updateable;
        this.selectColumns = selectCols;
    }
    
    /**
     * insert data to database
     * @param data element which will inserted
     * @return confirmation
     */
    public boolean create(LinkedHashMap<String, String> data) throws SQLException, Exception{
        String sql = SQLBuilder.buildParams(data, this::getInsertableColumns).getInsertSQLStatement(table);
        PreparedStatement stmt = conn.prepareStatement(sql);        
        this.putInsertValuesToParams(stmt, data);
        System.out.println(stmt);
        if(stmt.executeUpdate() > 0){
            return true;
        }
        return false;
    };
    
    /**
     * edit the value of the in the database
     * @param id the identifier of the row in database
     * @param data new data
     * @return confirmation
     */
    public boolean update(int id, LinkedHashMap<String, String> data) throws Exception{
        String sql = SQLBuilder.buildParams(data, this::getUpdateableColumns).getUpdateSQLStatement(table, this::getUpdateableColumns);
        System.out.println(getUpdateableColumns());
        PreparedStatement statement = conn.prepareStatement(sql);
        int lastIndex = this.putUpdateValuesToParams(statement, data);
        statement.setInt(lastIndex, id);
        System.out.println(statement);
        if(statement.executeUpdate() > 0){
            return true;
        }
        return false;
    }
    
    /**
     * delete existing element from database 
     * @param id the identifier of the element to delete
     * @return the element which deleted
     */
    public LinkedHashMap<String, String> delete(int id) throws SQLException{
        LinkedHashMap<String, String> item = get(id);
        String sql = SQLBuilder.getDeleteSQlStatement(table);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        return item;
    }
    
    /**
     * get element by id
     * @param id identifier of the element to get from the database
     * @return object of the model of the data
     */
    public LinkedHashMap<String, String> get(int id) throws SQLException{
        String sql = SQLBuilder.getSelectOneSQLStatement(table, this::getSelectColumns);
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if(rs.next()) return readRow(rs);
        return null;
    }
    
    /**
     * get all data in the database
     * @return a collection of data from database table 
     */
    public ArrayList<LinkedHashMap<String, String>> getAll() throws SQLException{
        String sql = SQLBuilder.getSelectAllSQLStatement(table, this::getSelectColumns);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return readAllQuerey(rs);
    }
    
    private LinkedHashMap<String, String> readRow(ResultSet rs) throws SQLException{
        LinkedHashMap<String, String> res = new LinkedHashMap<>();
        for(String key : this.getSelectColumns()){
            res.put(key, rs.getString(key));
        }
        return res;
    }
    
    private ArrayList<LinkedHashMap<String, String>> readAllQuerey(ResultSet rs) throws SQLException{
        ArrayList<LinkedHashMap<String, String>> finalResult = new ArrayList<>();
        while(rs.next()){
            finalResult.add(readRow(rs));
        }
        return finalResult;
    }
    
    /**
     * execute sql statement to create table in database
     * this will called first time you run your program 
     * @throws SQLException
     */
    public abstract void createTable() throws SQLException;
        
    @Override
    public Set<String> getInsertableColumns() {
        return insertableColumns.keySet();
    }
    
    /**
     * get all column which we can edit in the database .
     * @return Set of columns names
     */
    @Override
    public Set<String> getUpdateableColumns() {
        return updateableColumns.keySet();
    }
    
    /**
     * get all column which we can select in the database .
     * @return Set of columns names
     */
    @Override
    public Set<String> getSelectColumns() {
        return selectColumns.keySet();
    }
    
    @Override
    public PreparedStatement putInsertValuesToParams(PreparedStatement p, LinkedHashMap<String, String> data) throws SQLException {
        int i = 1;
        for(String key : insertableColumns.keySet()){
            if(insertableColumns.get(key).equals("string")){
                p.setString(i, data.get(key));
            }else if(insertableColumns.get(key).equals("int")){
                p.setInt(i, Integer.parseInt(data.get(key)));
            }
            i++;
        }
        return p;
    }
    
      
    @Override
    public int putUpdateValuesToParams(PreparedStatement p, LinkedHashMap<String, String> data) throws SQLException {
        int i = 1;
        System.out.println(updateableColumns.keySet());
        for(String key : updateableColumns.keySet()){
            if(updateableColumns.get(key).equals("string")){
                p.setString(i, data.get(key));
            }else if(updateableColumns.get(key).equals("int")){
                p.setInt(i, Integer.parseInt(data.get(key)));
            }
            i++;
        }
        return i;
    }
}


/**
 * create(HashMap<String, String> data)
 * check all keys availability
 * 
 * 
 * 
 * 
 * 
 * update(int id, HashMap<String, String> data)
 * delete(int id)
 * find(int id)
 * where(String column, String value)
 * where(String column, String operator, String value)
 * first()
 */