package org.bapakos.manager;

import org.bapakos.config.DBConfig;
import org.bapakos.controller.RegisterController;
import org.bapakos.dao.UserDao;
import org.bapakos.dao.UserDaoImpl;
import org.bapakos.service.AuthService;
import org.bapakos.service.AuthServiceImpl;

import java.sql.Connection;

public class ServiceManager {
    private Connection conn;
    private UserDao userDao;
    private AuthService authService;

    public ServiceManager() {
        try {
            this.conn = new DBConfig().getConnector();
            this.userDao = new UserDaoImpl(this.conn);
            this.authService = new AuthServiceImpl(this.userDao);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal inisialisasi ControllerContainer", e);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
}

