package org.bapakos.dao;

import org.bapakos.model.entity.UserEntity;

import java.sql.SQLException;

public interface UserDao {
    public boolean create(UserEntity user) throws SQLException;
    public boolean usernameExists(String username) throws SQLException;
    public UserEntity findByUsername(String username) throws SQLException;
}
