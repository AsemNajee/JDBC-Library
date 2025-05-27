package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import jdbclibrary.libs.querybuilder.query.Condition;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class DeleteStatementBuilder {
    
    private ArrayList<Condition> conditions;
    private String table;
    
    public DeleteStatementBuilder(){
        this.conditions = new ArrayList<>();
    }
    
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
    public DeleteStatementBuilder where(String column, String operator, String value){
        conditions.add(new Condition(column, operator, value));
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM \"").append(table).append("\"");
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
