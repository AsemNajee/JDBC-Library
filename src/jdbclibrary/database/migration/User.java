package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.libs.querybuilder.createschema.Column;

public class User {
    public static void createTable() throws SQLException{
        TableSchema.create("users", 
                Column.integer("id").primary(),
                Column.string("name", 100).unique()
        );
    }
}
