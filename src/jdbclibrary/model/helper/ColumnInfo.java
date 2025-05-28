package jdbclibrary.model.helper;

/**
 * @author Asem
 */
public class ColumnInfo{
    private ColumnType type;
    private boolean required;
    private boolean canUpdate;
    private boolean canSelect;

    public ColumnInfo(ColumnType type, boolean required, boolean canUpdate, boolean canSelect) {
        this.type = type;
        this.required = required;
        this.canUpdate = canUpdate;
        this.canSelect = canSelect;
    }

    public ColumnType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public boolean isCanSelect() {
        return canSelect;
    }
    
}

