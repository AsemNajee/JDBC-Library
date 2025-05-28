package jdbclibrary.model.helper;

/**
 * constraint on type of column accepted to create in the database .
 * @author Asem
 */
public enum ColumnType{
    STRING("string"), INT("int");
    
    private String type;
    
    ColumnType(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
}
