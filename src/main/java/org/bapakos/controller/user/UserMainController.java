package org.bapakos.controller.user; // Sesuaikan dengan path paket Anda

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager; // Pastikan import ini benar
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.BookingService;
import org.bapakos.service.KostService;
import org.bapakos.session.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserMainController {

    @FXML
    private BorderPane rootPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button paymentButton;
    @FXML
    private Button notificationButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField searchField;
    @FXML
    private Button refreshButton;

    private ViewManager viewManager;
    private ServiceManager serviceManager;
    private UserDashboardController userDashboardController;
    private BookingService bookingService;
    private KostService kostService;

    public void setViewManager(ViewManager viewManager) { this.viewManager = viewManager; }
    public void setServiceManager(ServiceManager serviceManager) { this.serviceManager = serviceManager; }
    public void setKostService(KostService kostService) { this.kostService = kostService; }
    public void setUserDashboardController(UserDashboardController controller) { this.userDashboardController = controller; }
    public void setBookingService(BookingService bookingService) { this.bookingService = bookingService; }

    @FXML
    public void initialize() {
        // Atur aksi untuk tombol-tombol utama
        homeButton.setOnAction(event -> loadPage("user-dashboard.fxml"));
        logoutButton.setOnAction(event -> handleLogout());
    }

    public void initAfterInject() {
        loadPage("user-dashboard.fxml");
    }

    /**
     * Metode helper untuk memuat file FXML ke bagian tengah (center) dari BorderPane.
     * @param fxmlFileName Nama file FXML yang akan dimuat.
     */
    public void loadPage(String fxmlFileName) {
        try {
            // Pastikan path ke view user sudah benar
            String fxmlPath = "/view/" + fxmlFileName;
            URL resourceUrl = getClass().getResource(fxmlPath);

            if (resourceUrl == null) {
                System.err.println("Error: File FXML tidak ditemukan di path: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent page = loader.load();


             Object loadedController = loader.getController();
             if (loadedController instanceof UserDashboardController c) {
                 c.setMainController(this);
                 c.setViewManager(viewManager);
                 c.setBookingService(bookingService);
                 c.setServiceManager(serviceManager);
                 c.initAfterInject(serviceManager);

                 this.userDashboardController = c;
             } else if (loadedController instanceof DetailPopupController c) {
                 c.setBookingService(bookingService);
                 c.setViewManager(viewManager);
                 c.setServiceManager(serviceManager);
             }

            rootPane.setCenter(page);

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    /**
     * Menangani aksi logout, kembali ke halaman login.
     */
    private void handleLogout() {
        try {
            // Dapatkan stage saat ini
            Stage currentStage = (Stage) rootPane.getScene().getWindow();
            Session.clear();
            // Muat ulang scene login menggunakan ViewManager jika ada,
            // atau muat manual jika tidak ada.
            if (viewManager != null) {
                viewManager.loginScene();
            } else {
                // Fallback jika ViewManager tidak di-set
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
                Parent loginRoot = loader.load();
                Scene loginScene = new Scene(loginRoot);
                loginScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/login.css")).toExternalForm());
                currentStage.setScene(loginScene);
                currentStage.setTitle("Masuk Akun");
            }
        } catch (IOException e) {
            System.err.println("Gagal kembali ke halaman login.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearchButtonClick() {
        String keyword = searchField.getText().trim();

        try {
            List<KostEntity> results = keyword.isEmpty()
                    ? serviceManager.getKostService().findAll()
                    : serviceManager.getKostService().findByKeyword(keyword);

            if (userDashboardController != null) {
                userDashboardController.showKostCards(results);
            } else {
                System.out.println("UserDashboardController belum dimuat");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRefreshButtonClick() {
        userDashboardController.loadPage();
    }


}
