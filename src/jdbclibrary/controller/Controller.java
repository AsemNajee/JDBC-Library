package jdbclibrary.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import jdbclibrary.model.Model;


public abstract class Controller {
    protected Model model;
    
    /**
     * create new instance
     * @param model is the type of data
     */
    public Controller(Model model){
        this.model = model;
    }
    
    /**
     * store new element in database
     * @param item the item which will store
     * @return confirmation
     */
    public boolean store(LinkedHashMap<String, String> item) throws Exception{
        return model.create(item);
    }
    
    /**
     * get a specific item from database 
     * @param id is an id of the item to get
     * @return 
     */
    public LinkedHashMap<String, String> get(int id) throws SQLException{
        return model.get(id);
    }
    
    /**
     * get all items in the database
     * @return a collection of elements
     */
    public ArrayList<LinkedHashMap<String, String>> getAll() throws SQLException{
        return model.getAll();
    }
    
    /**
     * update the existing value
     * @param id the id of element to update
     * @param newData new data to update
     * @return old data
     */
    public boolean update(int id, LinkedHashMap<String, String> newData) throws Exception{
        return model.update(id, newData);
    }
    
    /**
     * delete existing element
     * @param id the identifier of element to delete
     * @return current element which deleted
     */
    public LinkedHashMap<String, String> delete(int id) throws SQLException{
        return model.delete(id);
    }
    
}
