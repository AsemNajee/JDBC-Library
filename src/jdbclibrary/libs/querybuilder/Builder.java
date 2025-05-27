package jdbclibrary.libs.querybuilder;

public class Builder {

    /**
     * select columns from database
     * @return 
     */
    public static SelectStatementBuilder select(){
        return new SelectStatementBuilder();
    }
    /**
     * select columns from data base 
     * @param cols columns which you want to select from db
     * @return 
     */
    public static SelectStatementBuilder select(String ...cols){
        return select().get(cols);
    }
    
    public static InsertStatementBuilder insert(){
        return new InsertStatementBuilder();
    }
    
    public static UpdateStatementBuilder update(String table){
        return new UpdateStatementBuilder(table);
    }
    
    public static DeleteStatementBuilder delete(){
        return new DeleteStatementBuilder();
    }
}
