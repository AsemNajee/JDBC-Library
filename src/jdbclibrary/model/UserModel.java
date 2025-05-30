package jdbclibrary.model;

import java.sql.SQLException;
import java.util.HashMap;
import jdbclibrary.model.helper.ColumnInfo;
import jdbclibrary.model.helper.ColumnType;

/**
 * @author Asem
 */
public class UserModel extends AbstractModel {

    /**
     * for validation in controllers save each column and its info like type and
     * permissions on it
     */
    public static final HashMap<String, ColumnInfo> columns;

    static {
        columns = new HashMap<>();
        columns.put("id", new ColumnInfo(ColumnType.INT, false, false, true));
        columns.put("name", new ColumnInfo(ColumnType.STRING, true, true, true));
        columns.put("age", new ColumnInfo(ColumnType.INT, false, true, true));
    }

    public UserModel() throws SQLException {
        super("users", columns);
    }

}
