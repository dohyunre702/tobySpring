package com.example.tobyspring.dao;

import com.example.tobyspring.factory.DaoFactory;
import com.example.tobyspring.user.User;
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

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        UserDao userDao = new DaoFactory().userDao(); //팩토리 사용하도록 수정
        User user = new User("0", "dhlee", "0000");
        userDao.add(user);

        userDao.deleteAll();
        assertEquals(userDao.getCount(), "0");

        User user2 = new User("1", "dh", "1010");
        userDao.add(user2);
        assertEquals(userDao.getCount(), "1");

        User result = userDao.findById("0");
        assertEquals("0", result.getId());
        //assertThat("0").isEqualTo(result.getId());
    }

}