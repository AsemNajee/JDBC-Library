package jdbclibrary.model;

import java.util.ArrayList;
import java.util.HashMap;
import jdbclibrary.model.helper.ColumnInfo;

/**
 * @author Asem
 */
public abstract class ModelColumns {
    
    protected HashMap<String, ColumnInfo> columnsInfo;
    
    public ModelColumns(HashMap<String, ColumnInfo> columnsInfo){
        this.columnsInfo = columnsInfo;
    }
    
    public ArrayList<String> getSelectableColumns(){
        return get(ColumnInfo::isCanSelect);
    }
    
    public ArrayList<String> getRequiredColumns(){
        return get(ColumnInfo::isRequired);
    }
    
    public ArrayList<String> getUpdateableColumns(){
        return get(ColumnInfo::isCanUpdate);
    }
    
    
    private ArrayList<String> get(Functional f){
        ArrayList<String> list = new ArrayList<>();
        columnsInfo.forEach((key, value) -> {
            if(f.function(value)){
                list.add(key);
            }
        });
        return list;
    }
}

@FunctionalInterface
interface Functional{
    boolean function(ColumnInfo c);
}
