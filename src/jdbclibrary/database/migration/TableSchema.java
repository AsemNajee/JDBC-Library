package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.querybuilder.createschema.Column;

public final class TableSchema {
    
    private TableSchema(){}
    
    /**
     * create new table in database
     * @param tableName 
     * @param colsData columns as Column
     * @throws SQLException 
     */
    public final static void create(String tableName, Column ...colsData) throws SQLException{
        String sql = "CREATE TABLE " + tableName + "(";
        for(Column s : colsData){
            sql += s.getSql() + " ,\n";
        }
        sql.substring(0, sql.length() -3);
        sql += ")";
        
        var c = Connector.getInstance().getConnection();
        var stat = c.prepareStatement(sql);
        stat.execute();
    }
}
