package com.example.tobyspring.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStrategy implements StatementStrategy{
    @Override
    public PreparedStatement makePs(Connection c) throws SQLException {
        return c.prepareStatement("DELETE FROM `likelion`.`users`");

        /* lambda
        StatementStrategy stmt = (connection) -> {
		    return connection.prepareStatement("SELECT COUNT(*) FROM `likelion-db`.users");
        };
         */
    }
}
