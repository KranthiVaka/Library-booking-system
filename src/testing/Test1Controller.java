/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import group4u_booking.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class Test1Controller implements Initializable {

    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label labelStatus;

    
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
    private void profileOnAction(ActionEvent event) {
        System.out.println("Profile printing.../nTest 1");
    }

    @FXML
    private void userManagementOnAction(ActionEvent event) throws Exception {
        System.out.println("User management printing.../nTest 1");
        main.chngeScenes(event, "/admin/Test2.fxml");
    }

    @FXML
    private void roomManagementOnAction(ActionEvent event) {
        System.out.println("Room management printing.../nTest 1");
    }

    @FXML
    private void buttonMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/Test2.fxml");
    }
    
}
