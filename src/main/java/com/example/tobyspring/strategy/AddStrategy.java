package com.example.tobyspring.strategy;

import com.example.tobyspring.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStrategy implements StatementStrategy {

    private User user;
    public AddStrategy(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makePs(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        return ps;
    }
}
