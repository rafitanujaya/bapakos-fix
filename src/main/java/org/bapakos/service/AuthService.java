package org.bapakos.service;

import org.bapakos.model.entity.UserEntity;

import java.sql.SQLException;

public interface AuthService {
    public boolean register(String username, String password, UserEntity.Role role) throws SQLException;
    public boolean login(String username, String password) throws SQLException;
}
