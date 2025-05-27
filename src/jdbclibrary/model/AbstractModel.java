package jdbclibrary.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.filler.Filler;
import jdbclibrary.libs.querybuilder.Builder;
import jdbclibrary.model.helper.ColumnInfo;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class AbstractModel extends ModelColumns {

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

    public boolean create(HashMap<String, String> data) throws SQLException {
        var statementFillable = Builder.insert()
                .into(table)
                .values(data);
        PreparedStatement stmt = Filler.fillParams(statementFillable, columnsInfo);
        System.out.println(stmt);
        return stmt.execute();
    }

    public boolean update(int id, HashMap<String, String> data) throws SQLException {
        var statement = Builder.update(table);
        data.forEach((key, value) -> {
            statement.set(key, value);
        });
        statement.where("id", String.valueOf(id));
        PreparedStatement stmt = Filler.fillParams(statement, columnsInfo);
        System.out.println(stmt);
        return stmt.execute();
    }

    public boolean delete(int id) throws SQLException {
        var statement = Builder.delete()
                .from(table)
                .where("id", String.valueOf(id));
        PreparedStatement stmt = conn.prepareStatement(statement.toString());
        System.out.println(stmt);
        return stmt.execute();
    }

    public ArrayList<HashMap<String, String>> getAll() throws SQLException {
        var statement = Builder.select()
                .from(table)
                .get((String[]) columnsInfo.keySet().toArray());
        return get(statement.toString());
    }
    
    public ArrayList<HashMap<String, String>> getWhere(String column, String value) throws SQLException{
        var statement = Builder.select()
                .from(table)
                .where(column, value)
                .get(columnsInfo.keySet().toArray(new String[1]));
        return get(statement.toString());
    }

    public HashMap<String, String> get(int id) throws SQLException {
        var statement = Builder.select().from(table).where("id", String.valueOf(id))
                .get( columnsInfo.keySet().toArray(new String[1]));
        return get(statement.toString()).get(0);
    }
    
    private ArrayList<HashMap<String, String>> get(String sqlPreparedStatement) throws SQLException{
        PreparedStatement stmt = conn.prepareStatement(sqlPreparedStatement);
        System.out.println(stmt);
        ResultSet res = stmt.executeQuery();
        ArrayList<HashMap<String, String>> result = new ArrayList<>();
        while (res.next()) {
            result.add(readRow(res));
        }
        return result;
    }

    private HashMap<String, String> readRow(ResultSet res) throws SQLException {
        HashMap<String, String> data = new HashMap<>();
        for (String s : super.getSelectableColumns()) {
            data.put(s, res.getString(s));
        }
        return data;
    }
    
//    public static boolean execute(StatementFillable statement){
//        PreparedStatement stmt = Filler.fillParams(statement, columnInfo);
//    }

}
