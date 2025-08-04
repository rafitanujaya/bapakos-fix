package org.bapakos.manager;

import org.bapakos.config.DBConfig;
import org.bapakos.controller.RegisterController;
import org.bapakos.dao.*;
import org.bapakos.service.*;

import java.sql.Connection;

public class ServiceManager {
    private Connection conn;
    private UserDao userDao;
    private KostDao kostDao;
    private BookingDao bookingDao;
    private PaymentDao paymentDao;
    private AuthService authService;
    private KostService kostService;
    private BookingService bookingService;
    private PaymentService paymentService;

    public ServiceManager() {
        try {
            this.conn = new DBConfig().getConnector();
            this.userDao = new UserDaoImpl(this.conn);
            this.kostDao = new KostDaoImpl(this.conn);
            this.bookingDao = new BookingDaoImpl(this.conn);
            this.paymentDao = new PaymentDaoImpl(this.conn);
            this.authService = new AuthServiceImpl(this.userDao);
            this.kostService = new KostServiceImpl(this.kostDao);
            this.bookingService = new BookingServiceImpl(this.bookingDao, this.kostDao);
            this.paymentService = new PaymentServiceImpl(this.paymentDao);


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal inisialisasi ControllerContainer", e);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
    public KostService getKostService() { return kostService; }
    public BookingService getBookingService() { return bookingService; }
}

