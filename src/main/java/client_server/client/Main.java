package client_server.client;

import client_server.dao.DaoProduct;
import client_server.dao.UserDao;
import client_server.domain.Product;
import client_server.domain.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;

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

//        UserDao daoUser = new UserDao("file.db");
////        daoUser.deleteTable();
//        User user = new User(4,"user", DigestUtils.md5Hex("user"), "user");
//        daoUser.insert(user);
////        StoreClientTCP client = new StoreClientTCP();
////        final byte[] packet = MessageGenerator.generate((byte)1, UnsignedLong.valueOf(1));
////
////
////        Packet receivedPacket = client.sendPacket(packet);
//
////        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
////                "Response from server: " + new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8)
////                + "\t for user with ID: " + receivedPacket.getSrcId()
////                + "\t for packet with ID: " + receivedPacket.getbPktId());
//
////        final DaoGroup daoGroup = new DaoGroup("file.db");
////        for(int i = 0; i < 30; i++){
////            daoGroup.insertGroup(new Group( i, "very good"+i, "Rodyna"));
////        }
////
////        daoGroup.getAll()
////                .forEach(System.out::println);
////
////
//        final DaoProduct daoProduct = new DaoProduct("file.db");
//        for(int i = 0; i < 30; i++){
//            daoProduct.insertProduct(new Product(i,"гречка"+i , Math.random()*1000,Math.random()*1000,"very good", "Rodyna",i));
//        }
////
////        daoProduct.getAll(0,30)
////                .forEach(System.out::println);
//
////        List<Integer> list = new ArrayList<Integer>();
////        list.add(1);
////        list.add(2);
////        list.add(3);
////        list.add(4);
////        list.add(5);
////        list.add(6);
////
////        ProductFilter fl = new ProductFilter();
////        fl.setIds(list);
////        fl.setFromPrice(3.99);
////        fl.setToPrice(10.99);
//
//    }

}