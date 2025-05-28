package jdbclibrary.libs.querybuilder.query;

public class Join{
    private String table;
    private String alias;
    private String firstTableColumn;
    private String secondTableColumn;
    private String type;

    public Join(String table, String alias, String firstTableColumn, String secondTableColumn, String type) {
        this.table = table;
        this.alias = alias;
        this.firstTableColumn = firstTableColumn;
        this.secondTableColumn = secondTableColumn;
        this.type = type;
    }
    
    public String getJoin(){
        String join = "";
        join += " " + type + " JOIN `" + table + "` `" + alias + "` ON `" + firstTableColumn + "` = `" + secondTableColumn + "`";
        return join;
    }
}