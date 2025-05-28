package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import jdbclibrary.libs.querybuilder.query.Condition;

/**
 * build Delete from table prepared statement 
 * @author Asem
 */
public class DeleteStatementBuilder {
    
    /**
     * list of conditions 
     */
    private ArrayList<Condition> conditions;
    
    /**
     * table name which we will delete from 
     */
    private String table;
    
    public DeleteStatementBuilder(){
        this.conditions = new ArrayList<>();
    }
    
    /**
     * select table for delete from 
     * @param table is table name
     * @return 
     */
    public DeleteStatementBuilder from(String table){
        this.table = table;
        return this;
    }
    
    /**
     * add condition to the SQL statement
     * @param column is the column in the database to compare
     * @param value value to equal it with the value of the column
     * @return 
     */
    public DeleteStatementBuilder where(String column, String value){
        return where(column, "=", value);
    }
    
    /**
     * add condition to the SQL statement
     * @param column is the column in the database to compare
     * @param operator is the operator to compare column and value
     * @param value value to equal it with the value of the column
     * @return 
     */
    public DeleteStatementBuilder where(String column, String operator, String value){
        conditions.add(new Condition(column, operator, value));
        return this;
    }
    
    /**
     * get final sql statement 
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM `").append(table).append("`");
        if(!(conditions == null || conditions.isEmpty())){
            sb.append(" WHERE ");
            conditions.forEach(condition -> {
                sb.append(condition.toString());
                sb.append(" AND ");
            });
            sb.delete(sb.lastIndexOf(" AND "), sb.length());
        }
        return sb.toString();
    }  
}
