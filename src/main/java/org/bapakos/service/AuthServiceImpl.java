package org.bapakos.service;

import org.bapakos.dao.UserDao;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.session.Session;
import org.bapakos.util.PasswordUtil;

import java.sql.SQLException;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    private UserDao userDao;

    public AuthServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public Response register(String username, String password, UserEntity.Role role) throws SQLException {
        if(this.userDao.usernameExists(username)) {
            return new Response(false, "Username sudah digunakan");
        }
        UserEntity user = new UserEntity();
        user.setId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(PasswordUtil.hashPassword(password));
        user.setRole(role);

        this.userDao.create(user);
        return new Response(true, "Pendaftaran Akun Berhasil Dilakukan");
    }

    @Override
    public Response login(String username, String password) throws SQLException {
        UserEntity user = userDao.findByUsername(username);
        if(user == null) {
            return new Response(false, "Username atau Password salah");
        }
        if(!PasswordUtil.checkPassword(password, user.getPassword())) {
            return new Response(false, "Username atau Password salah");
        }
        Session.set(user);
        return new Response(true, "Login Berhasil Dilakukan");
    }
}
