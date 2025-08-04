package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.bapakos.controller.location.Location;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.CreateKostDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.FacilityEntity;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.KostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AdminEditKostController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> provinceComboBox;
    @FXML private ComboBox<String> cityComboBox;
    @FXML private TextField codePosField;
    @FXML private TextArea addressField;
    @FXML private CheckBox acCheckBox;
    @FXML private CheckBox kmlCheckBox;
    @FXML private CheckBox kmdCheckBox;
    @FXML private CheckBox wifiCheckBox;

    @FXML private StackPane dropZoneContainer;
    @FXML private VBox dropZoneContent;
    @FXML private ImageView previewImageView;
    @FXML private Button deleteImageButton;

    private File selectedImageFile;
    private byte[] imageData;

    private KostService kostService;
    private ServiceManager serviceManager;
    private ViewManager viewManager;
    private AdminMainController mainController;
    private KostEntity kostToEdit;

    public void setKostService(KostService kostService) {
        this.kostService = kostService;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setMainController(AdminMainController controller) {
        this.mainController = controller;
    }

    public void setKostToEdit(KostEntity kost) {
        this.kostToEdit = kost;

        // Prefill form
        nameField.setText(kost.getName());
        priceField.setText(String.valueOf(kost.getPrice()));
        descriptionField.setText(kost.getDescription());

        // Parsing lokasi
        if (kost.getLocation() != null) {
            String[] parts = kost.getLocation().split(", ");
            if (parts.length >= 1) provinceComboBox.setValue(parts[0]);
            if (parts.length >= 2) cityComboBox.setValue(parts[1]);
            if (parts.length >= 3) addressField.setText(parts[2]);
            if (parts.length >= 4) codePosField.setText(parts[3]);
        }

        acCheckBox.setDisable(true);
        kmlCheckBox.setDisable(true);
        kmdCheckBox.setDisable(true);
        wifiCheckBox.setDisable(true);

        // GambarA
        if (kost.getImage() != null) {
            Image image = new Image(new java.io.ByteArrayInputStream(kost.getImage()));
            previewImageView.setImage(image);
            dropZoneContent.setVisible(false);
            deleteImageButton.setVisible(true);
            imageData = kost.getImage(); // backup jika tidak diganti
        }
    }

    @FXML
    public void initialize() {
        setupLocationComboBoxes();
        setupImagePicker();
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

    private void setupImagePicker() {
        dropZoneContainer.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Gambar Kos");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(dropZoneContainer.getScene().getWindow());
            if (file != null) {
                try {
                    selectedImageFile = file;
                    this.imageData = Files.readAllBytes(file.toPath());
                    previewImageView.setImage(new Image(file.toURI().toString()));
                    dropZoneContent.setVisible(false);
                    deleteImageButton.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        deleteImageButton.setOnAction(event -> {
            selectedImageFile = null;
            this.imageData = null;
            previewImageView.setImage(null);
            dropZoneContent.setVisible(true);
            deleteImageButton.setVisible(false);
        });
    }

    public void setKostId(String kostId) {
        try {
            // Ambil data kost dari service
            this.kostToEdit = kostService.findById(kostId); // pastikan ada method ini

            if (kostToEdit == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Data Tidak Ditemukan");
                alert.setHeaderText(null);
                alert.setContentText("Kost dengan ID " + kostId + " tidak ditemukan.");
                alert.showAndWait();
                return;
            }

            // Set data ke field
            nameField.setText(kostToEdit.getName());
            priceField.setText(String.valueOf(kostToEdit.getPrice()));
            descriptionField.setText(kostToEdit.getDescription());

            // Lokasi parsing
            if (kostToEdit.getLocation() != null) {
                String[] parts = kostToEdit.getLocation().split(", ");
                if (parts.length >= 1) provinceComboBox.setValue(parts[0]);
                if (parts.length >= 2) cityComboBox.setValue(parts[1]);
                if (parts.length >= 3) addressField.setText(parts[2]);
                if (parts.length >= 4) codePosField.setText(parts[3]);
            }

            // Fasilitas tidak bisa diedit
            acCheckBox.setDisable(true);
            kmlCheckBox.setDisable(true);
            kmdCheckBox.setDisable(true);
            wifiCheckBox.setDisable(true);

            // Gambar
            if (kostToEdit.getImage() != null) {
                Image image = new Image(new java.io.ByteArrayInputStream(kostToEdit.getImage()));
                previewImageView.setImage(image);
                dropZoneContent.setVisible(false);
                deleteImageButton.setVisible(true);
                imageData = kostToEdit.getImage();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Kesalahan");
            alert.setHeaderText(null);
            alert.setContentText("Terjadi kesalahan saat memuat data kost.");
            alert.showAndWait();
        }
    }


    private String getFormattedLocation() {
        StringBuilder sb = new StringBuilder();
        if (provinceComboBox.getValue() != null) sb.append(provinceComboBox.getValue());
        if (cityComboBox.getValue() != null) sb.append(", ").append(cityComboBox.getValue());
        if (!addressField.getText().isEmpty()) sb.append(", ").append(addressField.getText().trim());
        if (!codePosField.getText().isEmpty()) sb.append(", ").append(codePosField.getText().trim());
        return sb.toString();
    }

    public void handleCancel() {
        if (mainController != null) {
            mainController.loadPage("admin-dashboard.fxml");
        }
    }

    @FXML
    public void handleSave() {
        // Validasi input sederhana (bisa dikembangkan)
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validasi", null, "Nama dan harga harus diisi.");
            return;
        }

        try {
            // Update data kostToEdit dengan data baru
            kostToEdit.setName(nameField.getText().trim());
            kostToEdit.setPrice(Integer.parseInt(priceField.getText().trim()));
            kostToEdit.setDescription(descriptionField.getText().trim());
            kostToEdit.setLocation(getFormattedLocation());

            if (imageData != null) {
                kostToEdit.setImage(imageData);
            }

            // Kirim ke database (asumsikan ada method update di service)
            Response response = kostService.updateById(kostToEdit);

            if (response.isSuccess()) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Data kost berhasil diperbarui.");

                // Kembali ke halaman dashboard
                if (mainController != null) {
                    mainController.loadPage("admin-dashboard.fxml");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Gagal", null, "Gagal memperbarui data: " + response.getMessage());
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Kesalahan Format", null, "Harga harus berupa angka.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Kesalahan", null, "Terjadi kesalahan saat menyimpan data.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



}
