package jdbclibrary.libs.querybuilder;

import java.util.HashMap;

/**
 * build prepared statement to insert data to the database .
 * @author PC
 */
public class InsertStatementBuilder implements StatementFillable{
    
    String table;
    HashMap<String, ColumnData> params;

    InsertStatementBuilder() {
        this.params = new HashMap<>();
    }
    
    /**
     * select the table to insert data into it
     * @param table name of the table
     * @return 
     */
    public InsertStatementBuilder into(String table){
        this.table = table;
        return this;
    }
    
    /**
     * select which columns you want to insert 
     * and set them values to fill later in the Filler class
     * @param values columns the are required and them values
     * @return 
     */
    public InsertStatementBuilder values(HashMap<String, String> values){
        params = new HashMap<>();
        var array = values.keySet().toArray(new String[0]);
        for(int i = 0; i < array.length; i++){
            params.put(array[i], new ColumnData(i+1, values.get(array[i])));
        }
        return this;
    }
    
    /**
     * getter for params member
     * @return 
     */
    public HashMap<String, ColumnData> getParams(){
        return this.params;
    }
    
    /**
     * get final sql statement 
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `").append(table).append("` (`");
        params.keySet().forEach(col -> {
            sb.append(col).append("`,`");
        });
        sb.delete(sb.length()-2, sb.length());
        sb.append(")");
        sb.append(" VALUES (").append("?,".repeat(params.size()));
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }    
    
    
}
