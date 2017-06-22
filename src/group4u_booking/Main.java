/*
 * This project is done as a part of course BTH.
 */
package group4u_booking;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Medis
 */
public class Main extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
    public static String usrnm;
    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void start(final Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/resources/icon.png")));
        stage.setTitle("Group 4, BTH 2016: iBTH");
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);

        stage.setScene(scene);
        stage.show();

        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void chngeScenes(Event event, String file) throws Exception {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource(file));
        scene = new Scene(root);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();

        dragStage();
    }

    public void dragStage() {
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}
