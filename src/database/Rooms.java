/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Medis
 */
public class Rooms {

    public Rooms(int roomID, String roomName, int sits, String board, String tv, String projector, String available) {
        this.roomID = new SimpleIntegerProperty(roomID);
        this.roomName = new SimpleStringProperty(roomName);
        this.sits = new SimpleIntegerProperty(sits);
        this.board = new SimpleStringProperty(board);
        this.tv = new SimpleStringProperty(tv);
        this.projector = new SimpleStringProperty(projector);
        this.available = new SimpleStringProperty(available);
    }

    public void setRoomID(int RoomID) {
        roomID.set(RoomID);
    }

    public int getRoomID() {
        return roomID.get();
    }

    public void setRoomName(String RoomName) {
        roomName.set(RoomName);
    }

    public String getRoomName() {
        return roomName.get();
    }

    public void setSits(int Sits) {
        sits.set(Sits);
    }

    public int getSits() {
        return sits.get();
    }

    public void setBoard(String Board) {
        board.set(Board);
    }

    public String getBoard() {
        return board.get();
    }

    public void setTv(String Tv) {
        tv.set(Tv);
    }

    public String getTv() {
        return tv.get();
    }

    public void setProjector(String Projector) {
        projector.set(Projector);
    }

    public String getProjector() {
        return projector.get();
    }

    public void setAvailable(String Available) {
        available.set(Available);
    }

    public String getAvailable() {
        return available.get();
    }

    @Override
    public String toString() {
        return getRoomName();
    }

    private final SimpleIntegerProperty roomID;
    private final SimpleStringProperty roomName;
    private final SimpleIntegerProperty sits;
    private final SimpleStringProperty board;
    private final SimpleStringProperty tv;
    private final SimpleStringProperty projector;
    private final SimpleStringProperty available;

}
