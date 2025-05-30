package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import java.util.HashMap;
import jdbclibrary.libs.querybuilder.query.Condition;

/**
 * @author Asem
 */
public class UpdateStatementBuilder implements StatementFillable {

    private int index = 1;
    private ArrayList<Condition> conditions;
    private String table;
    private HashMap<String, ColumnData> params;

    UpdateStatementBuilder(String table) {
        this.params = new HashMap<>();
        this.table = table;
        this.conditions = new ArrayList<>();
    }

    /**
     * select column to update and give it a new value
     *
     * @param column name of column in the database
     * @param value new value to update
     * @return
     */
    public UpdateStatementBuilder set(String column, String value) {
        params.put(column, new ColumnData(index++, value));
        return this;
    }

    /**
     * getter for params member
     *
     * @return
     */
    public HashMap<String, ColumnData> getParams() {
        return this.params;
    }

    /**
     * add condition to the SQL statement
     *
     * @param column is the column in the database to compare
     * @param value value to equal it with the value of the column
     * @return
     */
    public UpdateStatementBuilder where(String column, String value) {
        return where(column, "=", value);
    }

    /**
     * add condition to the SQL statement
     *
     * @param column is the column in the database to compare
     * @param operator is the operator to compare column and value
     * @param value value to equal it with the value of the column
     * @return
     */
    public UpdateStatementBuilder where(String column, String operator, String value) {
        conditions.add(new Condition(column, operator, value));
        return this;
    }

    /**
     * get final sql statement
     *
     * @return
     */
    @Override
    public String toString() {
        if (table == null) {
            throw new IllegalStateException("unkown table name");
        }
        if (params == null) {
            throw new IllegalStateException("unkown params values and keys");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `").append(table).append("` SET ");
        params.keySet().forEach(col -> {
            sb.append("`").append(col).append("` = ?,");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" ");
        if (!(conditions == null || conditions.isEmpty())) {
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
