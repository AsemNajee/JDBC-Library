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
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class Filler {
    public PreparedStatement fillParams(StatementFillable statement, HashMap<String, String> data, HashMap<String, ColumnInfo> columnInfos) throws SQLException{
        PreparedStatement stmt = Connector.getInstance().getConnection()
                .prepareStatement(statement.toString());
        setTypes(statement, columnInfos);
        var params = statement.getParams();
        data.forEach((key, value) -> {
            params.get(key).setValue(value);
        });
        params.forEach((key, value) -> {
            try {
                if(value.getType().equals(ColumnType.STRING)){
                    stmt.setString(value.getIndex(), value.getValue());
                }else{
                    stmt.setInt(value.getIndex(), Integer.parseInt(value.getValue()));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Filler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return stmt;
    }
    
    private StatementFillable setTypes(StatementFillable statement, HashMap<String, ColumnInfo> columnInfo){
        statement.getParams().forEach((key, value) -> {
            value.setType(columnInfo.get(key).getType());
        }); 
        return statement;
    }
    
}
