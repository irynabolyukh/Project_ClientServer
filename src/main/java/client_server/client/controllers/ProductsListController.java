package client_server.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductsListController {

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

    }
}
