package org.bapakos.config;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConfig {
    private final DatabaseConnector connector;

    public DBConfig() {
        String type = EnvLoader.get("DB_TYPE");
        switch (type) {
            case "mysql":
                connector = new MySQLConnector();
                break;
            default:
                throw new IllegalArgumentException("Tipe Database Tidak Didukung: " + type);

        }
    }

    public Connection getConnector() throws SQLException {
        return connector.getConnection();
    }
}
