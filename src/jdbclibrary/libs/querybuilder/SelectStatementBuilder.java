package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import java.util.List;
import jdbclibrary.libs.querybuilder.query.Condition;
import jdbclibrary.libs.querybuilder.query.Join;


/**
 * Build sql statements for select statements
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class SelectStatementBuilder {
    private ArrayList<String> selectColumns;
    private String table;
    private String alias;
    private ArrayList<Condition> conditions;
    private ArrayList<Join> joins;
    private ArrayList<String> groupBy;
    private ArrayList<String> orderBy;
    private int limit = -1;
    
    SelectStatementBuilder(){
        selectColumns = new ArrayList<>();
        conditions = new ArrayList<>();
        joins = new ArrayList<>();
        groupBy = new ArrayList<>();
        orderBy = new ArrayList<>();
    }
    
    /**
     * execute the final query 
     * @param columns are columns which will be get from the database
     * don't send any thing to select * 
     * @return final query as string
     */
    public SelectStatementBuilder get(String ... columns){
        this.selectColumns = new ArrayList(List.of(columns == null ? new String[]{"*"} : columns));
        return this;
    }
    
    /**
     * select the table name here
     * @param tableName is the table name
     * @return 
     */
    public SelectStatementBuilder from(String tableName){
        return from(tableName, tableName);
    }
    public SelectStatementBuilder from(String tableName, String alias){
        this.table = tableName;
        this.alias = alias;
        return this;
    }
    
    /**
     * add condition to the SQL statement
     * @param column is the column in the database to compare
     * @param value value to equal it with the value of the column
     * @return 
     */
    public SelectStatementBuilder where(String column, String value){
        return where(column, "=", value);
    }
    public SelectStatementBuilder where(String column, String operator, String value){
        conditions.add(new Condition(column, operator, value));
        return this;
    }
    
    /**
     * join tables
     * @param table the table to join it
     * @param onFirstTableColumn name of the column of the main table
     * @param equalsSecondTableColumn name of the column of the second table
     * @return 
     */
    public SelectStatementBuilder leftJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn){
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "LEFT");
    }
    public SelectStatementBuilder rightJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn){
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "RIGHT");
    }
    public SelectStatementBuilder innerJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn){
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "INNER");
    }
    public SelectStatementBuilder outerJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn){
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "OUTUER");
    }
    private SelectStatementBuilder join(String table, String alias, String onFirstTableColumn, String equalsSecondTableColumn, String joinType){
        joins.add(new Join(table, alias, onFirstTableColumn, equalsSecondTableColumn, joinType));
        return this;
    }
    
    /**
     * select the columns to group by 
     * @param columns 
     * @return 
     */
    public SelectStatementBuilder groupBy(String... columns){
        groupBy = new ArrayList<>(List.of(columns));
        return this;
    }
    
    /**
     * select the columns to order by
     * @param columns
     * @return 
     */
    public SelectStatementBuilder orderBy(String... columns){
        orderBy = new ArrayList<>(List.of(columns));
        return this;
    }
    
    /**
     * choose the max items to get from database
     * @param limit
     * @return 
     */
    public SelectStatementBuilder limit(int limit){
        this.limit = limit > 0 ? limit : 10;
        return this;
    }
    
    
    @Override
    public String toString(){
        StringBuilder totalSql = new StringBuilder("SELECT ");
        if(!(selectColumns == null || selectColumns.isEmpty())){
            selectColumns.forEach(column -> {
                totalSql.append(column).append(",");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }
        
        totalSql.append(" FROM ").append(table).append(" ").append(alias);
        if(!(joins == null || joins.isEmpty())){
            joins.forEach(join -> totalSql.append(join.getJoin()));
        }
        
        if(!(conditions == null || conditions.isEmpty())){
            totalSql.append(" WHERE ");
            conditions.forEach(condition -> {
                totalSql.append(condition.toString());
                totalSql.append(" AND ");
            });
            totalSql.delete(totalSql.lastIndexOf(" AND "), totalSql.length());
        }
        
        if(!(groupBy == null || groupBy.isEmpty())){
            totalSql.append(" GROUP BY ");
            groupBy.forEach(column -> {
                totalSql.append(column).append(",");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }
        if(!(orderBy == null || orderBy.isEmpty())){
            totalSql.append(" ORDER BY ");
            orderBy.forEach(column -> {
                totalSql.append(column).append(",");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }
        if(limit != -1){
            totalSql.append(" LIMIT ").append(limit);
        }
        return totalSql.toString();
    }
    
}
