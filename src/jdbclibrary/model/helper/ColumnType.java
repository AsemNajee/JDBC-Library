 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package jdbclibrary.model.helper;

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
