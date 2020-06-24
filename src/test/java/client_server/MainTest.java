package client_server;

import client_server.dao.DaoGroup;
import client_server.dao.DaoProduct;
import client_server.dao.UserDao;
import client_server.domain.*;
import client_server.entities.DeEncriptor;
import com.google.common.primitives.UnsignedLong;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MainTest{

    private DaoGroup daoGroup;
    private DaoProduct daoProduct;
    private UserDao userDao;
    private String database = "test.db";

    @BeforeEach
    void init() {
        daoGroup = new DaoGroup(database);
        daoProduct = new DaoProduct(database);
        userDao = new UserDao(database);
        Group group = new Group(23,"крупи", "корисно");
        daoGroup.insertGroup(group);
    }

    @AfterEach
    void cleanUp() {
        userDao.deleteTable();
        daoProduct.deleteTable();
        daoGroup.deleteTable();
    }

    @Test
    void checkWhether_InvalidMagicByte() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Packet(Hex.decodeHex("15"))
        );
    }

    @Test
    void checkWhether_InvalidCrc(){
        final String input = "1300000000000000000a000000300a8b6c0221f35d79ec1715362980276b7c96a5ec7b0f8e40428fff0f7f54652c00dce9ea";
        assertThrows(
                IllegalArgumentException.class,
                () -> new Packet(Hex.decodeHex(input))
        );
    }

    @Test
    void check_DeEncriptor() throws NoSuchAlgorithmException, NoSuchPaddingException,
            BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException,
            InvalidKeyException {
        Message originalMessage = new Message(1,1, new String("hello from user").getBytes());
        byte[] origMessageToBytes = originalMessage.toPacketPart();
        byte[] encode_decode = DeEncriptor.decode(DeEncriptor.encode(originalMessage.getWhole()));
        assert(Arrays.equals(origMessageToBytes, encode_decode));
    }

    @Test
    void check_DeEncriptor_forPacket() throws NoSuchPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException {
        Message originalMessage = new Message(1,1, new String("hello from user").getBytes());
        byte[] origMessageToBytes = DeEncriptor.decode(originalMessage.toPacketPart());

        Packet packet = new Packet((byte)1, UnsignedLong.ONE, originalMessage);
        byte[] packetToBytes = packet.toPacket();//encoding packet

        Packet decoded_packet = new Packet(packetToBytes);
        Message decoded_message = decoded_packet.getBMsq();

        byte[] decoded_messageToBytes = DeEncriptor.decode(decoded_message.toPacketPart());
        assert(Arrays.equals(origMessageToBytes, decoded_messageToBytes));
    }

    @Test
    void check_DeEncriptor_forPacket2(){
        Message originalMessage = new Message(1,1, new String("hello from user").getBytes());
        Packet packet = new Packet((byte)1, UnsignedLong.ONE, originalMessage);

        byte[] packBytes = packet.toPacket();//encodes packet

        Packet packet1 = new Packet(packBytes);//decodes packet

        byte [] packBytes1 = packet1.toPacket();//encodes packet

        assert(Arrays.equals(packBytes, packBytes1));
    }


    //FOR PRODUCTS
    @Test
    void check_insert_and_get_product(){
        Product product = new Product(1,"гречка",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product);

        Product insertedProduct = daoProduct.getProduct(1);

        assert(product.equals(insertedProduct));
    }

    @Test
    void check_update_and_get_product(){

        Product product1 = new Product(1,"гречка",234.5,324,"good","rodyna",23);
        Product product2 = new Product(1,"пшоно",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product1);
        System.out.println(daoProduct.getProduct(1));
        daoProduct.updateProduct(product2);
        System.out.println(daoProduct.getProduct(1));

//        List<Product> products = daoProduct.getAll(0,10);
//        for(int i=0;i<products.size();i++){
//            System.out.println(products.get(i));
//        }

        Product updatedProduct = daoProduct.getProduct(1);

        assert(product2.equals(updatedProduct));
    }

    @Test
    void check_get_product(){
        assert(daoProduct.getProduct(3456) == null);
    }

    @Test
    void check_get_All_Products(){

        int expected = 5;

        for(int i = 0; i < expected; i++){
            daoProduct.insertProduct(new Product("гречка"+i , Math.random()*100,Math.random()*100,"very good", "Summer",23));
        }

        List<Product> products = daoProduct.getAll(0,10);

        assert(expected == products.size());
    }

    @Test
    void check_filter_for_Product(){
        Group group = new Group(2,"напої", "освіжаючі");
        daoGroup.insertGroup(group);

        ProductFilter filter = new ProductFilter();
        filter.setManufacturer("Summer");
        filter.setGroup_id(23);

        int expected = 5;

        for(int i = 0; i < expected; i++){
            daoProduct.insertProduct(new Product("гречка"+i , Math.random()*100,Math.random()*100,"very good", "Summer",23));
        }
        for(int i = 5; i < 10; i++){
            daoProduct.insertProduct(new Product("мінеральна"+i , Math.random()*100,Math.random()*100,"good", "Rodyna",2));
        }

        List<Product> products = daoProduct.getList(0,10, filter);

        assert(expected == products.size());
    }

    @Test
    void check_delete_product(){

        Product product = new Product(1,"гречка",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product);
        daoProduct.deleteProduct(1);

        assert(daoProduct.getProduct(1) == null);

    }

    @Test
    void check_delete_all_products(){

        Product product1 = new Product(1,"гречка",234.5,324,"good","rodyna",23);
        Product product2 = new Product(2,"пшоно",234.5,324,"good","rodyna",23);
        Product product3 = new Product(3,"пшоно2",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product1);
        daoProduct.insertProduct(product2);
        daoProduct.insertProduct(product3);

        daoProduct.deleteAll();

        List<Product> products = daoProduct.getAll(0, 10);

        assert(products.size()==0);
    }

    @Test
    void check_delete_all_products_in_group(){
        Group group = new Group(2,"напої", "освіжаючі");
        daoGroup.insertGroup(group);

        Product product1 = new Product(1,"мінеральна",234.5,324,"good","rodyna",2);
        Product product2 = new Product(2,"пшоно",234.5,324,"good","rodyna",23);
        Product product3 = new Product(3,"гречка",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product1);
        daoProduct.insertProduct(product2);
        daoProduct.insertProduct(product3);

        daoProduct.deleteAllInGroup(23);

        ProductFilter filter = new ProductFilter();
        filter.setGroup_id(23);
        List<Product> products = daoProduct.getList(0, 10, filter);

        assert(products.size()==0 && daoProduct.getAll(0,10).size()==1);
    }


    //FOR GROUPS
    @Test
    void check_insert_and_get_group(){
        Group group = new Group(100, "крупи", "смачно");

        daoGroup.insertGroup(group);
        Group insertedGroup = daoGroup.getGroup(100);

        assert(group.equals(insertedGroup));
    }

    @Test
    void check_update_and_get_group(){

        Group group = new Group(100, "крупи", "смачно");
        Group group2 = new Group(100, "мийні засоби", "не смачно");

        daoGroup.insertGroup(group);
        daoGroup.updateGroup(group2);

        Group updatedGroup = daoGroup.getGroup(100);

        assert(group2.equals(updatedGroup));

    }

    @Test
    void check_get_group(){
        assert( daoGroup.getGroup(1) == null);
    }

    @Test
    void check_get_All_Groups(){
        int expected = 5;

        for(int i = 1; i < expected; i++){
            daoGroup.insertGroup(new Group(i ,"Фрукти"+i, "смачні"));
        }

        List<Group> groups = daoGroup.getAll();

        assert(expected == groups.size());
    }

    @Test
    void check_delete_group(){

        Group group = new Group(100, "крупи", "смачно");
        Group group2 = new Group(10, "мийні засоби", "не смачно");

        daoGroup.insertGroup(group);
        daoGroup.insertGroup(group2);
        daoGroup.deleteGroup(100);

        assert(daoGroup.getGroup(100)==null);
    }

    @Test
    void check_delete_all_groups(){

        Group group = new Group(100, "крупи", "смачно");
        Group group2 = new Group(10, "мийні засоби", "не смачно");
        Group group3 = new Group(11, "одяг", "не смачно");

        daoGroup.insertGroup(group);
        daoGroup.insertGroup(group2);
        daoGroup.insertGroup(group3);

        daoGroup.deleteAll();

        List<Group> groups = daoGroup.getAll();

        assert(groups.size()==0);
    }

}