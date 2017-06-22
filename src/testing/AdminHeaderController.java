/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import group4u_booking.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class AdminHeaderController implements Initializable {

    @FXML
    private ImageView logOutButton;
    @FXML
    private AnchorPane profileAnchorPane;
    @FXML
    private AnchorPane registrationAnchorPane;
    @FXML
    private Label messageLabel;

    
    Main main = new Main();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }

    @FXML
    private void profileTabSelectionChanged(Event event) throws IOException {
        profileAnchorPane.getChildren().clear();
//        boolean setAll = profileAnchorPane.getChildren().setAll(FXMLLoader.load(getClass().getResource("Login.fxml")));
//        profileAnchorPane.getChildren().setAll(FXMLLoader.load(getClass().getResource("Login.fxml")));
//        profileAnchorPane = (AnchorPane) FXMLLoader.load("Profile.fxml");
    }

    @FXML
    private void registrationTabSelectionChanged(Event event) {
    }
    
}
