/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Medis
 */
public class Times {
    
    public Times(int timeID, String timeName){
        this.timeID = new SimpleIntegerProperty(timeID);
        this.timeName = new SimpleStringProperty(timeName);
    }
    
    public void setTimeID(int TimeID) {
        timeID.set(TimeID);
    }

    public int getTimeID() {
        return timeID.get();
    }
    
    public void setTimeName(String TimeName){
        timeName.set(TimeName);
    }
    
    public String getTimeName(){
        return timeName.get();
    }
    @Override
    public String toString() {
        return getTimeName();
    }
    
    
    private final SimpleIntegerProperty timeID;
    private final SimpleStringProperty timeName;
    
}
