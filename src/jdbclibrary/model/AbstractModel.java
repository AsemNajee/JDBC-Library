package jdbclibrary.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.filler.Filler;
import jdbclibrary.libs.querybuilder.Builder;
import jdbclibrary.libs.querybuilder.SelectStatementBuilder;
import jdbclibrary.model.helper.ColumnInfo;
import jdbclibrary.model.pagination.Pagination;

/**
 * @author Asem
 */
public class AbstractModel extends ModelColumns {

    public static final int DEFAULT_LIMIT = 50;
//    public static final int DEFAULT_OFFSET = 0;

    /**
     * table name in the database
     */
    protected String table;

    /**
     * connection instance for the database statements
     */
    private Connection conn;

    public AbstractModel(String table, HashMap<String, ColumnInfo> columnsInfo) throws SQLException {
        super(columnsInfo);
        this.table = table;
        this.conn = Connector.getInstance().getConnection();
    }

    /**
     * insert into database check all columns if they are match selectable
     * column in the model
     *
     * @param data is data from user to insert it to database
     * @return
     * @throws SQLException
     */
    public boolean create(HashMap<String, String> data) throws SQLException {
        var statementFillable = Builder.insert()
                .into(table);
        HashMap<String, String> acceptedColumns = new HashMap<>();
        data.forEach((key, value) -> {
            // logic error
            if (columnsInfo.get(key).isCanSelect()) {
                acceptedColumns.put(key, value);
            }
        });
        statementFillable.values(acceptedColumns);
        PreparedStatement stmt = Filler.fillParams(statementFillable, columnsInfo);
        System.out.println(stmt);
        return stmt.execute();
    }

    /**
     * update data in database check all columns if they are match update able
     * column in the model
     *
     * @param id key of entity to update its data
     * @param data is data from user to update it to database
     * @return
     * @throws SQLException
     */
    public boolean update(int id, HashMap<String, String> data) throws SQLException {
        var statement = Builder.update(table);
        data.forEach((key, value) -> {
            if (columnsInfo.get(key).isCanUpdate()) {
                statement.set(key, value);
            }
        });
        statement.where("id", String.valueOf(id));
        PreparedStatement stmt = Filler.fillParams(statement, columnsInfo);
        System.out.println(stmt);
        return stmt.execute();
    }

    /**
     * delete item form database
     *
     * @param id key of item to delete
     * @return
     * @throws SQLException
     */
    public boolean delete(int id) throws SQLException {
        var statement = Builder.delete()
                .from(table)
                .where("id", String.valueOf(id));
        PreparedStatement stmt = conn.prepareStatement(statement.toString());
        System.out.println(stmt);
        return stmt.execute();
    }

    /**
     * get all
     *
     * @return
     * @throws SQLException
     */
    public Pagination getAll() throws SQLException {
        var statement = Builder.select()
                .from(table)
                .get(getSelectableColumns().toArray(new String[0]));
        return paginate(statement, DEFAULT_LIMIT, 0);
    }

    /**
     * get all items in the database with limitation count
     *
     * @param limit max items return from database
     * @param page number of page start from 1 for first page read more about
     * pagination from internet
     * @return
     * @throws SQLException
     */
    private Pagination paginate(SelectStatementBuilder builder, int limit, int page) throws SQLException {
        ArrayList<HashMap<String, String>> data = new ArrayList<>();
        var statement = builder
                .limit(limit)
                .offset((page - 1) * limit);
        var stmt = conn.prepareStatement(statement.toString());
//        System.out.println(stmt);
        ResultSet res = stmt.executeQuery();
        while (res.next()) {
            data.add(readRow(res));
        }
//        statement = Builder.select("COUNT(*)").from(table);
        statement = builder.get("COUNT(*) as c");
        stmt = conn.prepareStatement(statement.toString());
        res = stmt.executeQuery();
        res.next();
        int count = res.getInt("c");
        Pagination p = new Pagination(data, page, limit, res.getMetaData(), count);
        return p;
    }

    /**
     * get items with condition
     *
     * @param column
     * @param value
     * @return
     * @throws SQLException
     */
    public Pagination getWhere(String column, String value) throws SQLException {
        var statement = Builder.select()
                .from(table)
                .where(column, value)
                .get(columnsInfo.keySet().toArray(new String[1]));
        return paginate(statement, DEFAULT_LIMIT, 0);
    }

    /**
     * get item by its id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public HashMap<String, String> get(int id) throws SQLException {
        var statement = Builder.select()
                .from(table)
                .where("id", String.valueOf(id))
                .get(columnsInfo.keySet().toArray(new String[1]));
        PreparedStatement stmt = conn.prepareStatement(statement.toString());
        ResultSet res = stmt.executeQuery();
        res.next();
        return readRow(res);
    }

    /**
     * execute statement and get the result
     *
     * @param sqlPreparedStatement
     * @return
     * @throws SQLException
     */
//    private ArrayList<HashMap<String, String>> get(String sqlPreparedStatement) throws SQLException {
//        PreparedStatement stmt = conn.prepareStatement(sqlPreparedStatement);
//        ResultSet res = stmt.executeQuery();
//        ArrayList<HashMap<String, String>> result = new ArrayList<>();
//        while (res.next()) {
//            result.add(readRow(res));
//        }
//        res.close();
//        return result;
//    }

    /**
     * read result set to hash map
     * @param res
     * @return
     * @throws SQLException 
     */
    private HashMap<String, String> readRow(ResultSet res) throws SQLException {
        HashMap<String, String> data = new HashMap<>();
        ResultSetMetaData meta = res.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            data.put(meta.getColumnName(i), res.getString(meta.getColumnName(i)));
        }
        return data;
    }
}
