package org.bapakos.manager;

import org.bapakos.config.DBConfig;
import org.bapakos.controller.RegisterController;
import org.bapakos.dao.KostDao;
import org.bapakos.dao.KostDaoImpl;
import org.bapakos.dao.UserDao;
import org.bapakos.dao.UserDaoImpl;
import org.bapakos.service.AuthService;
import org.bapakos.service.AuthServiceImpl;
import org.bapakos.service.KostService;
import org.bapakos.service.KostServiceImpl;

import java.sql.Connection;

public class ServiceManager {
    private Connection conn;
    private UserDao userDao;
    private KostDao kostDao;
    private AuthService authService;
    private KostService kostService;


    public ServiceManager() {
        try {
            this.conn = new DBConfig().getConnector();
            this.userDao = new UserDaoImpl(this.conn);
            this.kostDao = new KostDaoImpl(this.conn);
            this.authService = new AuthServiceImpl(this.userDao);
            this.kostService = new KostServiceImpl(this.kostDao);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal inisialisasi ControllerContainer", e);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
    public KostService getKostService() { return kostService; }
}

