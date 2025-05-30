package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import java.util.List;
import jdbclibrary.libs.querybuilder.query.Condition;
import jdbclibrary.libs.querybuilder.query.Join;
import jdbclibrary.model.AbstractModel;

/**
 * Build sql statements for select statements
 *
 * @author Asem
 */
public class SelectStatementBuilder {

    /**
     * list of columns to select them from the database .
     */
    private ArrayList<String> selectColumns;
    private String table;
    private String alias;
    /**
     * list of conditions
     */
    private ArrayList<Condition> conditions;
    /**
     * list of joins
     */
    private ArrayList<Join> joins;
    /**
     * list of columns names to group by
     */
    private ArrayList<String> groupBy;
    /**
     * list of columns to order by
     */
    private ArrayList<String> orderBy;
    /**
     * max count rows in query of select
     */
    private int limit = -1;
    /**
     * start from row number
     */
    private int offset = 0;

    SelectStatementBuilder() {
        selectColumns = new ArrayList<>();
        conditions = new ArrayList<>();
        joins = new ArrayList<>();
        groupBy = new ArrayList<>();
        orderBy = new ArrayList<>();
    }

    /**
     * select columns which will get from database .
     *
     * @param columns are columns which will be get from the database don't send
     * any thing to select
     *
     * @return final query as string
     */
    public SelectStatementBuilder get(String... columns) {
        this.selectColumns = new ArrayList(List.of(columns == null ? new String[]{"*"} : columns));
        return this;
    }

    /**
     * select the table name here
     *
     * @param tableName is the table name
     * @return
     */
    public SelectStatementBuilder from(String tableName) {
        return from(tableName, tableName);
    }

    /**
     * select the table name here
     *
     * @param tableName is the table name
     * @param alias is alias for table name this used when we use join
     * conditions
     * @return
     */
    public SelectStatementBuilder from(String tableName, String alias) {
        this.table = tableName;
        this.alias = alias;
        return this;
    }

    /**
     * add condition to the SQL statement
     *
     * @param column is the column in the database to compare
     * @param value value to equal it with the value of the column
     * @return
     */
    public SelectStatementBuilder where(String column, String value) {
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
    public SelectStatementBuilder where(String column, String operator, String value) {
        conditions.add(new Condition(column, operator, value));
        return this;
    }

    /**
     * join tables
     *
     * @param table the table to join it
     * @param onFirstTableColumn name of the column of the main table
     * @param equalsSecondTableColumn name of the column of the second table
     * @return
     */
    public SelectStatementBuilder leftJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn) {
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "LEFT");
    }

    public SelectStatementBuilder rightJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn) {
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "RIGHT");
    }

    public SelectStatementBuilder innerJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn) {
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "INNER");
    }

    public SelectStatementBuilder outerJoin(String table, String onFirstTableColumn, String equalsSecondTableColumn) {
        return join(table, table, onFirstTableColumn, equalsSecondTableColumn, "OUTER");
    }

    /**
     * select the columns to group by
     *
     * @param columns
     * @return
     */
    public SelectStatementBuilder groupBy(String... columns) {
        groupBy = new ArrayList<>(List.of(columns));
        return this;
    }

    /**
     * select the columns to order by
     *
     * @param columns
     * @return
     */
    public SelectStatementBuilder orderBy(String... columns) {
        orderBy = new ArrayList<>(List.of(columns));
        return this;
    }

    /**
     * choose the max items to get from database
     *
     * @param limit
     * @return
     */
    public SelectStatementBuilder limit(int limit) {
        this.limit = limit > 0 ? limit : AbstractModel.DEFAULT_LIMIT;
        return this;
    }

    /**
     * choose how many row skip before start get data
     *
     * @param offset
     * @return
     */
    public SelectStatementBuilder offset(int offset) {
        this.offset = offset > 0 ? offset : 0;
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
            throw new IllegalStateException("unknown table name");
        }
        if (selectColumns == null || selectColumns.size() == 0) {
            throw new IllegalStateException("unknown select columns");
        }
        StringBuilder totalSql = new StringBuilder("SELECT ");
        if (!(selectColumns == null || selectColumns.isEmpty())) {
            selectColumns.forEach(column -> {
                String columnName[] = column.split(" as ");
                String name = columnName[0];
                String alias = columnName.length > 1 ? columnName[1] : "";
                if (name.contains(" ")) {
                    totalSql.append("`").append(name).append("`");
                    if (!alias.equals("")) {
                        totalSql.append(" as `").append(alias).append("`");
                    }
                } else {
                    totalSql.append(column);
                }
                totalSql.append(",");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }

        totalSql.append(" FROM `").append(table).append("` `").append(alias).append("`");
        if (!(joins == null || joins.isEmpty())) {
            joins.forEach(join -> totalSql.append(join.getJoin()));
        }

        if (!(conditions == null || conditions.isEmpty())) {
            totalSql.append(" WHERE ");
            conditions.forEach(condition -> {
                totalSql.append(condition.toString());
                totalSql.append(" AND ");
            });
            totalSql.delete(totalSql.lastIndexOf(" AND "), totalSql.length());
        }

        if (!(groupBy == null || groupBy.isEmpty())) {
            totalSql.append(" GROUP BY ");
            groupBy.forEach(column -> {
                totalSql.append("`").append(column).append("`,");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }
        if (!(orderBy == null || orderBy.isEmpty())) {
            totalSql.append(" ORDER BY ");
            orderBy.forEach(column -> {
                totalSql.append("`").append(column).append("`,");
            });
            totalSql.delete(totalSql.lastIndexOf(","), totalSql.length());
        }
        if (offset != 0) {
            totalSql.append(" OFFSET ").append(offset);
        }
        if (limit != -1) {
            totalSql.append(" LIMIT ").append(limit);
        }
        return totalSql.toString();
    }

    private SelectStatementBuilder join(String table, String alias, String onFirstTableColumn, String equalsSecondTableColumn, String joinType) {
        joins.add(new Join(table, alias, onFirstTableColumn, equalsSecondTableColumn, joinType));
        return this;
    }

}
