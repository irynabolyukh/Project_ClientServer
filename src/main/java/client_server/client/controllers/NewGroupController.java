package client_server.client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class NewGroupController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descrField;

    @FXML
    private Label statusLabel;

    @FXML
    void createGroup(ActionEvent event) {

    }

    @FXML
    void initialize() {

    }
}