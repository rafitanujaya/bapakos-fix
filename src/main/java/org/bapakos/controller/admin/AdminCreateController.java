package org.bapakos.controller.admin; // Sesuaikan dengan path paket Anda

import org.bapakos.controller.location.Location;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminCreateController implements Initializable {

    // --- Deklarasi Semua Komponen FXML ---
    @FXML private TextField namaKosField;
    @FXML private TextField hargaKosField;
    @FXML private TextArea deskripsiArea;
    @FXML private ComboBox<String> provinsiComboBox;
    @FXML private ComboBox<String> kotaComboBox;
    @FXML private TextField kodePosField;
    @FXML private TextArea alamatArea;
    @FXML private StackPane dropZoneContainer;
    @FXML private VBox dropZoneContent;
    @FXML private ImageView previewImageView;
    @FXML private Button deleteImageButton;
    @FXML private Button simpanButton;
    @FXML private Button batalButton;


    // Checkbox fasilitas (opsional, bisa ditambahkan jika perlu validasi)
    // @FXML private CheckBox acCheckBox;

    private File selectedImageFile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLocationComboBoxes();
        setupImagePicker();
        //setupFormValidationListener();
    }

    private void setupLocationComboBoxes() {
        // 1. Isi ComboBox provinsi dengan data dari kelas Location
        provinsiComboBox.setItems(Location.getProvinsiJawa());

        // 2. Awalnya, nonaktifkan ComboBox kota
        kotaComboBox.setDisable(true);

        // 3. Tambahkan "listener" untuk mendeteksi perubahan pada ComboBox provinsi
        provinsiComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue == null) {
                // Jika tidak ada provinsi yang dipilih, nonaktifkan dan kosongkan ComboBox kota.
                kotaComboBox.setDisable(true);
                kotaComboBox.getItems().clear();
                kotaComboBox.setPromptText("Pilih Kota/Kabupaten");
            } else {
                // Jika provinsi dipilih, aktifkan ComboBox kota
                kotaComboBox.setDisable(false);
                // Isi ComboBox kota dengan daftar yang sesuai dari Map di kelas Location
                kotaComboBox.setItems(Location.getKabupatenPerProvinsi().get(newValue));
                // Hapus pilihan sebelumnya agar tidak membingungkan
                kotaComboBox.getSelectionModel().clearSelection();
                kotaComboBox.setPromptText("Pilih Kota/Kabupaten");
            }
        });
    }

    /**
     * Mengatur logika untuk memilih gambar.
     */
    private void setupImagePicker() {
        // Tambahkan event handler saat area gambar diklik
        dropZoneContainer.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Gambar Kos");
            // Filter agar hanya file gambar yang bisa dipilih
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
            );

            // Tampilkan dialog pembuka file
            File file = fileChooser.showOpenDialog(dropZoneContainer.getScene().getWindow());

            if (file != null) {
                selectedImageFile = file;
                Image image = new Image(file.toURI().toString());
                previewImageView.setImage(image);

                // Tampilkan gambar dan tombol hapus, sembunyikan ikon tambah
                dropZoneContent.setVisible(false);
                deleteImageButton.setVisible(true);
            }
        });

        // Tambahkan event handler untuk tombol hapus gambar
        deleteImageButton.setOnAction(event -> {
            selectedImageFile = null;
            previewImageView.setImage(null);

            // Kembalikan ke tampilan awal
            dropZoneContent.setVisible(true);
            deleteImageButton.setVisible(false);
        });
    }

    /**
     * Menggabungkan data dari panel lokasi menjadi satu String.
     * Format: "Provinsi, Kota/Kabupaten, Alamat Lengkap, Kode Pos"
     * @return String lokasi yang sudah diformat.
     */
    public String getFormattedLocation() {
        // Mengambil nilai dari setiap komponen UI
        String provinsi = provinsiComboBox.getValue();
        String kota = kotaComboBox.getValue();
        String alamat = alamatArea.getText();
        String kodePos = kodePosField.getText();

        // Menggunakan StringBuilder untuk efisiensi
        StringBuilder locationBuilder = new StringBuilder();

        // Menambahkan setiap bagian jika tidak kosong
        if (provinsi != null && !provinsi.isEmpty()) {
            locationBuilder.append(provinsi);
        }

        if (kota != null && !kota.isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(kota);
        }

        if (alamat != null && !alamat.trim().isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(alamat.trim());
        }

        if (kodePos != null && !kodePos.trim().isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(kodePos.trim());
        }

        return locationBuilder.toString();
    }

}
