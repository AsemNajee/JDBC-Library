package jdbclibrary.model;

import jdbclibrary.model.helper.ColumnInfo;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import jdbclibrary.database.Connector;
import jdbclibrary.model.helper.ColumnType;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Book extends Model{
    public static final HashMap<String, ColumnInfo> columns;
    private static LinkedHashMap<String, String> insertable;
    private static LinkedHashMap<String, String> updateable;
    private static LinkedHashMap<String, String> selectCols;
    static {
        insertable = new LinkedHashMap<>();
        updateable = new LinkedHashMap<>();
        selectCols = new LinkedHashMap<>();
        columns = new HashMap<>();
        columns.put("id", new ColumnInfo(ColumnType.INT, true, false, true));
        columns.put("name", new ColumnInfo(ColumnType.STRING, true, true, true));
        columns.put("age", new ColumnInfo(ColumnType.INT, false, true, true));
        columns.put("time", new ColumnInfo(ColumnType.STRING, false, true, true));
    }
    
    public Book() throws SQLException {
        super("books", insertable, updateable, selectCols);
    }

    @Override
    public void createTable() throws SQLException {
        String sql = """
            CREATE TABLE %s (
                id INT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                teacher_id INT REFERENCES teachers(id),
                time TINYINT CHECK(time > 0 AND time < 5)
            );
        """.formatted(table);
        var conn = Connector.getInstance().getConnection();
        conn.prepareStatement(sql).execute();
    }
    
}

