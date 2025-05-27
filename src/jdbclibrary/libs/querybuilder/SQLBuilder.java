 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package jdbclibrary.libs.querybuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import jdbclibrary.database.HasTableColumns;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class SQLBuilder {
    private ArrayList<String> values;

    private SQLBuilder() {
        values = new ArrayList<>();
    }
    
    /**
     * order and check params values of the inserted model
     * @param dataFromUser params as key value from user
     * @param model is a model to compare its columns names
     * with the params keys and values
     * @return params with same order of this.params columns
     * @throws Exception if there is a column without a value
     * or if the values are filled before
     */
    public static SQLBuilder buildParams(LinkedHashMap<String, String> dataFromUser, HasTableColumns model) throws Exception{
        SQLBuilder ob = new SQLBuilder();
        for(String column : model.getColumns()){
            if(dataFromUser.containsKey(column)){
                ob.values.add(dataFromUser.get(column));
            }else{
                ob.values.clear();
                throw new Exception("Necessary column required [" + column + "]");
            }
        }
        return ob;
    }
    
    /**
     * build sql pre statement for insert into database
     * @param tableName table name in the database
     * @return final sql statement
     */
    public String getInsertSQLStatement(String tableName){
        String preParams = "";
        for(int i = 0; i < values.size(); i++){
            preParams += preParams.equals("") ? " ?" : ", ?";
        }
        String statement = "INSERT INTO " + tableName + " VALUES (";
        statement += preParams + ") " ;
        return statement;
    }
    
    /**
     * build sql pre statement for insert into database
     * @param tableName table name in the database
     * @return final sql statement
     */
    public String getUpdateSQLStatement(String tableName, HasTableColumns model){
        String preParams = "";
        for(int i = 0; i < values.size(); i++){
            preParams += preParams.equals("") ? " ?" : ", ?";
        }
        String statement = "UPDATE " + tableName + " SET ";
        for (String updateableColumn : model.getColumns()) {
            statement += " " + updateableColumn + " = ? ,";
        }
        statement = statement.substring(0, statement.length() -1);
        statement += " WHERE id = ?";
        return statement;
    }
    
    public static String getDeleteSQlStatement(String tableName){
        String statement = "DELETE FROM " + tableName + " WHERE id = ?";
        return statement;
    }
    
    public static String getSelectOneSQLStatement(String tableName, HasTableColumns model){
        String statement = getSelectAllSQLStatement(tableName, model);
        statement += " WHERE id = ?";
        return statement;
    }
    
    public static String getSelectAllSQLStatement(String tableName, HasTableColumns model){
        String statement = "SELECT";
        for(String column : model.getColumns()){
            statement += " " + column + ",";
        }
        statement = statement.substring(0, statement.length()-1);
        statement += " FROM " + tableName;
        return statement;
    }
}