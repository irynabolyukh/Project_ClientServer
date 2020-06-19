package client_server;

import client_server.client.StoreClientTCP;
import client_server.dao.UserDao;
import client_server.domain.Packet;
import client_server.domain.User;
import client_server.entities.*;
import com.google.common.primitives.UnsignedLong;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        UserDao daoUser = new UserDao("file.db");
//        daoUser.deleteTable();
//        User user = new User(3,"admin", DigestUtils.md5Hex("admin"), "admin");
//        daoUser.insert(user);
        StoreClientTCP client = new StoreClientTCP();
        final byte[] packet = MessageGenerator.generate((byte)1, UnsignedLong.valueOf(1));


        Packet receivedPacket = client.sendPacket(packet);

//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!\n" +
//                "Response from server: " + new String(receivedPacket.getBMsq().getMessage(), StandardCharsets.UTF_8)
//                + "\t for user with ID: " + receivedPacket.getSrcId()
//                + "\t for packet with ID: " + receivedPacket.getbPktId());

//        final DaoGroup daoGroup = new DaoGroup("file.db");
//        for(int i = 0; i < 30; i++){
//            daoGroup.insertGroup(new Group( i, "very good"+i, "Rodyna"));
//        }
//
//        daoGroup.getAll()
//                .forEach(System.out::println);
//
//
//        final DaoProduct daoProduct = new DaoProduct("file.db");
//        for(int i = 0; i < 30; i++){
//            daoProduct.insertProduct(new Product("гречка"+i , Math.random()*1000,Math.random()*1000,"very good", "Rodyna",i));
//        }
//
//        daoProduct.getAll(0,30)
//                .forEach(System.out::println);

//        List<Integer> list = new ArrayList<Integer>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        list.add(6);
//
//        ProductFilter fl = new ProductFilter();
//        fl.setIds(list);
//        fl.setFromPrice(3.99);
//        fl.setToPrice(10.99);

    }
}