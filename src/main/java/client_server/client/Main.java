package client_server.client;

//import client_server.dao.DaoGroup;
//import client_server.dao.DaoProduct;
//import client_server.domain.Group;
//import client_server.domain.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

//public class Main{

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        try {
            URL url = new File("src/main/java/client_server/client/views/login_window.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Storehouse");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

//    public static void main(String[] args) {
//
//        final DaoGroup daoGroup = new DaoGroup("file.db");
//        for(int i = 0; i < 30; i++){
//            daoGroup.insertGroup(new Group( i, "very good"+i, "Rodyna"));
//        }
//
//        final DaoProduct daoProduct = new DaoProduct("file.db");
//        daoProduct.deleteTable();
//        for(int i = 0; i < 30; i++){
//            daoProduct.insertProduct(new Product(i,"гречка"+i , Math.random()*1000,Math.random()*1000,"very good", "Rodyna",i));
//        }
//   }

}