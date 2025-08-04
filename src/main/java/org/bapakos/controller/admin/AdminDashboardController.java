package org.bapakos.controller.admin;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.controller.admin.AdminCreateKostController;
import org.bapakos.model.dto.KostReportDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.service.KostService;
import org.bapakos.session.Session;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDashboardController {

    @FXML private VBox mainContent;
    @FXML private Label welcomeLabel;
    @FXML private TextField searchField;
    @FXML private Button createButton;
    @FXML private VBox rowsContainer;


    // Variabel untuk menyimpan referensi ke controller utama
    private AdminMainController mainController;
    private KostService kostService;
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
    public void setKostService(KostService kostService) {
        this.kostService = kostService;
    }
    // ---------------------------------

    @FXML
    public void initialize() {
        welcomeLabel.setText("Selamat Datang, Admin!");
        createButton.setOnAction(event -> handleCreateKos());
    }

    public void initAfterInject(ServiceManager serviceManager) {
        System.out.println("ServiceManager: " + serviceManager);
        if (serviceManager != null) {
            loadKostTable();
        } else {
            System.out.println("ServiceManager belum di-set!");
        }
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

    public void loadKostTable() {
        try {
            // Ambil data dari service
            List<KostEntity> listKos = serviceManager.getKostService().findAllByOwnerId(String.valueOf(Session.get().getId()));

            // Bersihkan kontainer terlebih dahulu
            rowsContainer.getChildren().clear();

            if (listKos.isEmpty()) {
                loadDashboardData();
            }

            int index = 1;
            for (KostEntity kost : listKos) {
                HBox row = new HBox(10);
                row.getStyleClass().add("kos-table-row");
                row.setPadding(new Insets(10, 15, 10, 15));

                Label noLabel = new Label(String.valueOf(index++));
                noLabel.setPrefWidth(60);

                Label namaLabel = new Label(kost.getName());
                namaLabel.setPrefWidth(240);

                Label alamatLabel = new Label(kost.getLocation());
                alamatLabel.setPrefWidth(330);

                Label hargaLabel = new Label("Rp " + String.format("%,d", kost.getPrice()));
                hargaLabel.setPrefWidth(175);

                // Tombol Edit
                Button editBtn = new Button("Edit");
                editBtn.getStyleClass().add("button-edit");
                editBtn.setPrefWidth(60);
                editBtn.setOnAction(e -> { handleEdit(kost.getId()); });

                // Tombol Hapus
                Button hapusBtn = new Button("Hapus");
                hapusBtn.getStyleClass().add("button-hapus");
                hapusBtn.setPrefWidth(60);
                hapusBtn.setOnAction(e -> {handleDelete(kost);});


                // HBox untuk tombol aksi
                HBox aksiBox = new HBox(5, editBtn, hapusBtn);
                aksiBox.setPrefWidth(140);

                row.getChildren().addAll(noLabel, namaLabel, alamatLabel, hargaLabel, aksiBox);

                rowsContainer.getChildren().add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(KostEntity kost) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Konfirmasi Hapus");
        alert.setHeaderText("Apakah Anda yakin ingin menghapus kos ini?");
        alert.setContentText("Nama: " + kost.getName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                Response res = serviceManager.getKostService().deleteById(kost.getId());
                if (res.isSuccess()) {
                    Alert info = new Alert(Alert.AlertType.INFORMATION, res.getMessage());
                    info.showAndWait();
                    loadKostTable(); // refresh
                } else {
                    Alert err = new Alert(Alert.AlertType.ERROR, res.getMessage());
                    err.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Alert err = new Alert(Alert.AlertType.ERROR, "Terjadi kesalahan saat menghapus kos.");
                err.showAndWait();
            }
        }
    }

    public void handleEdit(String kostId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin-edit.fxml"));
            Parent root = loader.load();

            AdminEditKostController controller = loader.getController();
            controller.setMainController(mainController);
            controller.setServiceManager(serviceManager);
            controller.setKostService(serviceManager.getKostService());
            controller.setViewManager(viewManager); // this = viewManager
            controller.setKostId(kostId);

            mainController.setContent(root); // misalnya ini menaruh konten ke panel utama
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

