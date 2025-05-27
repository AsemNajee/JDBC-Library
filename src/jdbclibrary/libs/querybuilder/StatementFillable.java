package jdbclibrary.libs.querybuilder;

import java.util.HashMap;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public interface StatementFillable {
    public HashMap<String, ColumnData> getParams();
}
