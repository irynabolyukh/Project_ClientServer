package client_server;

import client_server.entities.*;

public class Main {

    public static void main(String[] args) {
        final DaoGroup daoGroup = new DaoGroup("file.db");
        for(int i = 0; i < 30; i++){
            daoGroup.insertGroup(new Group( i, "very good"+i, "Rodyna"));
        }

        daoGroup.getAll()
                .forEach(System.out::println);


        final DaoProduct daoProduct = new DaoProduct("file.db");
        for(int i = 0; i < 30; i++){
            daoProduct.insertProduct(new Product("гречка"+i , Math.random()*1000,Math.random()*1000,"very good", "Rodyna",i));
        }

        daoProduct.getAll(0,30)
                .forEach(System.out::println);

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