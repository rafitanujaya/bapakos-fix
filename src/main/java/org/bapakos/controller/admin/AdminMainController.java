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
import org.bapakos.manager.ViewManager;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {

    // Deklarasi semua komponen dari FXML yang butuh interaksi
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

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    /**
     * Metode ini dijalankan secara otomatis setelah FXML dimuat.
     * Digunakan untuk setup awal.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set nama profil (bisa diambil dari data login)
        profileNameLabel.setText("Admin BapaKos");

        // Muat halaman dashboard sebagai tampilan awal
        loadPage("admin-dashboard.fxml");

        // Menambahkan aksi untuk setiap tombol menu
        dashboardBtn.setOnAction(event -> loadPage("admin-dashboard.fxml"));
        transactionBtn.setOnAction(event -> loadPage("admin-transaction.fxml"));
        bookingBtn.setOnAction(event -> loadPage("admin-booking.fxml"));
        logoutBtn.setOnAction(event -> handleLogout());
    }

    /**
     * Metode helper untuk memuat file FXML ke bagian tengah (center) dari BorderPane.
     * @param fxmlFileName Nama file FXML yang akan dimuat (misal: "AdminDashboardView.fxml").
     */
    // Di dalam file AdminMainController.java

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

            // --- BAGIAN BARU (LANGKAH 2) ---
            // Dapatkan controller dari halaman yang baru dimuat
            Object loadedController = loader.getController();

            // Cek apakah controller tersebut adalah AdminDashboardController
            if (loadedController instanceof AdminDashboardController) {
                // Jika ya, berikan referensi controller utama ini (this) kepadanya
                ((AdminDashboardController) loadedController).setMainController(this);
            }
            // Anda bisa menambahkan 'else if' untuk controller lain di sini jika perlu
            // else if (loadedController instanceof TransaksiMenuController) {
            //     ((TransaksiMenuController) loadedController).setMainController(this);
            // }
            // ---------------------------------

            root.setCenter(page);

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman: " + fxmlFileName);
            e.printStackTrace();
        }
    }

    /**
     * Menangani aksi logout.
     * Menutup jendela admin dan membuka kembali jendela login.
     */
    private void handleLogout() {
        try {
            // Dapatkan stage (jendela) saat ini
            Stage currentStage = (Stage) root.getScene().getWindow();

            // Muat FXML untuk halaman login
            // Ganti "/path/to/your/LoginView.fxml" dengan path FXML login Anda
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
            Parent loginRoot = loader.load();
            Scene loginScene = new Scene(loginRoot);

            loginScene.getStylesheets().add("css/login.css");

            // Buat stage baru untuk login
            Stage loginStage = new Stage();
            loginStage.setTitle("BapaKos - Login");
            loginStage.setScene(loginScene);
            loginStage.show();

            // Tutup stage admin
            currentStage.close();

        } catch (IOException e) {
            System.err.println("Gagal memuat halaman login.");
            e.printStackTrace();
        }
    }
}

