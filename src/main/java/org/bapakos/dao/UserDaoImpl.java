package org.bapakos.dao;

import org.bapakos.model.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    private Connection conn;

    public UserDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean create(UserEntity user) throws SQLException {
        String query = "INSERT INTO users(id, username, password, roles) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole().toString());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean usernameExists(String username) throws SQLException {
        String query = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            return ps.executeQuery().next();
        }
    }

    @Override
    public UserEntity findByUsername(String username) throws SQLException {
        String query = "SELECT id, username, password, role, created_at FROM users WHERE username = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserEntity user = new UserEntity();
                user.setId(rs.getString("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(UserEntity.Role.valueOf(rs.getString("roles")));
                return user;
            }
        }
        return null;
    }
}
