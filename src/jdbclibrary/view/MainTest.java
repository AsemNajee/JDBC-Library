package jdbclibrary.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import jdbclibrary.database.migration.User;
import jdbclibrary.libs.filler.Filler;
import jdbclibrary.libs.querybuilder.Builder;
import jdbclibrary.model.UserModel;

/**
 * @Coder Asem Najee
 */
public class MainTest {

    public static void main(String[] args) throws SQLException {
        System.out.println(Builder.select("name", "age").from("users").where("id", "=", "1"));

        {
            HashMap<String, String> data = new HashMap<>();
            data.put("name", "Asem");
            data.put("age", "21");
            System.out.println(Builder.insert().into("users").values(data));
        }
        System.out.println(Builder.update("users").set("name", "Asem update").set("age", "20").where("id", "=", "1"));

        System.out.println(Builder.delete().from("users").where("id", "=", "1"));

        System.out.println(Builder.select().from("users").get("name", "age").where("id", "=", "1"));

        System.out.println("\nAll of above are examples of builders in the library, you can use them to get more control in your application\n");
        Scanner in = new Scanner(System.in);
        System.out.println("Wellcom !");
        System.out.println("Don't forgite, this library is for mysql");
        System.out.println("it will not work with any anuther DBMS");
        System.out.print("\npress any key to continue: ");
        in.nextLine();
        do {

            System.out.print("""
                            1. insert data
                            2. update data
                            3. delete data
                            4. getAll
                            5. get by id
                            6. get where
                            0. migrate data
                            Enter your choise: """);
            int choise = in.nextInt();
            if (choise == 0) {
                User.createTable();
            }

            switch (choise) {
                case 1 -> {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("name", "Asem" + Math.random());
                    data.put("age", "21");
                    new UserModel().create(data);
                }
                case 2 -> {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("name", "Asem udpate");
                    data.put("age", "20");
                    new UserModel().update(1, data);
                }
                case 3 -> {
                    new UserModel().delete(1);
                }
                case 4 -> {
                    System.out.println(new UserModel().getAll());
                }
                case 5 -> {
                    System.out.println(new UserModel().get(3));
                }
                case 6 -> {
                    System.out.println(new UserModel().getWhere("name", "Asem0.44427053939377836"));
                }
                case 0 -> {
                    User.createTable();
                    System.out.println("done");
                }
            }

        } while (true);
    }
}
