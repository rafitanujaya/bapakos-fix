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

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @FXML
    public void initialize() {
        profileNameLabel.setText("Admin BapaKos");

        loadPage("admin-dashboard.fxml");


        dashboardBtn.setOnAction(event -> loadPage("admin-dashboard.fxml"));
        transactionBtn.setOnAction(event -> loadPage("admin-transaction.fxml"));
        bookingBtn.setOnAction(event -> loadPage("admin-booking.fxml"));
        logoutBtn.setOnAction(event -> handleLogout());
    }

    public void loadPage(String fxmlFileName) {
        try {
            String fxmlPath = "/view/" + fxmlFileName;
            URL resourceUrl = getClass().getResource(fxmlPath);
            if (resourceUrl == null) {
                System.err.println("Error: File FXML tidak ditemukan di path: " + fxmlPath);
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent page = loader.load();

            Object loadedController = loader.getController();

            if (loadedController instanceof AdminDashboardController controller) {
                controller.setMainController(this);
                controller.setServiceManager(serviceManager);
            }

            if (loadedController instanceof AdminCreateKostController) {
                ((AdminCreateKostController) loadedController).setKostService(serviceManager.getKostService());
                ((AdminCreateKostController) loadedController).setViewManager(viewManager);
            }
            // Tambahkan untuk controller lain jika perlu

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
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
            Parent loginRoot = loader.load();

            Scene currentScene = root.getScene();
            currentScene.setRoot(loginRoot);

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman login.");
            e.printStackTrace();
        }
    }
}
