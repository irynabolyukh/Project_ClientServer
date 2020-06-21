package client_server.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private TableView<?> productsTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> amountCol;

    @FXML
    private TableColumn<?, ?> descrCol;

    @FXML
    private TableColumn<?, ?> manufacturerCol;

    @FXML
    private TableColumn<?, ?> groupIdCol;

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

    }
}
