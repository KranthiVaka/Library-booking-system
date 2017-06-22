/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import database.Users;
import database.UsersDAO;
import group4u_booking.Main;
import group4u_booking.UserInfo;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.mail.internet.AddressException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.EmailValidator;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class UserManagementController implements Initializable {

    Main main = new Main();
    EmailValidator validator = EmailValidator.getInstance();
    private final Color redColor = Color.rgb(204, 0, 0);
    private final Color greenColor = Color.rgb(81, 170, 0);

    private ArrayList<Users> usersInformation = new ArrayList<>();
    UsersDAO userDAO = new UsersDAO();

    @FXML
    private Button userButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TextField userIDTextField;
    @FXML
    private Color x2;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private Color x3;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField mobileTextField;
    @FXML
    private Button registerButton;
    @FXML
    private Font x4;
    @FXML
    private RadioButton userRadioButton;
    @FXML
    private ToggleGroup UserRole;
    @FXML
    private RadioButton systemAdminRadioButton;
    @FXML
    private TableView<Users> userTableView;
    @FXML
    private MenuItem refreshMenuItem;
    @FXML
    private TableColumn<Users, String> userIDTableColumn;
    @FXML
    private TableColumn<Users, String> nameTableColumn;
    @FXML
    private TableColumn<Users, String> emailTableColumn;
    @FXML
    private TableColumn<Users, String> blockedStatusTableColumn;
    @FXML
    private TableColumn<Users, String> roleTableColumn;
    @FXML
    private ComboBox<String> actionComboButton;
    @FXML
    private Button submitActionButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");

    }

    @FXML
    private void profileOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/AdminMain.fxml");
    }

    @FXML
    private void roomManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/RoomManagement.fxml");
    }

    @FXML
    private void bookingManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/BookingManagement.fxml");
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }

    private boolean alertWindowResult(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        DialogPane dialogPane = alert.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/icon.png").toString()));
        dialogPane.getStylesheets().add(
                getClass().getResource("/resources/style.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmationDialog");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void clearRegistrationForm() {
        userIDTextField.clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
        emailTextField.clear();
        userRadioButton.selectedProperty().set(true);
    }

    public void setMessageLabel(String txt, Color color) {
        statusLabel.setText(txt);
        statusLabel.setTextFill(color);
        removeMessage();

    }

    private void removeMessage() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
            statusLabel.setText("");
        }));
        timeline.play();
    }

    private String randomPassword() {
        String characters = "mAn0Bo1CpD2qE3rFs4Gt5HuI6vJ7wKx8Ly9Mz1NlO2kP3jQi4Rh5SgT6fU7eVd8WcX9bYaZ";
        String newPassword = RandomStringUtils.random(8, 0, 0, false, false, characters.toCharArray(), new SecureRandom());
        return newPassword;
    }

    private boolean isValidUserID(String userID) {
        boolean isOK;
        isOK = !(userID.replaceAll("\\s+", "").length() < 5 || userID.replaceAll("\\s+", "").length() > 10);
        return isOK;
    }

    private boolean isValidName(String name) {
        boolean isOK;
        isOK = !(name.replaceAll("\\s+", "").length() > 45 || name.replaceAll("\\s+", "").length() < 2);
        return isOK;
    }

    private boolean isValidMobile(String mobile) {
        boolean isOK;
        if (mobile.matches("[0-9]+")) {
            isOK = mobile.replaceAll("\\s+", "").length() <= 12 && mobile.replaceAll("\\s+", "").length() >= 10;
        } else {
            isOK = mobile.replaceAll("\\s+", "").equals("");
        }
        return isOK;
    }

    private void registerUser(Event event) throws Exception {
        String userID, firstName, lastName, email, mobile;
        int role;
        userID = userIDTextField.getText();
        firstName = firstNameTextField.getText();
        lastName = lastNameTextField.getText();
        email = emailTextField.getText();
        mobile = mobileTextField.getText();
        if (userRadioButton.isSelected()) {
            role = 2;
        } else if (systemAdminRadioButton.isSelected()) {
            role = 1;
        } else {
            role = -1;
        }
        if (!isValidUserID(userID)) {
            setMessageLabel("Please check your username...", Color.RED);
        } else if (!isValidName(firstName)) {
            setMessageLabel("Please check your first name...", Color.RED);
        } else if (!isValidName(lastName)) {
            setMessageLabel("Please check your last name...", Color.RED);
        } else if (!validator.isValid(email)) {
            setMessageLabel("Please check your email format...", Color.RED);
        } else if (!isValidMobile(mobile)) {
            setMessageLabel("Please check your mobile number...", Color.RED);
        } else if (!((role == 0) || (role == 1) || (role == 2))) {
            setMessageLabel("Please select user role...", Color.RED);
        } else {
            UsersDAO userDAO = new UsersDAO();
            if (userDAO.userIDExists(userID)) {
                setMessageLabel("This user ID exits...", Color.RED);
            } else {
                if (alertWindowResult("User Creation", "A new user will be created!", "Are you sure you want to create a new user?")) {
                    String password = randomPassword();
                    System.out.println("The selected role is: " + role);
                    if (userDAO.insertNewUser(userID, password, firstName, lastName, email, mobile, role, 0, UserInfo.getInstance().getUserID())) {
                        System.out.println("The password is: " + password);
                        EmailSender emailSender = new EmailSender();
                        boolean sent = emailSender.userCreated(password, email, userID, firstName);
                        if (sent) {
                            setMessageLabel("User successfully created check email...", Color.GREEN);
                            clearRegistrationForm();
                        } else {
                            setMessageLabel("User created but password did not sent, contact admin", Color.RED);
                            clearRegistrationForm();
                        }
                    } else {
                        setMessageLabel("Something goes wrong, contact admin...", Color.RED);
                    }
                } else {
                    setMessageLabel("The operation has been cancelled", Color.GREEN);
                    clearRegistrationForm();
                }
            }
        }
    }

    /* END OF REGISTRATION PART */
    //********************************************************************************************************************
    /* BEGGINING OF MANAGEMENT PART */
    private void populateUserTable() throws SQLException {
        final ObservableList<Users> data = FXCollections.observableArrayList(usersInformation);

        userIDTableColumn.setCellValueFactory(
                new PropertyValueFactory<>("userID"));
        nameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Users, String>("name"));
        emailTableColumn.setCellValueFactory(
                new PropertyValueFactory<Users, String>("email"));
        blockedStatusTableColumn.setCellValueFactory(
                new PropertyValueFactory<Users, String>("blocked"));
        roleTableColumn.setCellValueFactory(
                new PropertyValueFactory<Users, String>("role"));
        userTableView.setItems(data);
    }

    private void updateUserTable() throws SQLException {
        try {
            //Populate users array list
            usersInformation.clear();
            usersInformation = (ArrayList<Users>) userDAO.getAllSystemUsers();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateUserTable();
    }

    private void performAction() throws SQLException, IOException, AddressException, Exception {
        int action = -1;
        action = actionComboButton.getSelectionModel().getSelectedIndex();
        System.out.println("The selected action index is: " + action);
        if (userTableView.getSelectionModel().getSelectedItem() != null) {
            switch (action) {
                case 0:
                    System.out.println(userTableView.getSelectionModel().getSelectedItem().getUserID());
                    if (!userDAO.isBlocked(userTableView.getSelectionModel().getSelectedItem().getUserID())) {
                        if (alertWindowResult("Block User", "User will not be able to login again!", "Are you sure to block user?")) {
                            userDAO.blockUpdate(true, userTableView.getSelectionModel().getSelectedItem().getUserID());
                            updateUserTable();
                            actionComboButton.getSelectionModel().clearSelection();
                            setMessageLabel("User blocked successfully...", Color.GREEN);
                        } else {
                            actionComboButton.getSelectionModel().clearSelection();
                            setMessageLabel("Action cancelled...", Color.GREEN);
                        }
                    } else {
                        setMessageLabel("User is already blocked...", Color.RED);
                    }
                    break;
                case 1:
                    System.out.println(userTableView.getSelectionModel().getSelectedItem().getUserID());
                    if (userDAO.isBlocked(userTableView.getSelectionModel().getSelectedItem().getUserID())) {
                        if (alertWindowResult("Unblock User", "User will be able to login again!", "Are you sure to unblock user?")) {
                            userDAO.blockUpdate(false, userTableView.getSelectionModel().getSelectedItem().getUserID());
                            updateUserTable();
                            actionComboButton.getSelectionModel().clearSelection();
                            setMessageLabel("User unblocked successfully...", Color.GREEN);
                        } else {
                            actionComboButton.getSelectionModel().clearSelection();
                            setMessageLabel("Action cancelled...", Color.GREEN);
                        }

                    } else {
                        setMessageLabel("User is not blocked...", Color.RED);
                    }
                    break;
                case 2:
                    if (alertWindowResult("Reset Password", "User's password will have reset!", "Are you sure you want to reset user's password?")) {
                        System.out.println(userTableView.getSelectionModel().getSelectedItem().getUserID());
                        String password = randomPassword();
                        String recipient = userTableView.getSelectionModel().getSelectedItem().getEmail();
                        String userID = userTableView.getSelectionModel().getSelectedItem().getUserID();
                        System.out.println("The password is: " + password);
                        if (userDAO.changePassword(userID, password)) {
                            EmailSender emailSender = new EmailSender();
                            boolean sent = emailSender.resetPassword(password, recipient, userID);
                            if (sent) {
                                setMessageLabel("A new password sent to user email address", Color.GREEN);
                                actionComboButton.getSelectionModel().clearSelection();
                            } else {
                                setMessageLabel("Something goes wrong contact administrator", Color.RED);
                            }
                        } else {
                            setMessageLabel("Something goes wrong contact administrator", Color.RED);
                        }
                    } else {
                        setMessageLabel("The operation has been cancelled", Color.GREEN);
                        actionComboButton.getSelectionModel().clearSelection();
                    }
                    break;
                default:
                    setMessageLabel("You did not select any action...", Color.RED);
                    break;
            }
        } else {
            setMessageLabel("You did not select any row in table...", Color.RED);
        }

    }

    /* END OF MANAGEMENT PART */
    @FXML
    private void userIDKeyReleased(KeyEvent event) {
        String userID;
        userID = userIDTextField.getText();
        if (userID.replaceAll("\\s+", "").length() < 5 || userID.replaceAll("\\s+", "").length() > 10) {
            userIDTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            userIDTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void firstNameKeyReleased(KeyEvent event) {
        String firstName;
        firstName = firstNameTextField.getText();
        if (firstName.replaceAll("\\s+", "").length() > 45 || firstName.replaceAll("\\s+", "").length() < 2) {
            firstNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            firstNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void lastNameKeyReleased(KeyEvent event) {
        String lastName;
        lastName = lastNameTextField.getText();
        if (lastName.replaceAll("\\s+", "").length() > 45 || lastName.replaceAll("\\s+", "").length() < 2) {
            lastNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            lastNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void emailKeyReleased(KeyEvent event) {
        String email;
        email = emailTextField.getText();
        if (!validator.isValid(email)) {
            emailTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            emailTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void mobileKeyReleased(KeyEvent event) {
        String mobile;
        mobile = mobileTextField.getText();
        if (mobile.matches("[0-9]+")) {
            if (mobile.replaceAll("\\s+", "").length() <= 12 && mobile.replaceAll("\\s+", "").length() >= 10) {
                mobileTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
            } else {
                mobileTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
            }
        } else if (mobile.replaceAll("\\s+", "").equals("")) {
            mobileTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            mobileTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void registerEnterPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            registerUser(event);
        }
    }

    @FXML
    private void registerMouseClicked(MouseEvent event) throws Exception {
        registerUser(event);
    }

    @FXML
    private void registrationSelectionChanged(Event event) {
    }

    @FXML
    private void managementSelectionChanged(Event event) throws SQLException {
        updateUserTable();
        actionComboButton.getSelectionModel().clearSelection();
    }

    @FXML
    private void refreshMenueItemOnAction(ActionEvent event) throws SQLException {
        updateUserTable();
    }

    @FXML
    private void submitActionKeyPressed(KeyEvent event) throws SQLException, IOException, Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            performAction();
        }
    }

    @FXML
    private void submitActionMouseClicked(MouseEvent event) throws SQLException, IOException, Exception {
        performAction();
    }

}
