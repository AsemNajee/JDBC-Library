 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package jdbclibrary.database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Make the Model able to Insert it to the database
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public interface DatabaseInsertable {
    /**
     * get all columns of the table to insert
     * example [name, age, ...]
     * @return columns
     */
    Set<String> getInsertableColumns();
    
    /**
     * get all columns of the table to insert
     * example [name, age, ...]
     * @return columns
     */
    Set<String> getUpdateableColumns();
    
    /**
     * get all columns of the table to insert
     * example [name, age, ...]
     * @return columns
     */
    Set<String> getSelectColumns();
    
    /**
     * get validations roles for every column
     * 
     * @return 
     */
//    String[] getValidationRoles();
    
    /**
     * get values of the model
     * the values are from user
     * @return all values for insert of update to the database
     */
//    HashMap<String, String> getValues();
    
    /**
     * execute setInt and setString for all model 
     * with deferent value and types of data
     * @param p is the statement which build before
     * @param data is the data to insert into params
     * @return 
     */
    PreparedStatement putInsertValuesToParams(PreparedStatement p, LinkedHashMap<String, String> data) throws SQLException;
    
    
    int putUpdateValuesToParams(PreparedStatement p, LinkedHashMap<String, String> data) throws SQLException;
}


