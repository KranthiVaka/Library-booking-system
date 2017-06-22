/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import group4u_booking.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class Test3Controller implements Initializable {

    @FXML
    private ToolBar menuToolBar;
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
        System.out.println("Profile start ...");
        // TODO
    }    

    @FXML
    private void userManagementToolbarMouseClickd(MouseEvent event) throws Exception {
        System.out.println("Go to User Management ...");
        main.chngeScenes(event, "/admin/Test4.fxml");
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }


    
}
