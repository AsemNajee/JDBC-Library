package jdbclibrary.libs.filler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbclibrary.database.Connector;
import jdbclibrary.libs.querybuilder.StatementFillable;
import jdbclibrary.model.helper.ColumnInfo;
import jdbclibrary.model.helper.ColumnType;

/**
 * fill prepared statement by values instead of question marks
 *
 * @author Asem
 */
public class Filler {

    /**
     * fill builds statement by values
     *
     * @param statement Builds statement to fill its query statement
     * @param columnInfos columns constraints from data model
     * @return prepared statement filled and ready to execute .
     * @throws SQLException
     */
    public static PreparedStatement fillParams(StatementFillable statement, HashMap<String, ColumnInfo> columnInfos) throws SQLException {
        PreparedStatement stmt = Connector.getInstance().getConnection()
                .prepareStatement(statement.toString());
        setTypes(statement, columnInfos);
        var params = statement.getParams();
        params.forEach((key, value) -> {
            try {
                if (value.getType().equals(ColumnType.STRING)) {
                    stmt.setString(value.getIndex(), value.getValue());
                } else if (value.getType().equals(ColumnType.INT)) {
                    stmt.setInt(value.getIndex(), Integer.parseInt(value.getValue()));
                } else if (value.getType().equals(ColumnType.DECIMAL)) {
                    stmt.setDouble(value.getIndex(), Double.parseDouble(value.getValue()));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Filler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return stmt;
    }

    /**
     * identify every single column by its type (string, integer)
     *
     * @param statement builds statement to set its types
     * @param columnInfo source to get types of each column its comes from data
     * model
     * @return prepared statement to fill in fillParams method
     */
    private static StatementFillable setTypes(StatementFillable statement, HashMap<String, ColumnInfo> columnInfo) {
        statement.getParams().forEach((key, value) -> {
            value.setType(columnInfo.get(key).getType());
        });
        return statement;
    }

}
