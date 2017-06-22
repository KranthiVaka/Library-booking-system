/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Medis
 */
public class Bookings {
    
    public Bookings(String bookingNo, String roomName, String userID, String date, String startTime, String endTime){
        this.bookingNo = new SimpleStringProperty(bookingNo);
        this.roomName = new SimpleStringProperty(roomName);
        this.userID = new SimpleStringProperty(userID);
        this.date = new SimpleStringProperty(date);
        this.startTime = new SimpleStringProperty(startTime);
        this.endTime = new SimpleStringProperty(endTime);
    }

  
    
    public void setBookingNo(String BookingNo){
        bookingNo.set(BookingNo);
    }
    
    public String getBookingNo(){
        return bookingNo.get();
    }
    
    public void setRoomID(String RoomName){
        roomName.set(RoomName);
    }
    
    public String getRoomName(){
        return roomName.get();
    }
    
    public void setUserID(String UserID){
        userID.set(UserID);
    }
    
    public String getUserID(){
        return userID.get();
    }
    
    public void setDate(String Date){
        date.set(Date);
    }
    
    public String getDate(){
        return date.get();
    }
    
    public void setStartTime(String StartTime){
        startTime.set(StartTime);
    }
    
    public String getStartTime(){
        return startTime.get();
    }
    
    public void setEndTime(String EndTime){
        endTime.set(EndTime);
    }
    
    public String getEndTime(){
        return endTime.get();
    }
    
    private final SimpleStringProperty bookingNo;
    private final SimpleStringProperty roomName;
    private final SimpleStringProperty userID;
    private final SimpleStringProperty date;
    private final SimpleStringProperty startTime;
    private final SimpleStringProperty endTime;
}
