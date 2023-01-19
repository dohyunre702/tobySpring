package com.example.tobyspring.dao;

import com.example.tobyspring.factory.DaoFactory;
import com.example.tobyspring.user.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoTest {

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        UserDao userDao = new DaoFactory().userDao(); //팩토리 사용하도록 수정
        User user = new User("0", "dhlee", "0000");
        userDao.add(user);

        User result = userDao.findById("0");
        assertEquals("0", result.getId());
        //assertThat("0").isEqualTo(result.getId());
    }
}