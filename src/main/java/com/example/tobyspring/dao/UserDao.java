package com.example.tobyspring.dao;

import com.example.tobyspring.strategy.JdbcContext;
import com.example.tobyspring.strategy.StatementStrategy;
import com.example.tobyspring.user.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;

public class UserDao {

    //DataSource 적용
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    //jdbcTemplate는 jdbcContext를 완전히 대체
    // private JdbcContext jdbcContext;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(User user) {
        //익명 내부클래스 적용
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
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
        this.jdbcTemplate.update("DELETE FROM `likelion-db`.users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `likelion-db`.users", Integer.class);
    }
}