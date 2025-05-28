package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.libs.querybuilder.createschema.Column;

/**
 * create users table in the database by migrations
 * @author Asem
 */
public class User{
    public static void createTable() throws SQLException{
        TableSchema.create("users", 
                Column.integer("id").primary(true),
                Column.string("name", 100).unique(),
                Column.integer("age", 2).check("age > 18")
        );
    }
}
