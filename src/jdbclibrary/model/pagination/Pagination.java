package jdbclibrary.model.pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.ResultSetMetaData;

/**
 * @author Asem
 */
public class Pagination {
    private ArrayList<HashMap<String, String>> data;
    private int totalCount;
    private int currentPage;
    private int perPage;
    private int nextPage;
    private int prevPage;
    private int lastPage;
    private int from;
    private int to;
    private ResultSetMetaData meta;

    public Pagination(ArrayList<HashMap<String, String>> data, int currentPage, int perPage, ResultSetMetaData meta, int totalCount) {
        this.data = data;
        this.meta = meta;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.perPage = perPage;
        this.lastPage = totalCount / perPage;
        this.from = (currentPage * perPage);
        this.to = from + data.size();
        this.nextPage = currentPage == lastPage ? 0 : currentPage +1 ;
        this.prevPage = currentPage == 0 ? 0 : currentPage -1;
    }

    public ArrayList<HashMap<String, String>> getData() {
        return data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public ResultSetMetaData getMeta() {
        return meta;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pagination{");
        sb.append("data=").append(data);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", currentPage=").append(currentPage);
        sb.append(", perPage=").append(perPage);
        sb.append(", nextPage=").append(nextPage);
        sb.append(", prevPage=").append(prevPage);
        sb.append(", lastPage=").append(lastPage);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", meta=").append(meta);
        sb.append('}');
        return sb.toString();
    }
    
    
    
    
    
}
