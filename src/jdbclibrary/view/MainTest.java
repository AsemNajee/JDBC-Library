package jdbclibrary.view;

import java.sql.SQLException;
import java.util.Scanner;
import jdbclibrary.database.migration.User;

/**
 * @Coder Asem Najee
 */
public class MainTest {

    public static void main(String[] args) throws SQLException {
        System.out.println("99. Migrate data ");
        int choise = new Scanner(System.in).nextInt();
        if(choise == 99){
            User.createTable();
        }
    }
}
