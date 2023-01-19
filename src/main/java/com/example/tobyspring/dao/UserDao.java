package com.example.tobyspring.dao;

import com.example.tobyspring.ConnectionMaker;
import com.example.tobyspring.LocalConnectionMaker;
import com.example.tobyspring.strategy.AddStrategy;
import com.example.tobyspring.strategy.DeleteAllStrategy;
import com.example.tobyspring.strategy.StatementStrategy;
import com.example.tobyspring.user.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import javax.sql.rowset.RowSetWarning;
import java.sql.*;

public class UserDao {

    //DataSource 적용
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //try/catch/finally context code 메서드로 분리
    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            ps = stmt.makePs(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void add(User user) {
        //익명 내부클래스 적용
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePs(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement("INSERT INTO users(id, name, password) values(?,?,?)");
                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                return ps;
            }
        });
    }

    public User findById(String id) throws SQLException {
        Connection c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement("SELECT * FROM `likelion-db`.users WHERE id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;

        //User이 null이 아닐 때만 User 객체 생성.
        if (rs.next()) {
            user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
        }

        rs.close();
        ps.close();
        c.close();

        //User 객체가 null인 채로 끝나면 에러 던지기
        if (user == null) throw new EmptyResultDataAccessException(1);
        return user;
    }

    public void deleteAll() {
        jdbcContextWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makePs(Connection c) throws SQLException {
                return c.prepareStatement("DELETE FROM `likelion-db`.users");
            }
        }); //익명 내부클래스 적용

        /* lambda
        jebcContextWithStatementStrategy(c -> c.prepareStatement("DELETE FROM `likelion-db`.users"));
         */
    }

    public int getCount() {
        int count = 0;
        StatementStrategy stmt = (connection) -> {
            return connection.prepareStatement("SELECT COUNT(*) FROM `likelion-db`.users");
        };
        jdbcContextWithStatementStrategy(stmt);
        return count;
    }
}