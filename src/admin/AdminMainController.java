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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import org.apache.commons.validator.EmailValidator;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class AdminMainController implements Initializable {

    Main main = new Main();
    EmailValidator validator = EmailValidator.getInstance();
    Users user;
    UsersDAO userDAO = new UsersDAO();
    private final Color redColor = Color.rgb(204, 0, 0);
    private final Color greenColor = Color.rgb(81, 170, 0);

    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Button profileButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Color x3;
    @FXML
    private Font x4;
    @FXML
    private Label userIDLabel;
    @FXML
    private Label fullNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label mobileLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private Color x21;
    @FXML
    private Font x41;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private Color x211;
    @FXML
    private TextField newPasswordAgainTextField;
    @FXML
    private Color x2111;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profileButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");
        try {
            user = userDAO.getUserInformation(UserInfo.getInstance().getUserID());
        } catch (IOException | SQLException ex) {
            Logger.getLogger(AdminMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userIDLabel.setText(user.getUserID());
        fullNameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        mobileLabel.setText(user.getTel());
        roleLabel.setText(user.getRole());
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }

    @FXML
    private void userManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/UserManagement.fxml");
    }

    @FXML
    private void roomManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/RoomManagement.fxml");
    }

    @FXML
    private void bookingManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/BookingManagement.fxml");
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

    private void clearResetForm() {
        oldPasswordTextField.clear();
        newPasswordTextField.clear();
        newPasswordAgainTextField.clear();
    }

    /* Profile Code Start*/
    private void changeEmail() throws SQLException, IOException {
        TextInputDialog dialog = new TextInputDialog(user.getEmail());
        dialog.setTitle("Email Change");
        dialog.setHeaderText("You can change email address!");
        dialog.setContentText("Please enter your new email:");
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/icon.png").toString()));
        dialog.setGraphic(new ImageView(this.getClass().getResource("/resources/icon.png").toString()));
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your name: " + result.get());
            if (validator.isValid(result.get())) {
                if (userDAO.changeEmail(user.getUserID(), result.get())) {
                    user.setEmail(result.get());
                    emailLabel.setText(user.getEmail());
                    setMessageLabel("Email successfully updated...", Color.GREEN);
                } else {
                    setMessageLabel("Something goes wrong, contact admin", Color.RED);
                }
            } else {
                setMessageLabel("New email's format is not valid", Color.RED);
            }

        } else {
            setMessageLabel("Action cancelled...", Color.GREEN);
        }
    }

    /* Profile Code End*/
 /* Password Code Start*/
    private void updatePassword() throws SQLException, IOException {
        String oldPassword = oldPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        String newPasswordAgain = newPasswordAgainTextField.getText();
        if (newPassword.equals(newPasswordAgain) && newPassword.length() > 5) {
            if (userDAO.isAuthenticated(user.getUserID(), oldPassword)) {
                if (alertWindowResult("Update Password", "Your password will be updated!", "Are you sure to update your password?")) {
                    if (userDAO.changePassword(user.getUserID(), newPassword)) {
                        setMessageLabel("Your password successfully updated!", Color.GREEN);
                        clearResetForm();
                    } else {
                        setMessageLabel("Something goes wrong, contact admin", Color.RED);
                    }
                } else {
                    setMessageLabel("Action cancelled!", Color.GREEN);
                    clearResetForm();
                }
            } else {
                setMessageLabel("Your old password is not correct!", Color.RED);
                oldPasswordTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
            }
        } else {
            setMessageLabel("New password fields does not match!", Color.RED);
            newPasswordAgainTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }
    }

    /* Password Code End*/
    @FXML
    private void changeEmailEnterPressed(KeyEvent event) throws SQLException, IOException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            changeEmail();
        }

    }

    @FXML
    private void changeEmailMouseClicked(MouseEvent event) throws SQLException, IOException {
        changeEmail();
    }

    @FXML
    private void resetPasswordEnterPressed(KeyEvent event) throws SQLException, IOException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            updatePassword();
        }
    }

    @FXML
    private void resetPasswordMouseClicked(MouseEvent event) throws SQLException, IOException {
        updatePassword();
    }

    @FXML
    private void newPasswordKeyReleased(KeyEvent event) {
        if (newPasswordTextField.getText().length() < 6) {
            newPasswordTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            newPasswordTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void newPasswordAgainKeyReleased(KeyEvent event) {
        if (!newPasswordTextField.getText().equals(newPasswordAgainTextField.getText())) {
            newPasswordAgainTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        } else {
            newPasswordAgainTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void oldPasswordKeyReleased(KeyEvent event) {
        oldPasswordTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
    }

}
