/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Medis
 */
public class RoomsDAO extends DatabaseConnection {

    private PreparedStatement statement = null;
    private Connection dbConnection = null;
    private ResultSet resultSet = null;

    public boolean insertNewRoom(String roomName, int sits, boolean board, boolean tv, boolean projector, boolean available) throws SQLException, IOException {
        boolean isOK = false;
        int roomID;
        if (getNewRoomID() >= 100) {
            roomID = getNewRoomID();
        } else {
            return isOK;
        }
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("INSERT INTO rooms (roomID, roomName, sits, board, tv, projector, available) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setInt(1, roomID);
            statement.setString(2, roomName);
            statement.setInt(3, sits);
            statement.setBoolean(4, board);
            statement.setBoolean(5, tv);
            statement.setBoolean(6, projector);
            statement.setBoolean(7, available);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been inserted into Rooms tables");
        } catch (SQLException e) {
            System.out.println("Failed to save the room in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    public boolean roomNameExists(String roomName) throws SQLException, IOException {
        boolean roomNameExists = false;
        String result = "";

        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM rooms WHERE roomName = ? ");
            statement.setString(1, roomName);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to check room name." + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                roomNameExists = false;
                System.out.println("room name does not exist");
                break;
            case "1":
                roomNameExists = true;
                System.out.println("room name exists");
                break;
        }
        return roomNameExists;
    }

    public List<Rooms> getAllRooms() throws SQLException, IOException {
        List<Rooms> roomsList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM rooms ORDER BY roomName");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String roomName = resultSet.getString("roomName");
                int sits = resultSet.getInt("sits");
                int board = resultSet.getInt("board");
                String roomBoard = getYesNo(board);
                int tv = resultSet.getInt("tv");
                String roomTv = getYesNo(tv);
                int projector = resultSet.getInt("projector");
                String roomProjector = getYesNo(projector);
                int available = resultSet.getInt("available");
                String roomAvailable = getYesNo(available);

                Rooms room = new Rooms(roomID, roomName, sits, roomBoard, roomTv, roomProjector, roomAvailable);
                System.out.println("Room ID: " + roomID);
                System.out.println("Room Name: " + roomName);
                System.out.println("Sits: " + sits);
                System.out.println("Board: " + roomBoard);
                System.out.println("TV: " + roomTv);
                System.out.println("Projector: " + roomProjector);
                System.out.println("Available: " + roomAvailable);

                roomsList.add(room);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get rooms' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return roomsList;
    }

    public boolean updateRoomFeatures(int roomID, String roomName, int sits, boolean board, boolean tv, boolean projector, boolean available) throws SQLException, IOException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("UPDATE rooms SET roomName = ?, sits = ?, board = ?, tv = ?, projector = ?, available = ? WHERE roomID = ?");
            statement.setString(1, roomName);
            statement.setInt(2, sits);
            statement.setBoolean(3, board);
            statement.setBoolean(4, tv);
            statement.setBoolean(5, projector);
            statement.setBoolean(6, available);
            statement.setInt(7, roomID);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been updated in rooms table");
        } catch (SQLException e) {
            System.out.println("Failed to update the room in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    private int getNewRoomID() throws IOException, SQLException {
        int roomID = -1;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM rooms");
            resultSet = statement.executeQuery();
            resultSet.next();
            roomID = resultSet.getInt("c") + 100;
        } catch (SQLException e) {
            System.out.println("Failed to get room ID." + e);
        } finally {
            closeAllConnection();
        }
        return roomID;
    }
    
    
        public List<Rooms> searchAvailableRoom(int reqSits, boolean reqBoard, boolean reqTv, boolean reqProjector) throws SQLException, IOException {
        List<Rooms> roomsList = new ArrayList();
        
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM rooms WHERE sits >= ? AND board LIKE ? AND TV LIKE ? AND projector LIKE ? AND available = true ORDER BY roomName");
            statement.setInt(1, reqSits);
            if(reqBoard){
                statement.setInt(2, 1);
            }else{
                statement.setString(2, "%");
            }
            if(reqTv){
                statement.setInt(3, 1);
            }else{
                statement.setString(3, "%");
            }
            if(reqProjector){
                statement.setInt(4, 1);
            }else{
                statement.setString(4, "%");
            }
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int roomID = resultSet.getInt("roomID");
                String roomName = resultSet.getString("roomName");
                int sits = resultSet.getInt("sits");
                int board = resultSet.getInt("board");
                String roomBoard = getYesNo(board);
                int tv = resultSet.getInt("tv");
                String roomTv = getYesNo(tv);
                int projector = resultSet.getInt("projector");
                String roomProjector = getYesNo(projector);
                int available = resultSet.getInt("available");
                String roomAvailable = getYesNo(available);

                Rooms room = new Rooms(roomID, roomName, sits, roomBoard, roomTv, roomProjector, roomAvailable);
                System.out.println("Room ID: " + roomID);
                System.out.println("Room Name: " + roomName);
                System.out.println("Sits: " + sits);
                System.out.println("Board: " + roomBoard);
                System.out.println("TV: " + roomTv);
                System.out.println("Projector: " + roomProjector);
                System.out.println("Available: " + roomAvailable);

                roomsList.add(room);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get rooms' records in search from database" + ex);
        } finally {
            closeAllConnection();
        }
        return roomsList;
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

    private String getYesNo(int yesNo) {
        String YesNo;
        switch (yesNo) {
            case 0:
                YesNo = "No";
                break;
            case 1:
                YesNo = "Yes";
                break;
            default:
                YesNo = "Wrong";
                break;
        }
        return YesNo;
    }
    
}
