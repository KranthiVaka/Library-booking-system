package group4u_booking;

import database.Users;
import database.UsersDAO;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class LoginController implements Initializable {

    @FXML
    private Color x1;
    @FXML
    private ImageView logo;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView exitButton;
    @FXML
    private TextField userNameTextField;
    @FXML
    private PasswordField passTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label forgotPassLabel;

    static Effect logoEffect;

    Main main = new Main();
    Users user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void logoMouseExited(MouseEvent event) {
        logo.setCursor(Cursor.NONE);
        logo.setEffect(logoEffect);
    }

    @FXML
    private void logoMouseEntered(MouseEvent event) {
        logoEffect = logo.getEffect();
        logo.setCursor(Cursor.HAND);
        logo.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.7, 0, 0));
    }

    @FXML
    private void logoMouseClicked(MouseEvent event) throws Exception {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Library website");
        alert.setHeaderText("The library website will be open!");
        alert.setContentText("Do you want to open a new browser?");

        DialogPane dialogPane = alert.getDialogPane();
        Stage stage = (Stage) dialogPane.getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/resources/icon.png").toString()));
        dialogPane.getStylesheets().add(
                getClass().getResource("/resources/style.css").toExternalForm());
        dialogPane.getStyleClass().add("confirmationDialog");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            java.awt.Desktop.getDesktop().browse(new URI("https://www.bth.se/eng/library/"));
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    @FXML
    private void passEnterPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            if (!passTextField.getText().equals("")) {
                logIn(event);
            } else if (passTextField.getText().equals("")) {
                setMessageLabel("Please enter a valid password", Color.RED);
            }
        }
    }

    @FXML
    private void loginEnterPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            logIn(event);
        }
    }

    @FXML
    private void loginMouseExited(MouseEvent event) {
        loginButton.setCursor(Cursor.NONE);
        loginButton.setEffect(logoEffect);
    }

    @FXML
    private void loginMouseEntered(MouseEvent event) {
        logoEffect = loginButton.getEffect();
        loginButton.setCursor(Cursor.HAND);
        loginButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.7, 0, 0));
    }

    @FXML
    private void loginMouseClicked(MouseEvent event) throws Exception {
        logIn(event);
    }

    @FXML
    private void forgotPassMouseExited(MouseEvent event) {
        forgotPassLabel.setCursor(Cursor.NONE);
        forgotPassLabel.setUnderline(false);
        forgotPassLabel.setTextFill(Color.WHITE);
        forgotPassLabel.setOpacity(0.75);
    }

    @FXML
    private void forgotPassMouseEntered(MouseEvent event) {
        forgotPassLabel.setCursor(Cursor.HAND);
        forgotPassLabel.setUnderline(true);
        forgotPassLabel.setOpacity(1);
        forgotPassLabel.setTextFill(Color.RED);
    }

    @FXML
    private void forgotPassMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "ForgotPassword.fxml");
    }

    @FXML
    private void exitButtonMouseExit(MouseEvent event) {
        exitButton.setCursor(Cursor.NONE);
        exitButton.setEffect(logoEffect);
    }

    @FXML
    private void exitButtonMouseEntered(MouseEvent event) {
        logoEffect = exitButton.getEffect();
        exitButton.setCursor(Cursor.HAND);
        exitButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.7, 0, 0));
    }

    @FXML
    private void exitButtonMouseClicked(MouseEvent event) {
        //Duration.seconds(2);
        setMessageLabel("Thank you for using BTH iBooking. \nVi ses!", Color.GREEN);
        System.out.println("Thank you for using BTH iBooking");

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.exit(0);
            }
        }, 2000);
    }

    public void setMessageLabel(String txt, Color color) {
        messageLabel.setText(txt);
        messageLabel.setTextFill(color);
        removeMessage();

    }

    private void logIn(Event event) throws Exception {
        System.out.println(UserInfo.getInstance().getDatabaseIpAddress());
        UsersDAO userDAO = new UsersDAO();
        try {
            if (userNameTextField.getText().equals("") || passTextField.getText().equals("")) {
                setMessageLabel("Please enter a valid usrename and/or password", Color.RED);
            } else {
                if (!userDAO.isAuthenticated(userNameTextField.getText(), passTextField.getText())) {
                    setMessageLabel("Please enter a valid usrename and/or password", Color.RED);
                } else {
                    if (!userDAO.isBlocked(userNameTextField.getText())) {
                        user = userDAO.getUserInformation(userNameTextField.getText());
                        UserInfo.getInstance().setUserID(user.getUserID());
                        UserInfo.getInstance().setEmail(user.getEmail());
                        UserInfo.getInstance().setFirstName(user.getName());
                        if (user.getRole().equals("Supervisor")) {
                            setMessageLabel("Authenticated as supervisor: " + userNameTextField.getText(), Color.GREEN);
                            System.out.println("You are successfully authenticated as supervisor: " + userNameTextField.getText());
                            main.chngeScenes(event, "/admin/AdminMain.fxml");
                        } else if (user.getRole().equals("Admin")) {
                            setMessageLabel("Authenticated as admin: " + userNameTextField.getText(), Color.GREEN);
                            System.out.println("You are successfully authenticated as an admin: " + userNameTextField.getText());
                            main.chngeScenes(event, "/admin/AdminMain.fxml");
                        } else if (user.getRole().equals("User")) {
                            setMessageLabel("Authenticated as user: " + userNameTextField.getText(), Color.GREEN);
                            System.out.println("You are successfully authenticated as a user: " + userNameTextField.getText());
                            main.chngeScenes(event, "/user/UserMain.fxml");
                        } else {
                            setMessageLabel("You are not categorized in our system, contact admin", Color.RED);
                        }
                    } else {
                        setMessageLabel("Account is blocked, Please contact administrator", Color.RED);
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void removeMessage() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5),
                new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                messageLabel.setText("");
            }
        }));
        timeline.play();
    }
}
