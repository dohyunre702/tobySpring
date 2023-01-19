package com.example.tobyspring.dao;

import com.example.tobyspring.ConnectionMaker;
import com.example.tobyspring.LocalConnectionMaker;
import com.example.tobyspring.user.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;

public class UserDao {
    //1. getConnection 메서드로 추출
    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();
        ps.close();
        c.close();
    }

    public User findById(String id) throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM `likelion-db`.users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        //User이 null이 아닐 때만 User 객체 생성.

        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        };

        rs.close();
        ps.close();
        c.close();

        //User 객체가 null인 채로 끝나면 에러 던지기
        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("DELETE FROM `likelion-db`.`users`");
        ps.executeUpdate(); //int 반환

        ps.close();
        c.close();
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = connectionMaker.makeConnection();
        PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM `likelion-db`.`users`");
        ResultSet rs = ps.executeQuery(); //ResultSet 반환. 결과값 받아오기
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        c.close();

        return count;
    }
}
