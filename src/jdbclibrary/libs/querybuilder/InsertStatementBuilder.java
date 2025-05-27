package jdbclibrary.libs.querybuilder;

import java.util.HashMap;

public class InsertStatementBuilder implements StatementFillable{
    
    String table;
    HashMap<String, ColumnData> params;

    /**
     * INSERT INTO "student" ("name", "age") VALUES (?, ?);
     */
    InsertStatementBuilder() {
        this.params = new HashMap<>();
    }
    
    private InsertStatementBuilder columns(String ...cols){
        params = new HashMap<>();
        for(int i = 0; i < cols.length; i++){
            params.put(cols[i], new ColumnData(i+1, null));
        }
        return this;
    }
    
    public InsertStatementBuilder into(String table){
        this.table = table;
        return this;
    }
    
    public InsertStatementBuilder values(HashMap<String, String> values){
        columns(values.keySet().toArray(new String[0]));
        values.forEach((key, value) -> {
            params.get(key).setValue(value);
        });
        return this;
    }
    
    public HashMap<String, ColumnData> getParams(){
        return this.params;
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO \"").append(table).append("\" (\"");
        params.keySet().forEach(col -> {
            sb.append(col).append("\",\"");
        });
        sb.delete(sb.length()-2, sb.length());
        sb.append(")");
        sb.append(" VALUES (").append("?,".repeat(params.size()));
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }    
    
    
}
