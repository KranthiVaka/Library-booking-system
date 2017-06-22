/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group4u_booking;

import database.UsersDAO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.EmailValidator;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class ForgotPasswordController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Color x1;
    @FXML
    private ImageView logo;
    @FXML
    private TextField userNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button resetButton;
    @FXML
    private Label loginLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private ImageView backButton;

    static Effect logoEffect;

    Main main = new Main();

    EmailValidator validator = EmailValidator.getInstance();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GifDecoder d = new GifDecoder();
        d.read("/resources/busy.gif");
        int n = d.getFrameCount();
        for (int i = 0; i < n; i++) {
            BufferedImage frame = d.getFrame(i);
//            int t = d.getDelay(i);
            System.out.println(i);
        }
        // TODO
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
    private void logoMouseClicked(MouseEvent event) throws URISyntaxException, IOException {
        java.awt.Desktop.getDesktop().browse(new URI("https://www.bth.se/eng/library/"));
    }

    @FXML
    private void emailEnterPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            if (!emailTextField.getText().equals("")) {
                resetPassword(event);
            } else if (emailTextField.getText().equals("")) {
                setMessageLabel("Please enter a valid email", Color.RED);
            }
        }
    }

    @FXML
    private void resetKeyPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            resetPassword(event);
        }
    }

    @FXML
    private void resetMouseExited(MouseEvent event) {
        resetButton.setCursor(Cursor.NONE);
        resetButton.setEffect(logoEffect);
    }

    @FXML
    private void resetMouseEntered(MouseEvent event) {
        logoEffect = resetButton.getEffect();
        resetButton.setCursor(Cursor.HAND);
        resetButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.7, 0, 0));
    }

    @FXML
    private void resetMouseClicked(MouseEvent event) throws Exception {
        resetPassword(event);
    }

    @FXML
    private void loginMouseExited(MouseEvent event) {
        loginLabel.setCursor(Cursor.NONE);
        loginLabel.setUnderline(false);
        loginLabel.setTextFill(Color.WHITE);
        loginLabel.setOpacity(0.75);
    }

    @FXML
    private void loginMouseEntered(MouseEvent event) {
        loginLabel.setCursor(Cursor.HAND);
        loginLabel.setUnderline(true);
        loginLabel.setOpacity(1);
        loginLabel.setTextFill(Color.RED);
    }

    @FXML
    private void loginMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "Login.fxml");
    }

    @FXML
    private void backButtonMouseExit(MouseEvent event) {
        backButton.setCursor(Cursor.NONE);
        backButton.setEffect(logoEffect);
    }

    @FXML
    private void backButtonMouseEntered(MouseEvent event) {
        logoEffect = backButton.getEffect();
        backButton.setCursor(Cursor.HAND);
        backButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, Color.BLACK, 10, 0.7, 0, 0));
    }

    @FXML
    private void backButtonMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "Login.fxml");
    }

    @FXML
    private void anchorPaneEscPressed(KeyEvent event) throws Exception {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ESCAPE) {
            main.chngeScenes(event, "Login.fxml");
        }
    }

    public void setMessageLabel(String txt, Color color) {
        messageLabel.setText(txt);
        messageLabel.setTextFill(color);
        removeMessage();

    }

    private void removeMessage() {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
            messageLabel.setText("");
        }));
        timeline.play();
    }

    private String randomPassword() {
        String characters = "mAn0Bo1CpD2qE3rFs4Gt5HuI6vJ7wKx8Ly9Mz1NlO2kP3jQi4Rh5SgT6fU7eVd8WcX9bYaZ";
        String newPassword = RandomStringUtils.random(8, 0, 0, false, false, characters.toCharArray(), new SecureRandom());
        return newPassword;
    }

    private void resetPassword(Event event) throws Exception {
        UsersDAO userDAO = new UsersDAO();
        try {
            if (userNameTextField.getText().equals("") || emailTextField.getText().equals("")) {
                setMessageLabel("Please enter a valid usrename and/or password", Color.RED);
            } else if (!validator.isValid(emailTextField.getText())) {
                setMessageLabel("Please check email format", Color.RED);
            } else {
                if (userDAO.userIdAndEmailMaches(userNameTextField.getText(), emailTextField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Reset Password");
                    alert.setHeaderText("Your password will have reset!");
                    alert.setContentText("Are you sure you want to change the password?");

                    DialogPane dialogPane = alert.getDialogPane();
                    Stage stage = (Stage) dialogPane.getScene().getWindow();
                    stage.getIcons().add(new Image(this.getClass().getResource("/resources/icon.png").toString()));
                    dialogPane.getStylesheets().add(
                            getClass().getResource("/resources/style.css").toExternalForm());
                    dialogPane.getStyleClass().add("confirmationDialog");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        
                        String password = randomPassword();
                        if (userDAO.changePassword(userNameTextField.getText(), password)) {
                            System.out.println("The password is: " + password);
                            EmailSender emailSender = new EmailSender();
                            boolean sent = emailSender.resetPassword(password, emailTextField.getText(), userNameTextField.getText());
                            if(sent){
                                setMessageLabel("A new password sent to your email address", Color.GREEN);
                            }else{
                                setMessageLabel("Something goes wrong contact administrator", Color.RED);
                            }
                        } else {
                            setMessageLabel("Something goes wrong contact administrator", Color.RED);
                        }
                    } else {
                        setMessageLabel("The operation has been cancelled", Color.GREEN);
                    }
                } else {
                    setMessageLabel("Please check combination and/or contact administrator", Color.RED);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
