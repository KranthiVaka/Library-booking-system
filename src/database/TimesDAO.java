/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Medis
 */
public class TimesDAO extends DatabaseConnection {

    private PreparedStatement statement = null;
    private Connection dbConnection = null;
    private ResultSet resultSet = null;

    public List<Times> getAllTimes() throws SQLException, IOException {
        List<Times> timesList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM time ORDER BY ID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int timeID = resultSet.getInt("ID");
                String timeName = resultSet.getString("timeSlot");

                Times time = new Times(timeID, timeName);
                System.out.println("Time ID: " + timeID);
                System.out.println("Time Name: " + timeName);

                timesList.add(time);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get rooms' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return timesList;
    }

    public List<Times> getAllAvailableTimes(boolean[] roomMap) throws SQLException, IOException {
        List<Times> timesList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM time ORDER BY ID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int timeID = resultSet.getInt("ID");
                String timeName = resultSet.getString("timeSlot");

                if (roomMap[timeID] == true) {
                    Times time = new Times(timeID, timeName);
                    System.out.println("Time ID: " + timeID);
                    System.out.println("Time Name: " + timeName);

                    timesList.add(time);
                }

            }
        } catch (SQLException ex) {
            System.out.println("Failed to get rooms' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return timesList;
    }

    public List<String> getTimeNames() throws SQLException, IOException {
        List<String> timesList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM time ORDER BY ID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String timeName = resultSet.getString("timeSlot");

                System.out.println("Time Name: " + timeName);

                timesList.add(timeName);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get rooms' records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return timesList;
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

}
