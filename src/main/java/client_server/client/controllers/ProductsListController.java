package client_server.client.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
        statusLabel.setText("");

        List<Integer> listId = new ArrayList<Integer>();
        ProductFilter fl = new ProductFilter();
        if(!idFilter.getText().isEmpty()){
            try{
                int id = Integer.parseInt(idFilter.getText());
                if(id >= 0){
                    listId.add(id);
                    fl.setIds(listId);
                }else{
                    statusLabel.setText("Incorrect product ID.");
                }
            }catch (NumberFormatException e) {
                statusLabel.setText("Incorrect product ID.");
            }
        }

        if(!priceFromFilter.getText().isEmpty()){
            try{
                double price = Double.parseDouble(priceFromFilter.getText());
                if(price >= 0){
                    fl.setFromPrice(price);
                }else{
                    statusLabel.setText("Incorrect price \"from\".");
                }
            }catch (NumberFormatException e) {
                statusLabel.setText("Incorrect price \"from\".");
            }
        }

        if(!priceToFilter.getText().isEmpty()){
            try{
                double price = Double.parseDouble(priceToFilter.getText());
                if(price >= 0){
                    fl.setToPrice(price);
                }else{
                    statusLabel.setText("Incorrect price \"to\".");
                }
            }catch (NumberFormatException e) {
                statusLabel.setText("Incorrect price \"to\".");
            }
        }

        if(!manufacturerFilter.getText().isEmpty()){
           fl.setManufacturer(manufacturerFilter.getText());
        }

        if(!groupIdFilter.getText().isEmpty()){
            try{
                int gr_id = Integer.parseInt(groupIdFilter.getText());
                if(gr_id >= 0){
                    fl.setGroup_id(gr_id);
                }else{
                    statusLabel.setText("Incorrect group ID.");
                }
            }catch (NumberFormatException e) {
                statusLabel.setText("Incorrect group ID.");
            }
        }

        showFilteredProducts(fl);
        idFilter.clear();
        priceToFilter.clear();
        priceFromFilter.clear();
        manufacturerFilter.clear();
        groupIdFilter.clear();
    }


    @FXML
    void toGroupList(ActionEvent event) throws MalformedURLException {
        FXMLLoader loader = new FXMLLoader();
        Stage stage = (Stage) idFilter.getScene().getWindow();
        URL url = null;

        url = new File("src/main/java/client_server/client/views/groups_list.fxml").toURI().toURL();

        Parent root = null;
        try {
            root = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
            statusLabel.setText("Can't open groups.");
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void updateProductWindow(ActionEvent event) {

    }

    @FXML
    void showAllProducts(ActionEvent event) {
        statusLabel.setText("");
        resetTable();
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

        resetTable();
    }

    private void resetTable() {
        ProductFilter fl = new ProductFilter();
        showFilteredProducts(fl);
    }


    private void showFilteredProducts(ProductFilter fl) {
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
                statusLabel.setText("Can't show products!");
            }

        } else {
            statusLabel.setText("Can't show products!");
        }
    }
}
