package jdbclibrary.libs.querybuilder.createschema;

import java.sql.SQLException;


public class Column {
    private String columnName;
    private String sql;
    public static final int DEFAULT_STRING_LENGTH = 255;
    public static final int DEFAULT_INT_LENGTH = 8;
    
    /**
     * add new column with type string (varchar) in the database
     * @param columnName is the column name 
     * @param length the size of the column
     * @return
     */
    public static Column string(String columnName, int length){
        return create(columnName, "VARCHAR", length);
    }
    public static Column string(String columnName){
        return string(columnName, DEFAULT_STRING_LENGTH);
    }
    
    /**
     * add new column with type string (varchar) in the database
     * @param columnName is the column name 
     * @param length the size of the column
     * @return
     */
    public static Column integer(String columnName, int length){
        return create(columnName, "INT", length);
    }
    public static Column integer(String columnName){
        return string(columnName, DEFAULT_INT_LENGTH);
    }
    
    public static String enumeration(String tableName, String ...params) throws SQLException{
        String paramsImploded = "";
        for(String p : params){
            paramsImploded += p + ",";
        }
        if(!paramsImploded.equals("")){
            paramsImploded.substring(0, paramsImploded.length() - 1);
        }else{
            throw new SQLException();
        }
        return tableName + " enum(" + paramsImploded + ")";
    }
    
    private static Column create(String columnName, String type, int length){
        Column sb = new Column();
        sb.columnName = columnName;
        sb.sql = columnName + " " + type + "(" + length + ")";
        return sb;
    }
    
    /**
     * set a column as primary key in the table
     * @return 
     */
    public Column primary(){
        primary(false);
        return this;
    }
    
    public Column primary(boolean autoIncrement){
        this.sql += " PRIMARY KEY" + (autoIncrement ? " AUTO_INCREMENT " : "");
        return this;
    }
    
    /**
     * add not null constraint to the column
     * @return 
     */
    public Column notNull(){
        this.sql += " NOT NULL";
        return this;
    }
    
    /**
     * add unique constraint to the column
     * @return 
     */
    public Column unique(){
        this.sql += " UNIQUE";
        return this;
    }
    
    /**
     * can be used once for each column, add check constraint
     * @param condition is the condition of the check constraint
     * @return 
     */
    public Column check(String condition){
        this.sql += " CHECK(" + condition + ")";
        return this;
    }
    
    /**
     * set a column (must be number) unsigned
     * @param unsigned state of unsigned, set true to set the column as unsigned
     * @return 
     */
    public Column unsigned(boolean unsigned){
        this.sql += unsigned ? " UNSIGNED" : " SIGNED";
        return this;
    }
    
    /**
     * add foreign key constraint
     * @param tableName referenced table
     * @param columnName specify the column in the referenced table
     * @return 
     */
    public Column foreignKey(String tableName, String columnName){
        this.sql += " REFERENCES " + tableName + "(" + columnName + ")";
        return this;
    }
    
    /**
     * get final statement of the column properties
     * @return 
     */
    public String getSql(){
        return this.sql;
    }
}
