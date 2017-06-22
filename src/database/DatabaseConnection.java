package database;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.Connection;
import group4u_booking.UserInfo;
import java.io.IOException;

import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * @author: Medis
 */
public class DatabaseConnection {

    protected Connection getConnection() throws SQLException, IOException {

        System.out.println("********** mysql connection testing ***********");
        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            System.out.println("Where is mysql driver?");
        }

        System.out.println("Mysql Driver registered");

        Connection connection = null;

        try {

            // If you want to use your local database (Please change password)
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + UserInfo.getInstance().getDatabaseIpAddress() + "/" + UserInfo.getInstance().getDatabaseName() + "?user=" + UserInfo.getInstance().getDatabaseUserName() + "&password=" + UserInfo.getInstance().getDatabasePassword());

        } catch (CommunicationsException e) {
            System.out.println("Connection Failed: Check output console");
        }

        if (connection != null) {
            System.out.println("You made it: take control of your database");
        } else {

            System.out.println("Failed to make connection");
        }
        return connection;
    }

}
