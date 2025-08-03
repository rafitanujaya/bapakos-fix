package org.bapakos.controller.user; // Sesuaikan dengan path paket Anda

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bapakos.manager.ViewManager; // Pastikan import ini benar

import java.io.IOException;
import java.net.URL;
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

    // Anda bisa menambahkan @FXML untuk komponen filter jika diperlukan
    // @FXML private TextField searchField;
    // @FXML private ComboBox<String> provinsiComboBox;

    private ViewManager viewManager;

    // Metode ini digunakan untuk menerima ViewManager dari LoginController/Main
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @FXML
    public void initialize() {
        // Muat halaman dashboard sebagai tampilan awal
        loadPage("user-dashboard.fxml");

        // Atur aksi untuk tombol-tombol utama
        homeButton.setOnAction(event -> loadPage("user-dashboard.fxml"));
        // paymentButton.setOnAction(event -> loadPage("user-payment.fxml")); // Contoh untuk halaman lain
        logoutButton.setOnAction(event -> handleLogout());
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

            // Penting: Jika halaman konten perlu berkomunikasi balik,
            // Anda bisa memberikan referensi controller ini kepadanya.
            // Object loadedController = loader.getController();
            // if (loadedController instanceof UserDashboardController) {
            //     ((UserDashboardController) loadedController).setMainController(this);
            // }

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
}
