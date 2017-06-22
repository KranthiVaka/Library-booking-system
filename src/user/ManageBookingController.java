/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import database.BookingDAO;
import database.Bookings;
import group4u_booking.Main;
import group4u_booking.UserInfo;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class ManageBookingController implements Initializable {
    Main main = new Main();
    private boolean disableTab = false;
    private ArrayList<Bookings> bookingsInformation = new ArrayList<>();
    BookingDAO BookingDAO = new BookingDAO();
    
    
    @FXML
    private Button manageBookingButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TabPane bookingTabPane;
    @FXML
    private TableView<Bookings> bookingTableView;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private MenuItem moreInformationMenuItem;
    @FXML
    private TableColumn<Bookings, String> bookingNumberTableColumn;
    @FXML
    private TableColumn<Bookings, String> roomNameTableColumn;
    @FXML
    private TableColumn<Bookings, String> dateTableColumn;
    @FXML
    private TableColumn<Bookings, String> startTimeTableColumn;
    @FXML
    private TableColumn<Bookings, String> endTimeTableColumn;
    @FXML
    private Tab moreInformationTab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manageBookingButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");
        try {
            // TODO
            updateRoomTable();
        } catch (SQLException ex) {
            Logger.getLogger(ManageBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void profileOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/UserMain.fxml");
    }

    @FXML
    private void bookARoomOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/BookARoom.fxml");
    }

    @FXML
    private void bookingHistoryOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/BookingHistory.fxml");
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
    
    private void populateBookingTable() throws SQLException {
        final ObservableList<Bookings> data = FXCollections.observableArrayList(bookingsInformation);

        bookingNumberTableColumn.setCellValueFactory(
                new PropertyValueFactory<Bookings, String>("bookingNo"));
        roomNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Bookings, String>("roomName"));
        dateTableColumn.setCellValueFactory(
                new PropertyValueFactory<Bookings, String>("date"));
        startTimeTableColumn.setCellValueFactory(
                new PropertyValueFactory<Bookings, String>("startTime"));
        endTimeTableColumn.setCellValueFactory(
                new PropertyValueFactory<Bookings, String>("endTime"));
        
        bookingTableView.setItems(data);
    }
    
    private void updateRoomTable() throws SQLException {
        try {
            //Populate rooms array list
            bookingsInformation.clear();
            bookingsInformation = (ArrayList<Bookings>) BookingDAO.getAllFutureBookings(UserInfo.getInstance().getUserID());
        } catch (SQLException | IOException ex) {
            Logger.getLogger(ManageBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateBookingTable();
    }

    @FXML
    private void deleteMenueItemOnAction(ActionEvent event) throws SQLException, IOException {
        if(bookingTableView.getSelectionModel().getSelectedItem() != null){
            if (alertWindowResult("Delete A Record", "Selected record will be deleted!", "Are you sure to delete the booking record?")) {
                if(BookingDAO.deleteARecord(bookingTableView.getSelectionModel().getSelectedItem().getBookingNo())){
                    updateRoomTable();
                    setMessageLabel("Record removed successfully...", Color.GREEN);
                }else{
                    setMessageLabel("Something goes wrong contact admin...", Color.RED);
                }
            }else{
                setMessageLabel("Action cancelled...", Color.GREEN);
            }
        }else{
            setMessageLabel("You must right click on a record...", Color.RED);
        }
    }

    @FXML
    private void moreInformationMenueItemOnAction(ActionEvent event) {
        moreInformationTab.disableProperty().set(false);
        bookingTabPane.getSelectionModel().select(moreInformationTab);
    }

    @FXML
    private void ManageBookingOnSelectionChanged(Event event) {
    }

    @FXML
    private void moreInformationTabOnSelectionChanged(Event event) {
        if (!disableTab) {
            disableTab = true;
        } else {
            disableTab = false;
            moreInformationTab.disableProperty().set(true);
        }
    }

    @FXML
    private void moreInformationTabOnClose(Event event) {
        moreInformationTab.disableProperty().set(true);
    }
    
}
