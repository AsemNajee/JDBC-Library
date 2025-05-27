 /**
  *   >> Al-Reecha .~
  *   << BY : Asem Najee >>
  */

package jdbclibrary.view;

import java.sql.SQLException;
import java.util.HashMap;
import jdbclibrary.libs.filler.Filler;
import jdbclibrary.libs.querybuilder.Builder;
import jdbclibrary.model.Book;

/**
 * @Coder Asem Najee
 * @author Al-Reecha
 */
public class MainTest {
    public static void main(String[] args) throws SQLException {
//        String sql = Builder.select()
//                .get("s.name", "s.age", "c.name")
//                .groupBy("age")
//                .leftJoin("collage c", "s.collage_id", "c.id")
//                .orderBy("name")
//                .from("students", "s")
//                .limit(4).toString();
//        System.out.println(sql);
        HashMap<String, String> value = new HashMap<>();
        value.put("name", "Asem");
        value.put("id", "200");
//        System.out.println(Builder.insert().into("students").values(value));
//        System.out.println(Builder.update("students").set("name", "ali").set("age", "50").where("id", "20"));
//        System.out.println(Builder.delete().from("students").where("id", "20"));
        Filler filler = new Filler();
        var sql = Builder.insert().into("test").values(value);
        var stmt = filler.fillParams(sql, value, Book.columns);
        System.out.println(stmt);
//        stmt.execute();
        System.out.println("done");
    }
}
