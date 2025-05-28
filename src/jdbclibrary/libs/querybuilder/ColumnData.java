package jdbclibrary.libs.querybuilder;

import jdbclibrary.model.helper.ColumnType;

/**
 * store all column data
 * @author Asem
 */
public class ColumnData {

    /**
     * order of the column 
     * the prop is to help when fill params in Filler
     */
    private int index;
    
    /**
     * type of column data 
     * this help to insert valid data with valid type to database .
     */
    private ColumnType type;
    
    /**
     * value of the column 
     * this is used when we insert or update values in the database .
     */
    private String value;

    public ColumnData(int index) {
        this.index = index;
    }
    
    public ColumnData(int index, ColumnType type) {
        this.index = index;
        this.type = type;
    }
    
    public ColumnData(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public ColumnType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }
    
    

}
