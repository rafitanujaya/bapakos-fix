package org.bapakos.controller.admin; // Sesuaikan dengan path paket Anda

import javafx.beans.value.ChangeListener;
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
        //setupLocationComboBoxes();
        setupImagePicker();
        //setupFormValidationListener();
    }

    /**
     * Mengatur logika untuk ComboBox Provinsi dan Kota/Kabupaten.
     */
//    private void setupLocationComboBoxes() {
//        // Isi ComboBox provinsi dengan data dari kelas Location
//        provinsiComboBox.setItems(Location.getProvinsiJawa());
//
//        // Awalnya, nonaktifkan ComboBox kota
//        kotaComboBox.setDisable(true);
//
//        // Tambahkan listener ke ComboBox provinsi
//        provinsiComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
//            if (newValue == null) {
//                // Jika tidak ada provinsi yang dipilih, nonaktifkan dan kosongkan ComboBox kota
//                kotaComboBox.setDisable(true);
//                kotaComboBox.getItems().clear();
//            } else {
//                // Jika provinsi dipilih, aktifkan ComboBox kota dan isi dengan data yang sesuai
//                kotaComboBox.setDisable(false);
//                kotaComboBox.setItems(Location.getKabupatenPerProvinsi().get(newValue));
//            }
//        });
//    }

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
     * Mengatur listener pada semua input field untuk validasi form.
     */
//    private void setupFormValidationListener() {
//        // Awalnya, nonaktifkan tombol Simpan
//        simpanButton.setDisable(true);
//
//        // Buat satu listener yang akan digunakan oleh semua field
//        ChangeListener<Object> validationListener = (observable, oldValue, newValue) -> {
//            validateForm();
//        };
//
//        // Pasang listener ke setiap properti dari field yang wajib diisi
//        namaKosField.textProperty().addListener(validationListener);
//        hargaKosField.textProperty().addListener(validationListener);
//        deskripsiArea.textProperty().addListener(validationListener);
//        provinsiComboBox.valueProperty().addListener(validationListener);
//        kotaComboBox.valueProperty().addListener(validationListener);
//        kodePosField.textProperty().addListener(validationListener);
//        alamatArea.textProperty().addListener(validationListener);
//        previewImageView.imageProperty().addListener(validationListener);
//    }

    /**
     * Memeriksa apakah semua field yang wajib diisi sudah terisi.
     * Mengaktifkan atau menonaktifkan tombol Simpan berdasarkan hasilnya.
     */
//    private void validateForm() {
//        boolean isNamaKosFilled = !namaKosField.getText().trim().isEmpty();
//        boolean isHargaKosFilled = !hargaKosField.getText().trim().isEmpty();
//        boolean isDeskripsiFilled = !deskripsiArea.getText().trim().isEmpty();
//        boolean isProvinsiSelected = provinsiComboBox.getValue() != null;
//        boolean isKotaSelected = kotaComboBox.getValue() != null;
//        boolean isKodePosFilled = !kodePosField.getText().trim().isEmpty();
//        boolean isAlamatFilled = !alamatArea.getText().trim().isEmpty();
//        boolean isImageSelected = previewImageView.getImage() != null;
//
//        // Tombol Simpan akan aktif jika semua kondisi di atas terpenuhi
//        boolean allFieldsFilled = isNamaKosFilled && isHargaKosFilled && isDeskripsiFilled &&
//                isProvinsiSelected && isKotaSelected && isKodePosFilled &&
//                isAlamatFilled && isImageSelected;
//
//        simpanButton.setDisable(!allFieldsFilled);
//    }

//    @FXML
//    private void handleSimpan() {
//        // Logika untuk menyimpan data ke database
//        System.out.println("Tombol Simpan ditekan!");
//        System.out.println("Nama Kos: " + namaKosField.getText());
//        System.out.println("Gambar: " + selectedImageFile.getAbsolutePath());
//        // ... ambil data lainnya dan proses ...
//    }
//
//    @FXML
//    private void handleBatal() {
//        // Logika untuk membatalkan dan kembali ke halaman sebelumnya
//        System.out.println("Tombol Batal ditekan!");
//        // Contoh: mainController.loadPage("admin-dashboard.fxml");
//    }

//    private void setupLocationComboBoxes() {
//        // 1. Isi ComboBox provinsi dengan data dari kelas Location
//        provinsiComboBox.setItems(Location.getProvinsiJawa());
//
//        // 2. Awalnya, nonaktifkan ComboBox kota
//        kotaComboBox.setDisable(true);
//
//        // 3. Tambahkan listener untuk mendeteksi perubahan pada ComboBox provinsi
//        provinsiComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
//            if (newValue == null) {
//                // Jika tidak ada provinsi yang dipilih (misalnya, pilihan dikosongkan),
//                // nonaktifkan dan kosongkan ComboBox kota.
//                kotaComboBox.setDisable(true);
//                kotaComboBox.getItems().clear();
//                kotaComboBox.setPromptText("Pilih Kota/Kabupaten");
//            } else {
//                // Jika provinsi dipilih, aktifkan ComboBox kota
//                kotaComboBox.setDisable(false);
//                // Isi ComboBox kota dengan daftar yang sesuai dari Map
//                kotaComboBox.setItems(Location.getKabupatenPerProvinsi().get(newValue));
//                // Hapus pilihan sebelumnya agar tidak membingungkan
//                kotaComboBox.getSelectionModel().clearSelection();
//                kotaComboBox.setPromptText("Pilih Kota/Kabupaten");
//            }
//        });
//    }
}
