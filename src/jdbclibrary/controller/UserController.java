package jdbclibrary.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdbclibrary.model.AbstractModel;
import jdbclibrary.model.UserModel;

/**
 * example of controller
 * @author Asem Najee
 */
public class UserController{
    
    private static AbstractModel model;
    private static Scanner in = new Scanner(System.in);
    
    /**
     * initial the static instance
     */
    static {
        try {
            model = new UserModel();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
        model.create(data);
    }
    
    public static void update() throws SQLException{
        HashMap<String, String> data = new HashMap<>();
        System.out.print("Enter user id: ");
        int id = in.nextInt();
        System.out.print("Enter user name: ");
        data.put("name", in.nextLine());
        System.out.print("Enter user age: ");
        data.put("age", in.next());
        model.update(id, data);
    }
    
    public static void delete() throws SQLException{
        System.out.print("Enter user id: ");
        int id = in.nextInt();
        model.delete(id);
    }
    
    public static void getByID() throws SQLException{
        System.out.print("Enter user id: ");
        int id = in.nextInt();
        System.out.println(model.get(id));
    }
    
    public static void getWhere() throws SQLException{
        System.out.print("Enter condition column: ");
        String column = in.nextLine();
        System.out.print("Enter condition value: ");
        String value = in.nextLine();
        System.out.println(model.getWhere(column, value));
    }
    
    public static void getAll() throws SQLException{
        System.out.println(model.getAll());
    }
}
