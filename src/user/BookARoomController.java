/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import database.BookingDAO;
import database.Rooms;
import database.RoomsDAO;
import database.Times;
import database.TimesDAO;
import group4u_booking.Main;
import group4u_booking.UserInfo;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
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
import javafx.util.Callback;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Medis
 */
public class BookARoomController implements Initializable {

    Main main = new Main();

    private final Color redColor = Color.rgb(204, 0, 0);
    private final Color greenColor = Color.rgb(81, 170, 0);
    private final Color whiteColor = Color.rgb(255, 248, 220);
    private boolean disableTab = false;
    private int selectedRoomID;
    private String selectedRoomName;

    private ArrayList<Rooms> roomsInformation = new ArrayList<>();
    private boolean[] startTimeInformationArray;
    private boolean[] endTimeInformationArray;
    private ArrayList<Times> startTimeInformation = new ArrayList<>();
    private ArrayList<Times> endTimeInformation = new ArrayList<>();
    private ObservableList<Times> roomStartTime;
    private ObservableList<Times> roomEndTime;
    /*
    TESTING TO DISABLE SOME ITEMS IN COMBOBOX
     */
//    private ArrayList<String> timeNamesInformation = new ArrayList<>();
    RoomsDAO roomsDAO = new RoomsDAO();
    TimesDAO timesDAO = new TimesDAO();
    BookingDAO bookingDAO = new BookingDAO();

    @FXML
    private Button bookARoomButton;
    @FXML
    private ImageView logOutButton;
    @FXML
    private Label userLoggedInDetailsLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private TabPane bookARoomTabPane;
    @FXML
    private Font x4;
    @FXML
    private TextField sitsTextField;
    @FXML
    private CheckBox boardCheckBox;
    @FXML
    private CheckBox tvCheckBox;
    @FXML
    private CheckBox projectorCheckBox;
    @FXML
    private Tab bookARoomTab;
    @FXML
    private DatePicker chooseDateDatePicker;
    @FXML
    private ComboBox<Rooms> chooseRoomComboBox;
    @FXML
    private ComboBox<Times> startTimeComboBox;
    @FXML
    private ComboBox<Times> endTimeComboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookARoomButton.setStyle("-fx-text-fill: orange;");
        try {
            userLoggedInDetailsLabel.setText("Logged in as: " + UserInfo.getInstance().getUserID());
        } catch (IOException ex) {
            Logger.getLogger(UserMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        userLoggedInDetailsLabel.setStyle("-fx-text-fill: white;");
        // TODO
        chooseDateDatePicker.setValue(LocalDate.now());
        chooseDateDatePicker.setShowWeekNumbers(true);
        final Callback<DatePicker, DateCell> dayCellFactory
                = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(
                                LocalDate.now())
                                || item.isAfter(LocalDate.now().plusYears(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                };
            }
        };
        chooseDateDatePicker.setDayCellFactory(dayCellFactory);
        /*
        TESTING DISABLING ITEMS IN COMBOBOX
         */
//        final ObservableList<String> data1 = FXCollections.observableArrayList(timeNamesInformation);
//        startTimeComboBox.setDisabledItems("09:30");

    }

