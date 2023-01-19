package com.example.tobyspring.dao;

import com.example.tobyspring.factory.DaoFactory;
import com.example.tobyspring.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

//테스트코드에서 Spring ApplicationContext 사용 위해 @ExtendWith, @ContextConfiguration 추가
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext applicationContext;

    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        this.userDao = applicationContext.getBean("userDao", UserDao.class);
        userDao.deleteAll();
        this.user1 = new User("1", "dhlee", "1111");
        this.user1 = new User("2", "dhlee", "2111");
        this.user1 = new User("3", "dhlee", "3111");
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        assertEquals(userDao.getCount(), "0");

        userDao.add(user1);
        assertEquals(userDao.getCount(), "1");

        userDao.add(user2);
        userDao.add(user3);
        assertEquals(userDao.getCount(), "3");

        User result = userDao.findById("3");
        assertEquals("3", result.getId());
        //assertThat("0").isEqualTo(result.getId());
    }

}