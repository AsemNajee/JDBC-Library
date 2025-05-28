package jdbclibrary.controller;

import jdbclibrary.model.AbstractModel;

/**
 * control functions of the system
 * @author Asem Najee
 */
public class Controller {
    protected AbstractModel model ;
    
    public Controller(AbstractModel model){
        this.model = model;
    }
}
