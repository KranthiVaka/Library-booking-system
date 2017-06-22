package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * @author: Medis
 */
public class UsersDAO extends DatabaseConnection {

    private PreparedStatement statement = null;
    private Connection dbConnection = null;
    private ResultSet resultSet = null;

    public boolean insertNewUser(String userID, String password, String firstName, String lastName, String email, String tel, int role, int blocked, String accountCreator) throws SQLException, IOException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("INSERT INTO users (userID, password, firstName, lastName, email, tel, role, blocked, accountCreator) VALUES (?, (SHA2(?, 512)), ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, userID);
            statement.setString(2, password);
            statement.setString(3, firstName);
            statement.setString(4, lastName);
            statement.setString(5, email);
            statement.setString(6, tel);
            statement.setInt(7, role);
            statement.setInt(8, blocked);
            statement.setString(9, accountCreator);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been inserted into Users tables");
        } catch (SQLException e) {
            System.out.println("Failed to save the user in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    public boolean changePassword(String userID, String password) throws SQLException, IOException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("UPDATE users SET password = (SHA2(?, 512)) WHERE userID = ?");
            statement.setString(1, password);
            statement.setString(2, userID);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been updated in users table");
        } catch (SQLException e) {
            System.out.println("Failed to update the user in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }
    
    
    public boolean changeEmail(String userID, String email) throws SQLException, IOException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("UPDATE users SET email = ? WHERE userID = ?");
            statement.setString(1, email);
            statement.setString(2, userID);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been updated in users table");
        } catch (SQLException e) {
            System.out.println("Failed to update the user in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    /*
     * It authenticate user  ***************************
     */
    public boolean isAuthenticated(String userName, String password) throws SQLException, IOException {
        boolean isValid = false;
        String result = "";

        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE userID = ? AND password = (SHA2(?, 512))");
            statement.setString(1, userName);
            statement.setString(2, password);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to authenticate user" + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                isValid = false;
                System.out.println("Wrong Password");
                break;
            case "1":
                isValid = true;
                System.out.println("Correct Authentication");
                break;
        }
        return isValid;
    }

    public boolean userIDExists(String userID) throws SQLException, IOException {
        boolean userIDExists = false;
        String result = "";

        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE userID = ? ");
            statement.setString(1, userID);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to check user ID." + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                userIDExists = false;
                System.out.println("userID does not exist");
                break;
            case "1":
                userIDExists = true;
                System.out.println("userID exists");
                break;
        }
        return userIDExists;
    }

    public boolean emialExists(String email) throws SQLException, IOException {
        boolean emailExists = false;
        String result = "";

        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE email = ? ");
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to check email." + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                emailExists = false;
                System.out.println("Email does not exist");
                break;
            case "1":
                emailExists = true;
                System.out.println("Email exists");
                break;
        }
        return emailExists;
    }

    public boolean userIdAndEmailMaches(String userID, String email) throws SQLException, IOException {
        boolean userIDAndEmailMatches = false;
        String result = "";
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT COUNT(*) AS c FROM users WHERE userID = ? AND email = ?");
            statement.setString(1, userID);
            statement.setString(2, email);
            resultSet = statement.executeQuery();
            resultSet.next();
            result = resultSet.getString("c");
        } catch (SQLException e) {
            System.out.println("Failed to check if userID and email matches." + e);
        } finally {
            closeAllConnection();
        }
        switch (result) {
            case "0":
                userIDAndEmailMatches = false;
                System.out.println("userID and email does not match");
                break;
            case "1":
                userIDAndEmailMatches = true;
                System.out.println("userID and email matches");
                break;
        }
        return userIDAndEmailMatches;
    }

