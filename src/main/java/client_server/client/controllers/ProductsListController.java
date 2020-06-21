package client_server.client.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import client_server.domain.*;
import com.google.common.primitives.UnsignedLong;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static client_server.domain.Message.cTypes.GET_LIST_GROUPS;
import static client_server.domain.Message.cTypes.GET_LIST_PRODUCTS;

public class ProductsListController {


    @FXML
    private Button addNewProductBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Button updateProductBtn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idFilter;

    @FXML
    private TextField priceFromFilter;

    @FXML
    private TextField priceToFilter;

    @FXML
    private TextField manufacturerFilter;

    @FXML
    private TextField groupIdFilter;

    @FXML
    private TableView<Product> productsTable;

    @FXML
    private TableColumn<Product, String> idCol;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private TableColumn<Product, String> amountCol;

    @FXML
    private TableColumn<Product, String> descrCol;

    @FXML
    private TableColumn<Product, String> manufacturerCol;

    @FXML
    private TableColumn<Product, String> groupIdCol;

    @FXML
    private Label statusLabel;

    @FXML
    void addNewProductWindow(ActionEvent event) {

    }

    @FXML
    void deleteProduct(ActionEvent event) {

    }

    @FXML
    void filterProducts(ActionEvent event) {

    }

    @FXML
    void toGroupList(ActionEvent event) {

    }

    @FXML
    void updateProductWindow(ActionEvent event) {

    }

    @FXML
    void showAllProducts(ActionEvent event) {

    }

    @FXML
    void initialize() {
        if(GlobalContext.role.equals("user")){
            addNewProductBtn.setDisable(true);
            deleteProductBtn.setDisable(true);
            updateProductBtn.setDisable(true);
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        groupIdCol.setCellValueFactory(new PropertyValueFactory<>("group_id"));

        System.out.println("init");
        resetTable();
    }

    private void resetTable() {
        ProductFilter fl = new ProductFilter();
        JSONObject jsonObj = new JSONObject("{"+"\"page\":"+0+", \"size\":"+1000+
                ", \"productFilter\":"+ fl.toJSON().toString() +"}");
        Message msg = new Message(GET_LIST_PRODUCTS.ordinal() , 1, jsonObj.toString().getBytes(StandardCharsets.UTF_8));

        Packet packet = new Packet((byte) 1, UnsignedLong.valueOf(GlobalContext.packetId++), msg);
        System.out.println("reset");

        Packet receivedPacket = GlobalContext.clientTCP.sendPacket(packet.toPacket());

        int command = receivedPacket.getBMsq().getcType();
        Message.cTypes[] val = Message.cTypes.values();
        Message.cTypes command_type = val[command];

        System.out.println("reset1");

        if (command_type == GET_LIST_PRODUCTS) {
            String message = new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8);
            JSONObject information = new JSONObject(message);
            System.out.println("command");
            try {
                JSONObject list = information.getJSONObject("object");
                JSONArray array = list.getJSONArray("list");

                List<Product> products = new ArrayList<>();

                for (int i = 0; i < array.length(); i++) {
                    products.add(new Product(array.getJSONObject(i)));
                }

                productsTable.getItems().clear();
                productsTable.getItems().addAll(products);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

        }
    }
}
