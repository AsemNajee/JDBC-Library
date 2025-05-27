package jdbclibrary.libs.querybuilder;

import jdbclibrary.model.helper.ColumnType;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class ColumnData {

    private int index;
    private ColumnType type;
    private String value;

    public ColumnData(int index, ColumnType type) {
        this.index = index;
        this.type = type;
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
