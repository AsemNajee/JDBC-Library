package jdbclibrary.libs.querybuilder.query;

public class Condition{
    private String column;
    private String operator;
    private String value;

    public Condition(String column, String operator, String value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Condition(String column, String value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public String toString() {
        return column + " " + operator + " " + value;
    }
    
}
