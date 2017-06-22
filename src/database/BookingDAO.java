/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author Medis
 */
public class BookingDAO extends DatabaseConnection {

    private PreparedStatement statement = null;
    private PreparedStatement tempStatement = null;
    private Connection dbConnection = null;
    private Connection tempdbConnection = null;
    private ResultSet resultSet = null;
    private ResultSet tempResultSet = null;

    public boolean insertNewBookingRecord(int roomID, String userID, String date, int startTime, int endTime) throws SQLException, IOException {
        boolean isOK = false;
        String bookingNo = getARandomBookingNumber();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("INSERT INTO booking (bookingNo, rooms_roomID, users_userID, date, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, bookingNo);
            statement.setInt(2, roomID);
            statement.setString(3, userID);
            statement.setString(4, date);
            statement.setInt(5, startTime);
            statement.setInt(6, endTime);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been inserted into Booking tables");
        } catch (SQLException e) {
            System.out.println("Failed to save the booking record in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    public List<Bookings> getAllFutureBookings(String userID) throws SQLException, IOException {
        List<Bookings> bookingList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM booking where users_userID LIKE ? AND date >= CURDATE() ORDER BY date DESC");
            statement.setString(1, userID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookingNumber = resultSet.getString("bookingNo");
                int roomID = resultSet.getInt("rooms_roomID");
                String roomName = getRoomNameByID(roomID);
                String userName = resultSet.getString("users_userID");
                String date = resultSet.getString("date");
                int startTimeID = resultSet.getInt("startTime");
                String startTime = getTimeName(startTimeID);
                int endTimeID = resultSet.getInt("endTime");
                String endTime = getTimeName(endTimeID);

                Bookings booking = new Bookings(bookingNumber, roomName, userName, date, startTime, endTime);
                System.out.println("Booking number: " + bookingNumber);
                System.out.println("Room Name: " + roomName);
                System.out.println("User Name: " + userName);
                System.out.println("Date: " + date);
                System.out.println("Start time: " + startTime);
                System.out.println("End time: " + endTime);

                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get bookings' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return bookingList;
    }
    
 
     public boolean deleteARecord(String bookingNo) throws SQLException, IOException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("DELETE FROM booking WHERE bookingNo = ?");
            statement.setString(1, bookingNo);

            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been removed from Booking tables");
        } catch (SQLException e) {
            System.out.println("Failed to remove the booking record from database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }   
    

    public List<Bookings> getAllPassedBookings(String userID) throws SQLException, IOException {
        List<Bookings> bookingList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM booking where users_userID LIKE ? AND date < CURDATE() ORDER BY date DESC");
            statement.setString(1, userID);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String bookingNumber = resultSet.getString("bookingNo");
                int roomID = resultSet.getInt("rooms_roomID");
                String roomName = getRoomNameByID(roomID);
                String userName = resultSet.getString("users_userID");
                String date = resultSet.getString("date");
                int startTimeID = resultSet.getInt("startTime");
                String startTime = getTimeName(startTimeID);
                int endTimeID = resultSet.getInt("endTime");
                String endTime = getTimeName(endTimeID);

                Bookings booking = new Bookings(bookingNumber, roomName, userName, date, startTime, endTime);
                System.out.println("Booking number: " + bookingNumber);
                System.out.println("Room Name: " + roomName);
                System.out.println("User Name: " + userName);
                System.out.println("Date: " + date);
                System.out.println("Start time: " + startTime);
                System.out.println("End time: " + endTime);

                bookingList.add(booking);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get bookings' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return bookingList;
    }
    
    public boolean[] getRoomMapArrayStartTime(int roomID, String date) throws SQLException, IOException {
        boolean[] roomMap = new boolean[29];
        for(int i = 0; i < roomMap.length; i++){
            roomMap[i] = true;
        }
        roomMap[28] = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT startTime, endTime FROM booking where rooms_roomID = ? AND date = ? ORDER BY startTime");
            statement.setInt(1, roomID);
            statement.setString(2, date);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int startTime = resultSet.getInt("startTime");
                int endTime = resultSet.getInt("endTime");
                
                if(startTime == 0){
                    if(endTime == 28){
                        for(int i = 0; i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = 0; i <= (endTime - 1); i++){
                            roomMap[i] = false;
                        }
                    }
                    
                }else if(roomMap[startTime - 1] == false){
                    if(endTime == 28){
                        for(int i = startTime; i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = startTime; i <= (endTime - 1); i++){
                            roomMap[i] = false;
                        }
                    }
                    
                }else{
                    if(endTime == 28){
                        for(int i = startTime; i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = startTime; i <= (endTime - 1); i++){
                            roomMap[i] = false;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get room map records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return roomMap;
    }
    
    
    public boolean[] getRoomMapArrayEndTime(int roomID, String date) throws SQLException, IOException {
        boolean[] roomMap = new boolean[29];
        for(int i = 0; i < roomMap.length; i++){
            roomMap[i] = true;
        }
        roomMap[0] = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT startTime, endTime FROM booking where rooms_roomID = ? AND date = ? ORDER BY startTime");
            statement.setInt(1, roomID);
            statement.setString(2, date);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int startTime = resultSet.getInt("startTime");
                int endTime = resultSet.getInt("endTime");
                
                if(startTime == 0){
                    if(endTime == 28){
                        for(int i = 0; i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = 0; i <= (endTime - 1); i++){
                            roomMap[i] = false;
                        }
                    }
                    
                }else if(roomMap[startTime - 1] == false){
                    if(endTime == 28){
                        for(int i = (startTime + 1); i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = (startTime + 1); i <= endTime; i++){
                            roomMap[i] = false;
                        }
                    }
                    
                }else{
                    if(endTime == 28){
                        for(int i = (startTime + 1); i < roomMap.length; i++){
                            roomMap[i] = false;
                        }
                    }else{
                        for(int i = (startTime + 1); i <= endTime; i++){
                            roomMap[i] = false;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get room map records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return roomMap;
    }

    private String getARandomBookingNumber() throws SQLException, IOException {
        String bookingNo = randomBookingNo();
        while (bookingNumberExists(bookingNo)) {
            bookingNo = randomBookingNo();
        }

        return bookingNo;
    }

    public boolean bookingNumberExists(String bookingNumber) throws SQLException, IOException {
        boolean bookingNumberExists = true;
        String result = "";

        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM booking WHERE bookingNo = ? ");
            statement.setString(1, bookingNumber);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to check booking number." + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                bookingNumberExists = false;
                System.out.println("booking number does not exist");
                break;
            case "1":
                bookingNumberExists = true;
                System.out.println("booking number exists");
                break;
        }
        return bookingNumberExists;
    }

    private String getRoomNameByID(int roomID) throws IOException, SQLException {
        String roomName = null;
        try {
            tempdbConnection = getConnection();
            tempStatement = (PreparedStatement) tempdbConnection.prepareStatement("SELECT roomName FROM rooms WHERE roomID = ? ");
            tempStatement.setInt(1, roomID);
            tempResultSet = tempStatement.executeQuery();
            tempResultSet.next();
            roomName = tempResultSet.getString("roomName");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAllTempConnection();
        }
        return roomName;
    }

    private String getTimeName(int timeID) throws IOException, SQLException {
        String timeName = null;
        try {
            tempdbConnection = getConnection();
            tempStatement = (PreparedStatement) tempdbConnection.prepareStatement("SELECT timeSlot FROM time WHERE ID = ? ");
            tempStatement.setInt(1, timeID);
            tempResultSet = tempStatement.executeQuery();
            tempResultSet.next();
            timeName = tempResultSet.getString("timeSlot");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAllTempConnection();
        }
        return timeName;
    }

    private String randomBookingNo() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String letterPart = RandomStringUtils.random(2, 0, 0, false, false, letters.toCharArray(), new SecureRandom());
        String numberPart = RandomStringUtils.random(4, 0, 0, false, false, numbers.toCharArray(), new SecureRandom());
        String random = letterPart + numberPart;
        return random;
    }

    private void closeAllConnection() throws SQLException {
        if (statement != null) {
            statement.close();
        }

        if (dbConnection != null) {
            dbConnection.close();
        }

        if (resultSet != null) {
            resultSet.close();
        }
    }

    private void closeAllTempConnection() throws SQLException {
        if (tempStatement != null) {
            tempStatement.close();
        }

        if (tempdbConnection != null) {
            tempdbConnection.close();
        }

        if (tempResultSet != null) {
            tempResultSet.close();
        }
    }

}