    /*
     * It checks if the user is blocked ***************
     */
    public boolean isBlocked(String userName) throws SQLException, IOException {
        boolean isUserBlocked = true;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT blocked FROM users WHERE userID = ? ");
            statement.setString(1, userName);
            resultSet = statement.executeQuery();
            resultSet.next();
            isUserBlocked = resultSet.getBoolean("blocked");
            //System.out.println(isUserBlocked);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAllConnection();
        }
        if (isUserBlocked) {
            System.out.println("User is blocked");
        } else {
            System.out.println("User is not blocked");
        }
        return isUserBlocked;
    }

    public boolean blockUpdate(boolean blockStatus, String userID) throws IOException, SQLException {
        boolean isOK = false;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("UPDATE users SET blocked = ? WHERE userID = ?");
            statement.setBoolean(1, blockStatus);
            statement.setString(2, userID);
            statement.executeUpdate();
            isOK = true;
            System.out.println("Record has been updated in users table");
        } catch (SQLException e) {
            System.out.println("Failed to update the user in database." + e);
        } finally {
            closeAllConnection();
        }
        return isOK;
    }

    /*
     * It checks the user role *******************************************
     */
    public int inRole(String userName) throws SQLException, IOException {
        int role = -1;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT role FROM users WHERE userID = ? ");
            statement.setString(1, userName);
            resultSet = statement.executeQuery();
            resultSet.next();
            role = resultSet.getInt("role");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeAllConnection();
        }

        return role;
    }

    /*
     * This will get all user information *************************************
     */
    public Users getUserInformation(String userId) throws SQLException, IOException {
        Users users = null;
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM users WHERE userID = ?");
            statement.setString(1, userId);
            resultSet = statement.executeQuery();
            resultSet.next();

            String userID = resultSet.getString("userID");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String name = firstName + " " + lastName;
            String email = resultSet.getString("email");
            String tel = resultSet.getString("tel");
            int role = resultSet.getInt("role");
            String userRole = getRole(role);
            int blocked = resultSet.getInt("blocked");
            String blockStatus = getBlockedStatus(blocked);
            String accountCreator = resultSet.getString("accountCreator");

//            System.out.println("UserID: " + userID);
//            System.out.println("First Name: " + firstName);
//            System.out.println("Last Name: " + lastName);
//            System.out.println("Email: " + email);
//            System.out.println("Tel: " + tel);
//            System.out.println("Role: " + role);
//            System.out.println("Blocked: " + blocked);
//            System.out.println("AccountCreator: " + accountCreator);
            users = new Users(userID, name, email, tel, userRole, blockStatus, accountCreator);

        } catch (SQLException ex) {
            System.out.println("Failed to get user records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return users;
    }

    public List<Users> getAllSystemUsers() throws SQLException, IOException {
        List<Users> usersList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM users WHERE role = 1 OR role = 2 ORDER BY userID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String name = firstName + " " + lastName;
                String email = resultSet.getString("email");
                String tel = resultSet.getString("tel");
                int role = resultSet.getInt("role");
                String userRole = getRole(role);
                int blocked = resultSet.getInt("blocked");
                String blockStatus = getBlockedStatus(blocked);
                String accountCreator = resultSet.getString("accountCreator");

                Users user = new Users(userID, name, email, tel, userRole, blockStatus, accountCreator);
                System.out.println("User ID: " + userID);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Full name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Tel: " + tel);
                System.out.println("Role: " + role);
                System.out.println("Blocked: " + blocked);
                System.out.println("Account creator: " + accountCreator);

                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get all system users's records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return usersList;
    }

    public List<Users> getAllUsers() throws SQLException, IOException {
        List<Users> usersList = new ArrayList();
        try {
            dbConnection = getConnection();
            statement = (PreparedStatement) dbConnection.prepareStatement("SELECT * FROM users WHERE role = 2 ORDER BY userID");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String name = firstName + " " + lastName;
                String email = resultSet.getString("email");
                String tel = resultSet.getString("tel");
                int role = resultSet.getInt("role");
                String userRole = getRole(role);
                int blocked = resultSet.getInt("blocked");
                String blockStatus = getBlockedStatus(blocked);
                String accountCreator = resultSet.getString("accountCreator");

                Users user = new Users(userID, name, email, tel, userRole, blockStatus, accountCreator);
                System.out.println("User ID: " + userID);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.println("Full name: " + name);
                System.out.println("Email: " + email);
                System.out.println("Tel: " + tel);
                System.out.println("Role: " + role);
                System.out.println("Blocked: " + blocked);
                System.out.println("Account creator: " + accountCreator);

                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get users's records from database" + ex);
        } finally {
            closeAllConnection();
        }
        return usersList;
    }

    /**
     * It will close all opened datasets and connections
     * *********************************
     *
     * @throws SQLException
     */
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

    private String getRole(int role) {
        String userRole;
        switch (role) {
            case 0:
                userRole = "Supervisor";
                break;
            case 1:
                userRole = "Admin";
                break;
            case 2:
                userRole = "User";
                break;
            default:
                userRole = "Wrong";
                break;
        }
        return userRole;
    }

    private String getBlockedStatus(int blocked) {
        String blockStatus;
        switch (blocked) {
            case 0:
                blockStatus = "Not blocked";
                break;
            case 1:
                blockStatus = "Blocked";
                break;
            default:
                blockStatus = "Wrong";
                break;
        }
        return blockStatus;
    }

}
