package client_server.client.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import client_server.domain.Group;
import client_server.domain.Message;
import client_server.domain.Packet;
import com.google.common.primitives.UnsignedLong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static client_server.domain.Message.cTypes.GET_LIST_GROUPS;

public class UpdateProductController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField descrField;

    @FXML
    private TextField manufField;

    @FXML
    private ChoiceBox<Group> groupIdChoice;

    @FXML
    void updateProduct(ActionEvent event) {

    }

    @FXML
    void initialize() {
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

                ObservableList<Group> groups = FXCollections.observableArrayList();

                for (int i = 0; i < array.length(); i++) {
                    groups.add(new Group(array.getJSONObject(i)));
                }

                groupIdChoice.setItems(groups);

                System.out.println(groups.get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

