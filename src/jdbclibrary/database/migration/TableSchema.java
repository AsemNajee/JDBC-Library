package jdbclibrary.database.migration;

import java.sql.SQLException;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.querybuilder.createschema.Column;

/**
 * Create new table in database
 *
 * @author Asem
 */
public final class TableSchema {

    private TableSchema() {
    }

    /**
     * create new table in database
     *
     * @param tableName
     * @param colsData columns as Column class with them data
     * @throws SQLException
     */
    public final static void create(String tableName, Column... colsData) throws SQLException, IllegalArgumentException {
        if (colsData == null || colsData.length == 0) {
            throw new IllegalArgumentException("Columns data must not be empty");
        }
        String sql = "CREATE OR REPLACE TABLE `" + tableName + "` (\n";
        for (Column s : colsData) {
            sql += s.getSql() + ",\n";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += ")";

        try (var c = Connector.getInstance().getConnection(); var stat = c.prepareStatement(sql);) {
            stat.execute();
        }
    }
}
