package com.example.tobyspring.strategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
    PreparedStatement makePs(Connection c) throws SQLException;
}
