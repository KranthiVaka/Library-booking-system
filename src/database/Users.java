package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Medis
 */
public class Users {

    public Users(String userID, String name, String email, String tel, String role, String blocked, String accountCreator) {
        this.userID = new SimpleStringProperty(userID);
        this.name = new SimpleStringProperty(name);
        this.email = new SimpleStringProperty(email);
        this.tel = new SimpleStringProperty(tel);
        this.role = new SimpleStringProperty(role);
        this.blocked = new SimpleStringProperty(blocked);
        this.accountCreator = new SimpleStringProperty(accountCreator);

    }

    private final SimpleStringProperty userID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty email;
    private final SimpleStringProperty tel;
    private final SimpleStringProperty role;
    private final SimpleStringProperty blocked;
    private final SimpleStringProperty accountCreator;

    public void setUserID(String UserID) {
        userID.set(UserID);
    }

    public String getUserID() {
        return userID.get();
    }

    public void setName(String Name) {
        name.set(Name);
    }

    public String getName() {
        return name.get();
    }


    public void setEmail(String Email) {
        email.set(Email);
    }

    public String getEmail() {
        return email.get();
    }

    public void setTel(String Tel) {
        tel.set(Tel);
    }

    public String getTel() {
        return tel.get();
    }

    public void setRole(String Role) {
        role.set(Role);
    }

    public String getRole() {
        return role.get();
    }

    public void setBlocked(String Blocked) {
        blocked.set(Blocked);
    }

    public String getBlocked() {
        return blocked.get();
    }

    public void setAccountCreator(String AccountCreator) {
        accountCreator.set(AccountCreator);
    }

    public String getAccountCreator() {
        return accountCreator.get();
    }
}
