/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import group4u_booking.Main;
import group4u_booking.UserInfo;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class BookingHistoryController implements Initializable {
    Main main = new Main();
    
    @FXML
    private Button bookingHistoryButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label statusLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookingHistoryButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");
        // TODO
    }    

    @FXML
    private void profileOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/UserMain.fxml");
    }

    @FXML
    private void bookARoomMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/BookARoom.fxml");
    }

    @FXML
    private void manageBookingOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/ManageBooking.fxml");
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }
    
}
