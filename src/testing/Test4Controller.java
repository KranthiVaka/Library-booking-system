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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class Test4Controller implements Initializable {

    @FXML
    private ToolBar menuToolBar;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label labelStatus;

    
    Main main = new Main();
    @FXML
    private Button userManagementButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //userManagementButton.requestFocus();
//        menuToolBar.setFocusTraversable(true);
//        userManagementButton.setFocusTraversable(true);
//        userManagementButton.requestFocus();
        userManagementButton.setStyle("-fx-text-fill: orange;");
        
        System.out.println("User Management start ...");
    }    

    @FXML
    private void profileToolbarMouseClicked(MouseEvent event) throws Exception {
        System.out.println("Go to profile ...");
        main.chngeScenes(event, "/admin/Test3.fxml");
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }
    
}
