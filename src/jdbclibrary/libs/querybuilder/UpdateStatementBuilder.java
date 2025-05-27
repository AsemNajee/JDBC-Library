package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import java.util.HashMap;
import jdbclibrary.libs.querybuilder.query.Condition;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class UpdateStatementBuilder implements StatementFillable{
    
    private int index = 1;
    private ArrayList<Condition> conditions;
    private String table;
    private HashMap<String, ColumnData> params;

    UpdateStatementBuilder(String table) {
        this.params = new HashMap<>();
        this.table = table;
        this.conditions = new ArrayList<>();
    }
    
    public UpdateStatementBuilder set(String column, String value){
        params.put(column, new ColumnData(index++, null));
        params.get(column).setValue(value);
        return this;
    }
    
    public HashMap<String, ColumnData> getParams(){
        return this.params;
    }
    
    /**
     * add condition to the SQL statement
     * @param column is the column in the database to compare
     * @param value value to equal it with the value of the column
     * @return 
     */
    public UpdateStatementBuilder where(String column, String value){
        return where(column, "=", value);
    }
    public UpdateStatementBuilder where(String column, String operator, String value){
        conditions.add(new Condition(column, operator, value));
        return this;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE \"").append(table).append("\" SET ");
        params.keySet().forEach(col -> {
            sb.append("\"").append(col).append("\" = ?,");
        });
        sb.deleteCharAt(sb.length()-1);
        sb.append(" ");
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
