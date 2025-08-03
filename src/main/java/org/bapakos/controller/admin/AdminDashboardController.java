package org.bapakos.controller.admin;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.controller.admin.AdminCreateKostController;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardController {

    @FXML private VBox mainContent;
    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;
    @FXML private Button createButton;
    @FXML private VBox rowsContainer;


    // Variabel untuk menyimpan referensi ke controller utama
    private AdminMainController mainController;
    private AdminCreateKostController createKostController;
    private ServiceManager serviceManager;

    /**
     * Metode ini akan dipanggil oleh AdminMainController untuk memberikan referensinya.
     */
    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
    // ---------------------------------

    @FXML
    public void initialize() {
        welcomeLabel.setText("Selamat Datang, Admin!");
        createButton.setOnAction(event -> handleCreateKos());
        loadDashboardData();
    }

    /**
     * Menangani aksi ketika tombol "Buat" ditekan.
     */
    private void handleCreateKos() {
        System.out.println("Tombol 'Buat' ditekan. Memuat halaman tambah kos...");

        // --- BAGIAN BARU (LANGKAH 3) ---
        // Pastikan mainController tidak null sebelum digunakan
        if (mainController != null) {
            // Beri perintah ke controller utama untuk memuat halaman create
            mainController.loadPage("admin-create.fxml");

        } else {
            System.err.println("Error: MainController belum di-set.");
        }
        // ---------------------------------
    }

    private void loadDashboardData() {
        rowsContainer.getChildren().clear();
        Label placeholder = new Label("Belum ada data kos yang ditambahkan.");
        placeholder.setStyle("-fx-padding: 20px; -fx-font-size: 14px; -fx-text-fill: #888;");
        rowsContainer.getChildren().add(placeholder);
    }
}
