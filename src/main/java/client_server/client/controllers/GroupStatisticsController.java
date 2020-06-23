package client_server.client.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import client_server.domain.*;
import com.google.common.primitives.UnsignedLong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static client_server.domain.Message.cTypes.*;

public class GroupStatisticsController {

    private Group group;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label groupNameLabel;

    @FXML
    private ChoiceBox<Group> groupIdChoice;

    @FXML
    private Label totalCostLabel;

    @FXML
    private TableView<ProductStatistics> productsTable;

    @FXML
    private TableColumn<ProductStatistics, String> idCol;

    @FXML
    private TableColumn<ProductStatistics, String> nameCol;

    @FXML
    private TableColumn<ProductStatistics, String> priceCol;

    @FXML
    private TableColumn<ProductStatistics, String> amountCol;

    @FXML
    private TableColumn<ProductStatistics, String> descrCol;

    @FXML
    private TableColumn<ProductStatistics, String> manufacturerCol;

    @FXML
    private TableColumn<ProductStatistics, String> totalCostCol;

    @FXML
    private Label statusLabel;

    @FXML
    void showStatistics(ActionEvent event) {
        resetTable(groupIdChoice.getValue().getId());
    }

    @FXML
    void initialize() {

    }

    public void initData(Group selectedGroup) {
        group = selectedGroup;

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
                    Group groupLocal = new Group(array.getJSONObject(i));
                    groups.add(groupLocal);

                    if(groupLocal.getId() == group.getId()){
                        groupIdChoice.setValue(groupLocal);
                    }
                }

                groupIdChoice.setItems(groups);

                System.out.println(groups.get(0).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("total_cost"));

        resetTable(group.getId());
    }

    private void resetTable(Integer group_id){
        Double totalGroupCost = 0.0;

        Message msg = new Message(GET_PRODUCTS_STATISTICS.ordinal(), 1, group_id.toString().getBytes(StandardCharsets.UTF_8));

        Packet packet = new Packet((byte) 1, UnsignedLong.valueOf(GlobalContext.packetId++), msg);

        Packet receivedPacket = GlobalContext.clientTCP.sendPacket(packet.toPacket());

        int command = receivedPacket.getBMsq().getcType();
        Message.cTypes[] val = Message.cTypes.values();
        Message.cTypes command_type = val[command];


        if (command_type == GET_PRODUCTS_STATISTICS) {
            String message = new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8);
            JSONObject information = new JSONObject(message);

            try {
                JSONObject list = information.getJSONObject("object");
                JSONArray array = list.getJSONArray("list");

                List<ProductStatistics> products = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    ProductStatistics productStatistics = new ProductStatistics(array.getJSONObject(i));
                    totalGroupCost += productStatistics.getTotal_cost();
                    products.add(productStatistics);
                }

                productsTable.getItems().clear();
                productsTable.getItems().addAll(products);
                totalCostLabel.setText(totalCostLabel.getText() + totalGroupCost.toString());

            } catch (JSONException e) {
                e.printStackTrace();
                statusLabel.setText(information.getString("message"));
            }

        } else {
            statusLabel.setText("Can't show statistics!");
        }
    }

}
