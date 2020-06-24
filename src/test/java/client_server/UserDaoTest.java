package client_server;

import client_server.dao.UserDao;
import client_server.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDaoTest {

    private UserDao userDao;
    private String database = "test.db";

    @BeforeEach
    void init() {
        userDao = new UserDao(database);
    }

    @AfterEach
    void cleanUp() {
        userDao.deleteTable();
    }

    @Test
    void check_get_user_by_login(){
        User user = new User("login", "password", "role");
        userDao.insertUser(user);

        User user1 = userDao.getByLogin(user.getLogin());

        assert(user.equals(user1));
    }

    @Test
    void check_unknown_user(){
        User user = new User("login", "password", "role");

        User user1 = userDao.getByLogin(user.getLogin());

        assert(user1 == null);
    }

    @Test
    void check_login_duplication(){
        User user = new User("login", "password", "role");
        userDao.insertUser(user);

        User user1 = new User("login", "password", "role");
        int id = userDao.insertUser(user1);

        assert(id == -1);
    }

}
