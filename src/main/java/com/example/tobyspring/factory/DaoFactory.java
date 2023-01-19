package com.example.tobyspring.factory;

import com.example.tobyspring.ConnectionMaker;
import com.example.tobyspring.LocalConnectionMaker;
import com.example.tobyspring.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;

@Configuration
public class DaoFactory {

    //UserDao의 생성 책임을 맡음
    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao(connectionMaker());
        return userDao;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new LocalConnectionMaker();
    }
}
