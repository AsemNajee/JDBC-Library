package jdbclibrary.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbclibrary.model.UserModel;

/**
 * example of controller
 */
public class UserController extends Controller{
    
    /**
     * use static instance to simplify use the UserController by static methods
     */
    private static UserController controller;
    private static Scanner in = new Scanner(System.in);
    
    /**
     * initial the static instance
     */
    static {
        try {
            controller = new UserController();
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private UserController() throws SQLException {
        super(new UserModel());
    }
    
    /**
     * store new data in the model of the controller instance
     * @throws SQLException 
     */
    public static void store() throws SQLException{
        HashMap<String, String> data = new HashMap<>();
        System.out.print("Enter user name: ");
        data.put("name", in.nextLine());
        System.out.print("Enter user age: ");
        data.put("age", in.next()); // use next() because hash map is type of <string, string>
        controller.model.create(data);
    }
    
}
