package org.bapakos.service;

import org.bapakos.dao.UserDao;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.util.PasswordUtil;

import java.sql.SQLException;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    private UserDao userDao;

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public boolean register(String username, String password, UserEntity.Role role) throws SQLException {
        if(this.userDao.usernameExists(username)) {
            return false;
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRole(role);

        return this.userDao.create(user);
    }

    @Override
    public boolean login(String username, String password) throws SQLException {
        return false;
    }
}
