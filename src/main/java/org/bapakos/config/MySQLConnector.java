package org.bapakos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnector implements DatabaseConnector {
    private static final String URL = String.format("jdbc:mysql://%s:%s/%s", EnvLoader.get("DB_HOST"), EnvLoader.get("DB_PORT"), EnvLoader.get("DB_NAME"));
    private static final String USER = EnvLoader.get("DB_USER");
    private static final String PASS = EnvLoader.get("DB_PASSWORD");

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
