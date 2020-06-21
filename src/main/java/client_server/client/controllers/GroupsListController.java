package client_server.client.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import client_server.domain.Group;
import client_server.domain.Message;
import client_server.domain.Packet;
import com.google.common.primitives.UnsignedLong;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static client_server.domain.Message.cTypes.*;

public class GroupsListController {

    @FXML
    private Button addNewGroupBtn;

    @FXML
    private Button deleteGroupBtn;

    @FXML
    private Button updateGroupBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idFilterField;

    @FXML
    private TableView<Group> groupsTable;

    @FXML
    private TableColumn<Group, String> idCol;

    @FXML
    private TableColumn<Group, String> nameCol;

    @FXML
    private TableColumn<Group, String> descrCol;

    @FXML
    private Label statusLabel;


    @FXML
    void addNewGroupWindow(ActionEvent event) {

    }

    @FXML
    void deleteGroup(ActionEvent event) {

    }

    @FXML
    void filterGroups(ActionEvent event) {

        statusLabel.setText("");

        try {
            int id = Integer.parseInt(idFilterField.getText());
            if (id >= 0) {
                Message msg = new Message(Message.cTypes.GET_GROUP.ordinal(), 1, idFilterField.getText().getBytes(StandardCharsets.UTF_8));
                Packet packet = new Packet((byte) 1, UnsignedLong.valueOf(GlobalContext.packetId++), msg);


                Packet receivedPacket = GlobalContext.clientTCP.sendPacket(packet.toPacket());

                int command = receivedPacket.getBMsq().getcType();
                Message.cTypes[] val = Message.cTypes.values();
                Message.cTypes command_type = val[command];


                if (command_type == GET_GROUP) {
                    String message = new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8);
                    JSONObject information = new JSONObject(message);
                    try {
                        JSONObject object = information.getJSONObject("object");
                        Group group = new Group(object);

                        groupsTable.getItems().clear();
                        groupsTable.getItems().add(group);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        statusLabel.setText(information.getString("message"));
                    }
                } else {
                    statusLabel.setText("Can't show group.");
                }
            }else{
                statusLabel.setText("Incorrect group ID.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Incorrect group ID.");
        }

        idFilterField.clear();

    }

    @FXML
    void toProductsList(ActionEvent event) {

    }

    @FXML
    void updateGroupWindow(ActionEvent event) {

    }

    @FXML
    void showAllGroups(ActionEvent event) {
        statusLabel.setText(" ");
        resetTable();
    }

    @FXML
    void initialize() {
        if (GlobalContext.role.equals("user")) {
            addNewGroupBtn.setDisable(true);
            deleteGroupBtn.setDisable(true);
            updateGroupBtn.setDisable(true);
        }

        descrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        System.out.println("init");
        resetTable();
    }

    private void resetTable() {
        Message msg = new Message(GET_LIST_GROUPS.ordinal(), 1, "".getBytes(StandardCharsets.UTF_8));
        Packet packet = new Packet((byte) 1, UnsignedLong.valueOf(GlobalContext.packetId++), msg);

        Packet receivedPacket = GlobalContext.clientTCP.sendPacket(packet.toPacket());

        int command = receivedPacket.getBMsq().getcType();
        Message.cTypes[] val = Message.cTypes.values();
        Message.cTypes command_type = val[command];


        if (command_type == GET_LIST_GROUPS) {
            String message = new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8);
            JSONObject information = new JSONObject(message);
            System.out.println("command");
            try {
                JSONObject list = information.getJSONObject("object");
                JSONArray array = list.getJSONArray("list");

                List<Group> groups = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    groups.add(new Group(array.getJSONObject(i)));
                }

                groupsTable.getItems().clear();
                groupsTable.getItems().addAll(groups);

                System.out.println(groups.get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
                statusLabel.setText("Can't show groups.");
            }
        } else {
            statusLabel.setText("Can't show groups.");
        }
    }
}
