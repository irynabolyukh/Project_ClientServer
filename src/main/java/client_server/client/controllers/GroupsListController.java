package client_server.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class GroupsListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idFilterField;

    @FXML
    private TableView<?> groupsTable;

    @FXML
    private TableColumn<?, ?> idCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> descrCol;

    @FXML
    void addNewGroupWindow(ActionEvent event) {

    }

    @FXML
    void deleteGroup(ActionEvent event) {

    }

    @FXML
    void filterGroups(ActionEvent event) {

    }

    @FXML
    void toProductsList(ActionEvent event) {

    }

    @FXML
    void updateGroupWindow(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }
}
