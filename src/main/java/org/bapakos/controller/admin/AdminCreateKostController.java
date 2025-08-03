package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.bapakos.controller.location.Location;
import org.bapakos.dao.KostDao;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.KostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class AdminCreateKostController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<String> provinceComboBox;
    @FXML
    private ComboBox<String> cityComboBox;
    @FXML
    private TextField codePosField;
    @FXML
    private TextArea addressField;
    @FXML
    private CheckBox acCheckBox;
    @FXML
    private CheckBox kmlCheckBox;
    @FXML
    private CheckBox kmdCheckBox;
    @FXML
    private CheckBox wifiCheckBox;
    private KostService kostService;
    private File selectedImageFile;
    private byte[] imageData;

    // UI requirement dont delete it
    @FXML
    private StackPane dropZoneContainer;
    @FXML
    private VBox dropZoneContent;
    @FXML
    private ImageView previewImageView;
    @FXML
    private Button deleteImageButton;


    public AdminCreateKostController() {}

    public void setKostService(KostService kostService) {
        this.kostService = kostService;
    }

    @FXML
    public void initialize() {
        setupLocationComboBoxes();
        setupImagePicker();
    }

    private void setupImagePicker() {
        // Tambahkan event handler saat area gambar diklik
        dropZoneContainer.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Gambar Kos");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File file = fileChooser.showOpenDialog(dropZoneContainer.getScene().getWindow());

            if (file != null) {
                try {
                    // --- PERUBAHAN UTAMA DI SINI ---
                    // 1. Simpan referensi file
                    selectedImageFile = file;

                    // 2. Baca file gambar menjadi byte array dan simpan di field imageData
                    this.imageData = Files.readAllBytes(file.toPath());

                    // 3. Tampilkan pratinjau gambar
                    Image image = new Image(file.toURI().toString());
                    previewImageView.setImage(image);

                    // 4. Atur tampilan UI
                    dropZoneContent.setVisible(false);
                    deleteImageButton.setVisible(true);

                } catch (IOException e) {
                    System.err.println("Gagal membaca file gambar: " + e.getMessage());
                    // Tampilkan alert error kepada pengguna jika perlu
                    e.printStackTrace();
                }
            }
        });

        // Tambahkan event handler untuk tombol hapus gambar
        deleteImageButton.setOnAction(event -> {
            // Kosongkan semua data gambar
            selectedImageFile = null;
            this.imageData = null; // Kosongkan juga byte array-nya
            previewImageView.setImage(null);

            // Kembalikan ke tampilan awal
            dropZoneContent.setVisible(true);
            deleteImageButton.setVisible(false);
        });
    }

    private void setupLocationComboBoxes() {
        provinceComboBox.setItems(Location.getProvinsiJawa());

        cityComboBox.setDisable(true);

        provinceComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue == null) {
                cityComboBox.setDisable(true);
                cityComboBox.getItems().clear();
                cityComboBox.setPromptText("Pilih Kota/Kabupaten");
            } else {
                cityComboBox.setDisable(false);
                cityComboBox.setItems(Location.getKabupatenPerProvinsi().get(newValue));
                cityComboBox.getSelectionModel().clearSelection();
                cityComboBox.setPromptText("Pilih Kota/Kabupaten");
            }
        });
    }

    public String getFormattedLocation() {
        // Mengambil nilai dari setiap komponen UI
        String province = provinceComboBox.getValue();
        String city = cityComboBox.getValue();
        String address = addressField.getText();
        String codePos = codePosField.getText();

        // Menggunakan StringBuilder untuk efisiensi
        StringBuilder locationBuilder = new StringBuilder();

        // Menambahkan setiap bagian jika tidak kosong
        if (province != null && !province.isEmpty()) {
            locationBuilder.append(province);
        }

        if (city != null && !city.isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(city);
        }
        if (address != null && !address.trim().isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(address.trim());
        }

        if (codePos != null && !codePos.trim().isEmpty()) {
            if (locationBuilder.length() > 0) {
                locationBuilder.append(", ");
            }
            locationBuilder.append(codePos.trim());
        }

        return locationBuilder.toString();
    }

    public void handleCreate() throws IOException {
        try {
            String name = nameField.getText();
            int price = Integer.valueOf(priceField.getText());
            String description = descriptionField.getText();
            String address = this.getFormattedLocation();

            System.out.println("Name: " + name);
            System.out.println("Price: " + price);
            System.out.println("Description: " + description);
            System.out.println("Address: " + address);

            if(name.isEmpty() || description.isEmpty() || address.isEmpty()) {

            }

            if(price < 0) {

            }

            KostEntity kost = new KostEntity();
            kost.setName(name);
            kost.setPrice(price);
            kost.setDescription(description);
            kost.setLocation(address);
            kost.setImage(imageData);

            Response result = kostService.create(kost);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCancel() throws IOException {

    }

}
