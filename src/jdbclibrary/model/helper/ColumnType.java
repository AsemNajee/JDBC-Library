package jdbclibrary.model.helper;

/**
 * constraint on type of column accepted to create in the database .
 *
 * @author Asem
 */
public enum ColumnType {
    // add new types here if needed like DATE, TIME, etc.
    STRING("string"), INT("int"), DECIMAL("decimal");

    private String type;

    ColumnType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
