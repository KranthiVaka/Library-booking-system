package group4u_booking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Medis
 */
public class UserInfo {

    Properties prop;
    InputStream inputStream;
    final static String propFileName = "/resources/config.properties";

    private static UserInfo userInfo;
    private String userID;
    private String email;
    private String firstName;

    private final String databaseIpAddress;
    private final String databaseName;
    private final String databaseUserName;
    private final String databasePassword;
    private final String emailSender;
    private final String emailPassword;

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(UserInfo userInfo) {
        UserInfo.userInfo = userInfo;
    }

    private UserInfo() throws IOException {
        userID = "";
        email = "";
        firstName = "";
        databaseIpAddress = getAProperties("DATABASE_IP_ADDRESS");
        databaseName = getAProperties("DATABASE_NAME");
        databaseUserName = getAProperties("DATABASE_USER_NAME");
        databasePassword = getAProperties("DATABASE_PASSWORD");
        emailSender = getAProperties("EMAIL");
        emailPassword = getAProperties("EMAIL_PASSWORD");

    }

    public static UserInfo getInstance() throws IOException {
        if (userInfo == null) {
            userInfo = new UserInfo();
        }
        return userInfo;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDatabaseIpAddress() {
        return databaseIpAddress;
    }
    
    public String getDatabaseName(){
        return databaseName;
    }
    
    public String getDatabaseUserName(){
        return databaseUserName;
    }
    
    public String getDatabasePassword(){
        return databasePassword;
    }
    
    public String getEmailSender(){
       return emailSender; 
    }
    
    public String getEmailPassword(){
        return emailPassword;
    }

    private String getAProperties(String propertyName) throws IOException {
        String result = null;
        try {
            prop = new Properties();
            inputStream = getClass().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
            result = prop.getProperty(propertyName);

        } catch (IOException e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return result;
    }
}
