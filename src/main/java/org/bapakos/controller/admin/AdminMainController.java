package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.entity.BookingEntity;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.BookingService;
import org.bapakos.session.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminMainController {

    @FXML
    private BorderPane root;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button transactionBtn;

    @FXML
    private Button bookingBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label profileNameLabel;

    private ViewManager viewManager;
    private ServiceManager serviceManager;
    private BookingService bookingService;
    private BookingEntity bookingEntity;
    private KostEntity kostEntity;

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    public void setServiceManager(ServiceManager serviceManager) {this.serviceManager = serviceManager;}
    public ServiceManager getServiceManager() {return serviceManager;}
    public void setBookingService(BookingService bookingService) {this.bookingService = bookingService;}
    public void setBookingEntity(BookingEntity bookingEntity) { this.bookingEntity = bookingEntity; }
    public void setKostEntity(KostEntity kostEntity) { this.kostEntity = kostEntity; }

    @FXML
    public void initialize() {
        profileNameLabel.setText("Admin BapaKos");

        dashboardBtn.setOnAction(event -> loadPage("admin-dashboard.fxml"));
        transactionBtn.setOnAction(event -> loadPage("admin-transaction.fxml"));
        bookingBtn.setOnAction(event -> loadPage("admin-booking.fxml"));
        logoutBtn.setOnAction(event -> handleLogout());


    }

    public void initAfterInject() {
        loadPage("admin-dashboard.fxml");
    }


    public void loadPage(String fxmlFileName) {
        try {
            String fxmlPath = "/view/" + fxmlFileName;
            URL resourceUrl = getClass().getResource(fxmlPath);
            if (resourceUrl == null) {
                System.err.println("Error: File FXML tidak ditemukan di path: " + fxmlPath);
                return;
            }
            if (viewManager == null) {
                System.out.println("ViewManager tidak ditemukan");
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent page = loader.load();

            Object controller = loader.getController();

            if (controller instanceof AdminDashboardController c) {
                c.setMainController(this);
                c.setViewManager(viewManager);
                c.setServiceManager(serviceManager);
                c.initAfterInject(serviceManager);
            } else if (controller instanceof AdminCreateKostController c) {
                c.setMainController(this);
                c.setKostService(serviceManager.getKostService());
                c.setViewManager(viewManager);
            } else if (controller instanceof AdminTransactionController c) {
                c.setServiceManager(serviceManager);
                c.setViewManager(viewManager);
            } else if (controller instanceof AdminBookingController c) {
                c.setServiceManager(serviceManager);
                c.setViewManager(viewManager);
                c.setBookingService(bookingService);
                c.setKostEntity(kostEntity);
                c.setBookingEntity(bookingEntity);
            } else if (controller instanceof AdminEditKostController c) {
                c.setMainController(this);
                c.setKostService(serviceManager.getKostService());
                c.setViewManager(viewManager);
            }

            root.setCenter(page);

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman: " + fxmlFileName);
            e.printStackTrace();
        }
    }


    /**
     * Mengganti scene saat ini ke login tanpa membuat Stage baru.
     */
    private void handleLogout() {
        try {
            viewManager.loginScene();
            Session.clear();
        } catch (IOException e) {
            System.err.println("Gagal memuat halaman login.");
            e.printStackTrace();
        }
    }

    public void setContent(Parent node) {
        root.setCenter(node);
    }


}
