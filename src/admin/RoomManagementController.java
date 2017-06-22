/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

import database.Rooms;
import database.RoomsDAO;
import database.Users;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
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
import javafx.util.converter.NumberStringConverter;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class RoomManagementController implements Initializable {

    Main main = new Main();

    private final Color redColor = Color.rgb(204, 0, 0);
    private final Color greenColor = Color.rgb(81, 170, 0);
    private final Color whiteColor = Color.rgb(255, 248, 220);
    private boolean disableTab = false;
    private int selectedRoomID;
    private String selectedRoomName;

    private ArrayList<Rooms> roomsInformation = new ArrayList<>();
    RoomsDAO roomsDAO = new RoomsDAO();

    @FXML
    private Button roomButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Button registerButton;
    @FXML
    private Font x4;
    @FXML
    private TextField roomNameTextField;
    @FXML
    private Color x2;
    @FXML
    private TextField sitsTextField;
    @FXML
    private CheckBox boardCheckBox;
    @FXML
    private CheckBox tvCheckBox;
    @FXML
    private CheckBox projectorCheckBox;
    @FXML
    private CheckBox availableCheckBox;
    @FXML
    private MenuItem updateMenuItem;
    @FXML
    private TableView<Rooms> roomTableView;
    @FXML
    private TableColumn<Rooms, String> roomNameTableColumn;
    @FXML
    private TableColumn<Rooms, Integer> sitsTableColumn;
    @FXML
    private TableColumn<Rooms, String> boardTableColumn;
    @FXML
    private TableColumn<Rooms, String> tvTableColumn;
    @FXML
    private TableColumn<Rooms, String> projectorTableColumn;
    @FXML
    private TableColumn<Rooms, String> availableTableColumn;
    @FXML
    private Tab updateRoomTab;
    @FXML
    private Font x41;
    @FXML
    private TextField updateRoomNameTextField;
    @FXML
    private TextField updateSitsTextField;
    @FXML
    private CheckBox updateBoardCheckBox;
    @FXML
    private CheckBox updateTvCheckBox;
    @FXML
    private CheckBox updateProjectorCheckBox;
    @FXML
    private CheckBox updateAvailableCheckBox;
    @FXML
    private TabPane roomTabPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roomButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(RoomManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");
        // TODO
//        sitsTextField.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
        roomNameTextField.setTooltip(new Tooltip("Room name could only contain letters and digits"));
        sitsTextField.setTooltip(new Tooltip("Number of room's sits could be between 1 and 150"));
        updateRoomNameTextField.setTooltip(new Tooltip("Room name could only contain letters and digits"));
        updateSitsTextField.setTooltip(new Tooltip("Number of room's sits could be between 1 and 150"));
    }

    @FXML
    private void profileOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/AdminMain.fxml");
    }

    @FXML
    private void userManagementOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/admin/UserManagement.fxml");
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
        roomNameTextField.clear();
        sitsTextField.clear();
        boardCheckBox.setSelected(false);
        tvCheckBox.setSelected(false);
        projectorCheckBox.setSelected(false);
        availableCheckBox.setSelected(false);
        roomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        sitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        availableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
    }

    private void clearUpdateForm() {
        updateRoomNameTextField.clear();
        updateSitsTextField.clear();
        updateBoardCheckBox.setSelected(false);
        updateTvCheckBox.setSelected(false);
        updateProjectorCheckBox.setSelected(false);
        updateAvailableCheckBox.setSelected(false);
        updateRoomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        updateSitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        updateBoardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        updateTvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        updateProjectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        updateAvailableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
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

    private boolean validateRoomName(String roomName) {
        boolean isOK;
        isOK = (roomName.replaceAll("\\s+", "").matches("[\\p{L}0-9]+") || roomName.replaceAll("\\s+", "").matches("[0-9]+")) && roomName.replaceAll("\\s+", "").length() >= 1 && roomName.replaceAll("\\s+", "").length() <= 15;
        return isOK;
    }

    private boolean validateSites() {
        boolean isOK;
        if (sitsTextField.getText().matches("\\d*") && !sitsTextField.getText().replaceAll("\\s+", "").equals("")) {
            return Integer.parseInt(sitsTextField.getText().replaceAll("\\s+", "")) >= 1 && Integer.parseInt(sitsTextField.getText().replaceAll("\\s+", "")) <= 150;
        } else {
            isOK = false;
        }
        return isOK;
    }

    private boolean validateSitesUpdate() {
        boolean isOK;
        if (updateSitsTextField.getText().matches("\\d*") && !updateSitsTextField.getText().replaceAll("\\s+", "").equals("")) {
            return Integer.parseInt(updateSitsTextField.getText().replaceAll("\\s+", "")) >= 1 && Integer.parseInt(updateSitsTextField.getText().replaceAll("\\s+", "")) <= 150;
        } else {
            isOK = false;
        }
        return isOK;
    }

    private boolean validateRoomNameUpdate(String roomName) {
        boolean isOK;
        isOK = (roomName.replaceAll("\\s+", "").matches("[\\p{L}0-9]+") || roomName.replaceAll("\\s+", "").matches("[0-9]+")) && roomName.replaceAll("\\s+", "").length() >= 1 && roomName.replaceAll("\\s+", "").length() <= 15;
        return isOK;
    }

    /*
    REGISTERING NEW ROOM
     */
    private void registerRoom(Event event) throws SQLException, IOException {
        if (validateRoomName(roomNameTextField.getText())) {
            if (validateSites()) {
                if (!roomsDAO.roomNameExists(roomNameTextField.getText())) {
                    if (alertWindowResult("Register New Room", "A new room will be added to system!", "Are you sure to add the new room?")) {
                        if (roomsDAO.insertNewRoom(roomNameTextField.getText(), Integer.parseInt(sitsTextField.getText()), boardCheckBox.isSelected(), tvCheckBox.isSelected(), projectorCheckBox.isSelected(), availableCheckBox.isSelected())) {
                            setMessageLabel("The new room has registered successfully...", Color.GREEN);
                            clearRegistrationForm();
                        } else {
                            setMessageLabel("Something goes wrong contact administrator...", Color.RED);
                        }
                    } else {
                        setMessageLabel("The registration is cancelled...", Color.RED);
                        clearRegistrationForm();
                    }

                } else {
                    setMessageLabel("There is a similar room name in system please change it...", Color.RED);
                }
            } else {
                setMessageLabel("Sits field must be between 1 and 150...", Color.RED);
            }
        } else {
            setMessageLabel("Check room name format...", Color.RED);
        }
    }

    /*
    SHOWING REGISTERED ROOMS
     */
    private void populateRoomTable() throws SQLException {
        final ObservableList<Rooms> data = FXCollections.observableArrayList(roomsInformation);

        roomNameTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, String>("roomName"));
        sitsTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, Integer>("sits"));
        boardTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, String>("board"));
        tvTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, String>("tv"));
        projectorTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, String>("projector"));
        availableTableColumn.setCellValueFactory(
                new PropertyValueFactory<Rooms, String>("available"));
        roomTableView.setItems(data);
    }

    private void updateRoomTable() throws SQLException {
        try {
            //Populate rooms array list
            roomsInformation.clear();
            roomsInformation = (ArrayList<Rooms>) roomsDAO.getAllRooms();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(RoomManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        populateRoomTable();
    }

    /*
        ACTIVE UPDATE TAB
     */
    private void activeUpdateTab() {
        if (roomTableView.getSelectionModel().getSelectedItem() != null) {
            selectedRoomID = roomTableView.getSelectionModel().getSelectedItem().getRoomID();
            selectedRoomName = roomTableView.getSelectionModel().getSelectedItem().getRoomName();
            updateRoomNameTextField.setText(roomTableView.getSelectionModel().getSelectedItem().getRoomName());
            updateSitsTextField.setText(String.valueOf(roomTableView.getSelectionModel().getSelectedItem().getSits()));
            if (roomTableView.getSelectionModel().getSelectedItem().getBoard().equals("Yes")) {
                updateBoardCheckBox.setSelected(true);
            }
            if (roomTableView.getSelectionModel().getSelectedItem().getTv().equals("Yes")) {
                updateTvCheckBox.setSelected(true);
            }
            if (roomTableView.getSelectionModel().getSelectedItem().getProjector().equals("Yes")) {
                updateProjectorCheckBox.setSelected(true);
            }
            if (roomTableView.getSelectionModel().getSelectedItem().getAvailable().equals("Yes")) {
                updateAvailableCheckBox.setSelected(true);
            }
            updateRoomTab.disableProperty().set(false);
            roomTabPane.getSelectionModel().select(updateRoomTab);
        } else {
            setMessageLabel("You did not choose any room...", Color.RED);
        }
    }

    /*
        UPDATE ROOM
     */
    private void updateRoom(Event event) throws SQLException, IOException {
        if (validateRoomNameUpdate(updateRoomNameTextField.getText())) {
            if (validateSitesUpdate()) {
                if (updateRoomNameTextField.getText().equals(selectedRoomName)) {
                    if (alertWindowResult("Update Room Features", "Room features will be updated!", "Are you sure to update the room?")) {
                        if (roomsDAO.updateRoomFeatures(selectedRoomID, updateRoomNameTextField.getText(), Integer.parseInt(updateSitsTextField.getText()), updateBoardCheckBox.isSelected(), updateTvCheckBox.isSelected(), updateProjectorCheckBox.isSelected(), updateAvailableCheckBox.isSelected())) {
                            setMessageLabel("The has updated successfully...", Color.GREEN);
                        } else {
                            setMessageLabel("Something goes wrong contact administrator...", Color.RED);
                        }
                    } else {
                        setMessageLabel("The registration is cancelled...", Color.RED);
                    }
                } else {
                    if (!roomsDAO.roomNameExists(updateRoomNameTextField.getText())) {
                        if (alertWindowResult("Update Room Features", "Room features will be updated!", "Are you sure to update the room?")) {
                            if (roomsDAO.updateRoomFeatures(selectedRoomID, updateRoomNameTextField.getText(), Integer.parseInt(updateSitsTextField.getText()), updateBoardCheckBox.isSelected(), updateTvCheckBox.isSelected(), updateProjectorCheckBox.isSelected(), updateAvailableCheckBox.isSelected())) {
                                setMessageLabel("The has updated successfully...", Color.GREEN);
                            } else {
                                setMessageLabel("Something goes wrong contact administrator...", Color.RED);
                            }
                        } else {
                            setMessageLabel("The registration is cancelled...", Color.RED);
                        }
                    } else {
                        setMessageLabel("There is a similar room name in system please change it...", Color.RED);
                    }
                }

            } else {
                setMessageLabel("Sits field must be between 1 and 150...", Color.RED);
            }
        } else {
            setMessageLabel("Check room name format...", Color.RED);
        }
    }

    @FXML
    private void registerEnterPressed(KeyEvent event) throws SQLException, IOException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            registerRoom(event);
        }
    }

    @FXML
    private void registerMouseClicked(MouseEvent event) throws SQLException, IOException {
        registerRoom(event);
    }

    @FXML
    private void roomNameKeyReleased(KeyEvent event) {
        if (validateRoomName(roomNameTextField.getText())) {
            roomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            roomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }

    }

    @FXML
    private void sitsKeyReleased(KeyEvent event) {
        if (validateSites()) {
            sitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            sitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }

    }

    @FXML
    private void roomNameKeyReleasedUpdate(KeyEvent event) {
        if (validateRoomNameUpdate(updateRoomNameTextField.getText())) {
            updateRoomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateRoomNameTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void sitsKeyReleasedUpdate(KeyEvent event) {
        if (validateSitesUpdate()) {
            updateSitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateSitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void boardCheckBoxMouseReleased(MouseEvent event) {
        if (boardCheckBox.isSelected()) {
            boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }

        if (updateBoardCheckBox.isSelected()) {
            updateBoardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateBoardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void tvCheckBoxMouseReleased(MouseEvent event) {
        if (tvCheckBox.isSelected()) {
            tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }

        if (updateTvCheckBox.isSelected()) {
            updateTvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateTvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void projectorCheckBoxMouseReleased(MouseEvent event) {
        if (projectorCheckBox.isSelected()) {
            projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }

        if (updateProjectorCheckBox.isSelected()) {
            updateProjectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateProjectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void availableCheckBoxMouseReleased(MouseEvent event) {
        if (availableCheckBox.isSelected()) {
            availableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            availableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }

        if (updateAvailableCheckBox.isSelected()) {
            updateAvailableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            updateAvailableCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void updateMenueItemOnAction(ActionEvent event) {
        activeUpdateTab();
    }

    @FXML
    private void managementSelectionChanged(Event event) throws SQLException {
        updateRoomTable();
    }

    @FXML
    private void updateRoomEnterPressed(KeyEvent event) throws SQLException, IOException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            updateRoom(event);
        }
    }

    @FXML
    private void updateRoomMouseClicked(MouseEvent event) throws SQLException, IOException {
        updateRoom(event);
    }

    @FXML
    private void updateRoomOnClose(Event event) {
        updateRoomTab.disableProperty().set(true);
    }

    @FXML
    private void updateRoomTabOnSelectionChanged(Event event) {
        if (!disableTab) {
            disableTab = true;
        } else {
            disableTab = false;
            updateRoomTab.disableProperty().set(true);
            clearUpdateForm();
        }
    }

}
