package client_server;

import client_server.dao.DaoGroup;
import client_server.domain.Group;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DaoGroupTest {

    private DaoGroup daoGroup;
    private String database = "test.db";

    @BeforeEach
    void init() {
        daoGroup = new DaoGroup(database);
        Group group = new Group(23,"крупи", "корисно");
        daoGroup.insertGroup(group);
    }

    @AfterEach
    void cleanUp() {
        daoGroup.deleteTable();
    }

    @Test
    void check_insert_and_get_group(){
        Group group = new Group(100, "не крупи", "смачно");

        daoGroup.insertGroup(group);
        Group insertedGroup = daoGroup.getGroup(100);

        assert(group.equals(insertedGroup));
    }

    @Test
    void check_group_duplication(){
        Group group = new Group(100, "не крупи", "смачно");
        daoGroup.insertGroup(group);

        Group group1 = new Group(100, "не крупи", "смачно");
        int id = daoGroup.insertGroup(group1);

        assert(id == -1);
    }

    @Test
    void check_update_and_get_group(){

        Group group = new Group(100, "не крупи", "смачно");
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
