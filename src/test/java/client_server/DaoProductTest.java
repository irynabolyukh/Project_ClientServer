package client_server;

import client_server.dao.DaoGroup;
import client_server.dao.DaoProduct;
import client_server.domain.Group;
import client_server.domain.Product;
import client_server.domain.ProductFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DaoProductTest {

    private DaoGroup daoGroup;
    private DaoProduct daoProduct;
    private String database = "test.db";

    @BeforeEach
    void init() {
        daoGroup = new DaoGroup(database);
        daoProduct = new DaoProduct(database);
        daoProduct.database = database;
        Group group = new Group(23,"крупи", "корисно");
        daoGroup.insertGroup(group);
    }

    @AfterEach
    void cleanUp() {
        daoProduct.deleteTable();
        daoGroup.deleteTable();
    }

    @Test
    void check_insert_and_get_product(){
        Product product = new Product(1,"гречка",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product);

        Product insertedProduct = daoProduct.getProduct(1);

        assert(product.equals(insertedProduct));
    }

    @Test
    void check_product_duplication(){
        Product product = new Product(1,"гречка",234.5,324,"good","rodyna",23);
        daoProduct.insertProduct(product);

        Product product1 = new Product(1,"гречка",234.5,324,"good","rodyna",23);
        int id = daoProduct.insertProduct(product1);

        assert(id == -1);
    }

    @Test
    void check_update_and_get_product(){

        Product product1 = new Product(1,"гречка",234.5,324,"good","rodyna",23);
        Product product2 = new Product(1,"пшоно",234.5,324,"good","rodyna",23);

        daoProduct.insertProduct(product1);
        System.out.println(daoProduct.getProduct(1));

        daoProduct.updateProduct(product2);
        System.out.println(daoProduct.getProduct(1));

        Product updatedProduct = daoProduct.getProduct(1);
        System.out.println(updatedProduct);

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
}
