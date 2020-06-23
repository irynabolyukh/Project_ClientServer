package client_server.client.controllers;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import client_server.client.GlobalContext;
import client_server.domain.Message;
import client_server.domain.Packet;
import client_server.domain.Product;
import com.google.common.primitives.UnsignedLong;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DeductAmountController {

    private Product product;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountField;

    @FXML
    private Label statusLabel;

    @FXML
    void deductAmount(ActionEvent event) {
        if(amountField.getText().isEmpty()){
            statusLabel.setText("Fill out the field before deducting.");
        }else{
            Double amount = null;
            try{
                amount = Double.parseDouble(amountField.getText());
            }catch(NumberFormatException e){
                statusLabel.setText("Incorrect amount.");
            }
            if(amount>=0 && amount!=null && amount<=product.getAmount()){

                Product productToUpdate = new Product(product.getId(), product.getName(), product.getPrice(), product.getAmount()-amount, product.getDescription(), product.getManufacturer(), product.getGroup_id());
                UpdateProductController.updateProduct(productToUpdate, statusLabel);

            }else{
                statusLabel.setText("Incorrect amount.");
            }
        }
    }


    @FXML
    void initialize() {

    }

    public void initData(Product selectedItem) {
        product = selectedItem;
    }
}
