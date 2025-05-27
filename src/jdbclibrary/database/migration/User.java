package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.libs.querybuilder.createschema.Column;

/**
 * create table in the database by migrations
 */
public class User{
    public static void createTable() throws SQLException{
        TableSchema.create("users", 
                Column.integer("id").primary(),
                Column.string("name", 100).unique(),
                Column.integer("age", 2).check("age > 18")
        );
    }
}
