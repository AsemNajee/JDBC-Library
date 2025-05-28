package jdbclibrary.libs.querybuilder;

import java.util.HashMap;

public class Builder {

    /**
     * build query for select columns from database 
     * @return 
     */
    public static SelectStatementBuilder select(){
        return new SelectStatementBuilder();
    }
    
    /**
     * build query for select columns from database 
     * @param cols columns which you want to select from db
     * @return 
     */
    public static SelectStatementBuilder select(String ...cols){
        return select().get(cols);
    }
    
    /**
     * build query for insert data into the database .
     * @return 
     */
    public static InsertStatementBuilder insert(){
        return new InsertStatementBuilder();
    }
    
    /**
     * build query to insert data into the database
     * @param data key value of data to insert them to database
     * @return 
     */
    public static InsertStatementBuilder insert(HashMap<String, String> data){
        return new InsertStatementBuilder().values(data);
    }
    
    /**
     * build query to update data in the database 
     * @param table is table name to update it 
     * @return 
     */
    public static UpdateStatementBuilder update(String table){
        return new UpdateStatementBuilder(table);
    }
    
    /**
     * build query to delete item from database .
     * @return 
     */
    public static DeleteStatementBuilder delete(){
        return new DeleteStatementBuilder();
    }
}
