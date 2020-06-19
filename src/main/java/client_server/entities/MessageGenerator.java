package client_server.entities;

import client_server.domain.*;
import com.google.common.primitives.UnsignedLong;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MessageGenerator {

    public static byte[] generate(byte srcId, UnsignedLong bPktId){

        Product prod = new Product(2, "пшоно", 12, 99, "опис1", "Родина", 1);
        Product prod2 = new Product(2, "пшоно2", 12, 99, "опис2", "Рошен", 1);
        Product prod3 = new Product(2, "пшоно3", 12, 99, "опис3", "Родина", 1);
        Group group = new Group(100, "крупи", "смачно");
        Group group2 = new Group(8, "мийні засоби", "не смачно");

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);

        ProductFilter fl = new ProductFilter();
        fl.setIds(list);
        fl.setFromPrice(3.99);
        fl.setToPrice(1000.99);
        fl.setManufacturer("Rodyna");

        JSONObject jsonObj = new JSONObject("{"+"\"page\":"+0+", \"size\":"+10+
                ", \"productFilter\":"+ fl.toJSON().toString() +"}");

        UserCredentials user = new UserCredentials("admin", DigestUtils.md5Hex("admin"));

        Message msg1 = new Message(Message.cTypes.DELETE_ALL_IN_GROUP.ordinal() , 1, "2".getBytes(StandardCharsets.UTF_8));
        Message msg2 = new Message(Message.cTypes.DELETE_GROUP.ordinal() , 1, "1".getBytes(StandardCharsets.UTF_8));
        Message msg3 = new Message(Message.cTypes.DELETE_PRODUCT.ordinal() , 1, "21".getBytes(StandardCharsets.UTF_8));
        Message msg4 = new Message(Message.cTypes.UPDATE_PRODUCT.ordinal() , 1, prod2.toJSON().toString().getBytes(StandardCharsets.UTF_8));
        Message msg5 = new Message(Message.cTypes.INSERT_PRODUCT.ordinal() , 1, prod3.toJSON().toString().getBytes(StandardCharsets.UTF_8));
        Message msg6 = new Message(Message.cTypes.GET_PRODUCT.ordinal() , 1, "22".getBytes(StandardCharsets.UTF_8));
        Message msg7 = new Message(Message.cTypes.GET_GROUP.ordinal() , 1, "2".getBytes(StandardCharsets.UTF_8));
        Message msg8 = new Message(Message.cTypes.GET_LIST_GROUPS.ordinal() , 1, "".getBytes(StandardCharsets.UTF_8));
        Message msg9 = new Message(Message.cTypes.INSERT_GROUP.ordinal() , 1, group.toJSON().toString().getBytes(StandardCharsets.UTF_8));
        Message msg10 = new Message(Message.cTypes.UPDATE_GROUP.ordinal() , 1, group2.toJSON().toString().getBytes(StandardCharsets.UTF_8));
        Message msg11 = new Message(Message.cTypes.GET_LIST_PRODUCTS.ordinal() , 1, jsonObj.toString().getBytes(StandardCharsets.UTF_8));
        Message msg12 = new Message(Message.cTypes.LOGIN.ordinal() , 1, user.toJSON().toString().getBytes(StandardCharsets.UTF_8));

        //Message[] msgArray = {msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11};

        Packet packet = new Packet(srcId, bPktId, msg12);

        return packet.toPacket();
    }
}