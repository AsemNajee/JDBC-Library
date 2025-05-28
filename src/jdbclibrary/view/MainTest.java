package jdbclibrary.view;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import jdbclibrary.database.migration.User;
import jdbclibrary.model.UserModel;

/**
 * @Coder Asem Najee
 */
public class MainTest {

    public static void main(String[] args) throws SQLException {
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
