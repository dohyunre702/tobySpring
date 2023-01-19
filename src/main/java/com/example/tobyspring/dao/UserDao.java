package com.example.tobyspring.dao;

import com.example.tobyspring.strategy.JdbcContext;
import com.example.tobyspring.strategy.StatementStrategy;
import com.example.tobyspring.user.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;

public class UserDao {

    //DataSource 적용
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    //jdbcTemplate는 jdbcContext를 완전히 대체
    // private JdbcContext jdbcContext;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    RowMapper<User> rowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));
            return user;
        }
    };

    public void add(User user) {
        //익명 내부클래스 적용
        this.jdbcTemplate.update("INSERT INTO users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public User findById(String id) throws SQLException {
        //RowMapper: 인터페이스 구현체로 ResultSet의 정보를 User에 매핑할 때 사용
        String sql = "SELECT * FROM `likelion-db`.users WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM `likelion-db`.users");
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM `likelion-db`.users", Integer.class);
    }

    public List<User> getAll() {
        //모든 user를 list에 담아 리턴
        String sql = "SELECT * FROM `likelion-db`.users ORDER BY id";
        return this.jdbcTemplate.query(sql, rowMapper);
    }
}