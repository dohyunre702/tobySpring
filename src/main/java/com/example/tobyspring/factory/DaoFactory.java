package com.example.tobyspring.factory;

import com.example.tobyspring.ConnectionMaker;
import com.example.tobyspring.LocalConnectionMaker;
import com.example.tobyspring.dao.UserDao;

import java.sql.Connection;


public class DaoFactory {

    //UserDao의 생성 책임을 맡음
    public UserDao userDao() {
        UserDao userDao = new UserDao(connectionMaker());
        return userDao;
    }

    public ConnectionMaker connectionMaker() {
        return new LocalConnectionMaker();
    }
}