    @FXML
    private void profileOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/UserMain.fxml");
    }

    @FXML
    private void manageBookingOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/ManageBooking.fxml");
    }

    @FXML
    private void bookingHistoryOnMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/user/BookingHistory.fxml");
    }

    @FXML
    private void logOutMouseClicked(MouseEvent event) throws Exception {
        main.chngeScenes(event, "/group4u_booking/Login.fxml");
    }

    private void clearAllComboboxes() {
        clearStartTimeComboBox();
        clearEndTimeComboBox();
        clearRoomNameComboBox();
    }

    private void clearStartTimeComboBox() {
//        startTimeComboBox.getSelectionModel().select(-1);
        startTimeComboBox.getItems().clear();
        startTimeComboBox.getItems().add(null);
    }

    public void clearEndTimeComboBox() {
//        endTimeComboBox.getSelectionModel().select(-1);
        endTimeComboBox.getItems().clear();
        endTimeComboBox.getItems().add(null);
    }

    public void clearRoomNameComboBox() {
//        chooseRoomComboBox.getSelectionModel().select(-1);
        chooseRoomComboBox.getItems().clear();
        chooseRoomComboBox.getItems().add(null);
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

    private void clearSearchForm() {
        sitsTextField.clear();
        boardCheckBox.setSelected(false);
        tvCheckBox.setSelected(false);
        projectorCheckBox.setSelected(false);
        sitsTextField.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, redColor, 10, 0.7, 0, 0));
        boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
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

    private boolean validateSites() {
        boolean isOK;
        if (sitsTextField.getText().matches("\\d*") && !sitsTextField.getText().replaceAll("\\s+", "").equals("")) {
            return Integer.parseInt(sitsTextField.getText().replaceAll("\\s+", "")) >= 1 && Integer.parseInt(sitsTextField.getText().replaceAll("\\s+", "")) <= 150;
        } else {
            isOK = false;
        }
        return isOK;
    }

    /*
    SEARCH ROOM SECTION
     */
    private void searchRooms(Event event) throws SQLException, IOException {
        if (validateSites()) {
            roomsInformation.clear();
            roomsInformation = (ArrayList<Rooms>) roomsDAO.searchAvailableRoom(Integer.parseInt(sitsTextField.getText()), boardCheckBox.isSelected(), tvCheckBox.isSelected(), projectorCheckBox.isSelected());
            if (!roomsInformation.isEmpty()) {
                populateRoomComboBox();
                bookARoomTab.disableProperty().set(false);
                bookARoomTabPane.getSelectionModel().select(bookARoomTab);
            } else {
                setMessageLabel("No room is available, refine your search option", redColor);
            }
        } else {
            setMessageLabel("Number of sits must be between 1 and 150", redColor);
        }
    }

    private void populateRoomComboBox() {
        final ObservableList<Rooms> roomNames = FXCollections.observableArrayList(roomsInformation);
        chooseRoomComboBox.setItems(roomNames);
    }

    /*
    RECORD SAVING
     */
    private void saveRecord(Event event) throws IOException, SQLException {
        String selectedDate;
        int roomID, startTime, endTime;
        if (chooseDateDatePicker.getValue() != null) {
            LocalDate date = chooseDateDatePicker.getValue();
            selectedDate = date.toString();
            if (chooseRoomComboBox.getSelectionModel().getSelectedItem() != null) {
                roomID = chooseRoomComboBox.getSelectionModel().getSelectedItem().getRoomID();
                if (startTimeComboBox.getSelectionModel().getSelectedItem() != null) {
                    startTime = startTimeComboBox.getSelectionModel().getSelectedItem().getTimeID();
                    if (endTimeComboBox.getSelectionModel().getSelectedItem() != null) {
                        endTime = endTimeComboBox.getSelectionModel().getSelectedItem().getTimeID();
                        if (alertWindowResult("Save A Record", "Booking will be saved!", "Are you sure to book the room?")) {
                            if (bookingDAO.insertNewBookingRecord(roomID, UserInfo.getInstance().getUserID(), selectedDate, startTime, endTime)) {
                                System.out.println(selectedDate + " " + roomID + " " + startTime + " " + endTime);
                                chooseRoomComboBox.getSelectionModel().select(-1);
                                clearSearchForm();
                                setMessageLabel("Record saved successfully...", greenColor);
                            } else {
                                setMessageLabel("Something goes wrong, contact admin...", redColor);
                            }
                        } else {
                            setMessageLabel("Action cancelled successfully...", greenColor);
                        }

                    } else {
                        setMessageLabel("Please choose your end time...", redColor);
                    }
                } else {
                    setMessageLabel("Please choose your start time...", redColor);
                }
            } else {
                setMessageLabel("Please choose a room...", redColor);
            }
        } else {
            setMessageLabel("Please choose a date...", redColor);
        }
    }

    @FXML
    private void searchButtonEnterPressed(KeyEvent event) throws SQLException, IOException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            searchRooms(event);
        }
    }

    @FXML
    private void searchButtonMouseClicked(MouseEvent event) throws SQLException, IOException {
        searchRooms(event);
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
    private void boardCheckBoxMouseReleased(MouseEvent event) {
        if (boardCheckBox.isSelected()) {
            boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            boardCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void tvCheckBoxMouseReleased(MouseEvent event) {
        if (tvCheckBox.isSelected()) {
            tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            tvCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void projectorCheckBoxMouseReleased(MouseEvent event) {
        if (projectorCheckBox.isSelected()) {
            projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, greenColor, 10, 0.7, 0, 0));
        } else {
            projectorCheckBox.setEffect(new DropShadow(BlurType.THREE_PASS_BOX, whiteColor, 10, 0.7, 0, 0));
        }
    }

    @FXML
    private void bookButtonEnterPressed(KeyEvent event) throws IOException, SQLException {
        KeyCode Key = event.getCode();
        if (Key == KeyCode.ENTER) {
            saveRecord(event);
        }
    }

    @FXML
    private void bookButtonMouseClicked(MouseEvent event) throws IOException, SQLException {
        saveRecord(event);
    }

    @FXML
    private void chooseDateOnAction(ActionEvent event) {
        System.out.println(chooseDateDatePicker.getValue());
        clearEndTimeComboBox();
        clearStartTimeComboBox();
        chooseRoomComboBox.getSelectionModel().select(-1);
    }

    @FXML
    private void chooseRoomComboBoxOnAction(ActionEvent event) throws SQLException, IOException {
//        System.out.println(chooseRoomComboBox.getSelectionModel().getSelectedItem().getRoomID());
        clearEndTimeComboBox();
        clearStartTimeComboBox();
        LocalDate date = chooseDateDatePicker.getValue();
        String selectedDate = date.toString();
        if (chooseRoomComboBox.getSelectionModel().getSelectedItem() != null) {
            startTimeInformationArray = null;
            endTimeInformationArray = null;
            startTimeInformationArray = bookingDAO.getRoomMapArrayStartTime(chooseRoomComboBox.getSelectionModel().getSelectedItem().getRoomID(), selectedDate);
            endTimeInformationArray = bookingDAO.getRoomMapArrayEndTime(chooseRoomComboBox.getSelectionModel().getSelectedItem().getRoomID(), selectedDate);
            boolean[] startTimeArray = Arrays.copyOf(startTimeInformationArray, startTimeInformationArray.length);

            startTimeInformation = (ArrayList<Times>) timesDAO.getAllAvailableTimes(startTimeArray);

            roomStartTime = FXCollections.observableArrayList(startTimeInformation);

            startTimeComboBox.setItems(roomStartTime);

        }

    }

    @FXML
    private void startTimeComboBoxOnAction(ActionEvent event) throws SQLException, IOException {
//        System.out.println(startTimeComboBox.getSelectionModel().getSelectedItem());
//        System.out.println(startTimeComboBox.getSelectionModel().selectedIndexProperty().get());
        if (startTimeComboBox.getSelectionModel().getSelectedItem() != null) {
            clearEndTimeComboBox();
            boolean[] endTimeArray = Arrays.copyOf(endTimeInformationArray, endTimeInformationArray.length);
            int before = startTimeComboBox.getSelectionModel().getSelectedItem().getTimeID();
            for (int i = before; i >= 0; i--) {
                endTimeArray[i] = false;
            }

            for (int i = (before + 1); i < endTimeArray.length; i++) {
                if (endTimeArray[i] == false) {
                    for (int j = i; j < endTimeArray.length; j++) {
                        endTimeArray[j] = false;
                    }
                    break;
                }
            }

            endTimeInformation = (ArrayList<Times>) timesDAO.getAllAvailableTimes(endTimeArray);
            roomEndTime = FXCollections.observableArrayList(endTimeInformation);
            endTimeComboBox.setItems(roomEndTime);
        }

    }

    @FXML
    private void endTimeComboBoxOnAction(ActionEvent event) {
//        System.out.println(endTimeComboBox.getSelectionModel().getSelectedItem().getTimeName());
//        System.out.println(endTimeComboBox.getSelectionModel().getSelectedItem().getTimeID());
//        System.out.println(endTimeComboBox.getSelectionModel().getSelectedIndex());
    }

    @FXML
    private void bookARoomOnSelectionChanged(Event event) {
        if (!disableTab) {
            disableTab = true;
        } else {
            disableTab = false;
            bookARoomTab.disableProperty().set(true);
            clearAllComboboxes();
        }

    }

    @FXML
    private void bookARoomOnClose(Event event) {
        bookARoomTab.disableProperty().set(true);
    }

}
