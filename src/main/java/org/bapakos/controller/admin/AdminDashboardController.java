package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.entity.KostEntity;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardController {

    @FXML private VBox mainContent;
    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;
    @FXML private Button createButton;
    @FXML private VBox rowsContainer;


    // Variabel untuk menyimpan referensi ke controller utama
    private AdminMainController mainController;
    private ServiceManager serviceManager;
    private ViewManager viewManager;

    /**
     * Metode ini akan dipanggil oleh AdminMainController untuk memberikan referensinya.
     */
    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
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

//    public void loadKostTable() {
//        try {
//            // Ambil data dari service
//            List<KostEntity> listKos = serviceManager.getKostService().findAllByOwnerId(ownerId);
//
//            // Bersihkan kontainer terlebih dahulu
//            rowsContainer.getChildren().clear();
//
//            int index = 1;
//            for (KostEntity kost : listKos) {
//                HBox row = new HBox(10);
//                row.getStyleClass().add("kos-table-row");
//                row.setPadding(new Insets(10, 15, 10, 15));
//
//                Label noLabel = new Label(String.valueOf(index++));
//                noLabel.setPrefWidth(60);
//
//                Label namaLabel = new Label(kost.getNama());
//                namaLabel.setPrefWidth(240);
//
//                Label alamatLabel = new Label(kost.getAlamat());
//                alamatLabel.setPrefWidth(330);
//
//                Label hargaLabel = new Label("Rp " + String.format("%,.0f", kost.getHarga()));
//                hargaLabel.setPrefWidth(175);
//
//                Button aksiBtn = new Button("Lihat");
//                aksiBtn.setPrefWidth(80);
//
//                row.getChildren().addAll(noLabel, namaLabel, alamatLabel, hargaLabel, aksiBtn);
//
//                rowsContainer.getChildren().add(row);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
