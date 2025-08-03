package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.bapakos.controller.location.Location;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.KostService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class AdminCreateKostController {

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

    private KostService kostService;
    private File selectedImageFile;
    private byte[] imageData;

    @FXML private StackPane dropZoneContainer;
    @FXML private VBox dropZoneContent;
    @FXML private ImageView previewImageView;
    @FXML private Button deleteImageButton;

    // Tambahkan referensi ke MainController
    private AdminMainController mainController;
    private ViewManager viewManager;

    public void setMainController(AdminMainController mainController) {
        this.mainController = mainController;
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setKostService(KostService kostService) {
        this.kostService = kostService;
    }

    @FXML
    public void initialize() {
        setupLocationComboBoxes();
        setupImagePicker();
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

                    Image image = new Image(file.toURI().toString());
                    previewImageView.setImage(image);
                    dropZoneContent.setVisible(false);
                    deleteImageButton.setVisible(true);

                } catch (IOException e) {
                    System.err.println("Gagal membaca file gambar: " + e.getMessage());
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
        String province = provinceComboBox.getValue();
        String city = cityComboBox.getValue();
        String address = addressField.getText();
        String codePos = codePosField.getText();

        StringBuilder locationBuilder = new StringBuilder();
        if (province != null && !province.isEmpty()) locationBuilder.append(province);
        if (city != null && !city.isEmpty()) locationBuilder.append(", ").append(city);
        if (address != null && !address.trim().isEmpty()) locationBuilder.append(", ").append(address.trim());
        if (codePos != null && !codePos.trim().isEmpty()) locationBuilder.append(", ").append(codePos.trim());

        return locationBuilder.toString();
    }

    public void handleCreate() {
        try {
            String name = nameField.getText();
            int price = Integer.parseInt(priceField.getText() == "" ? "0" : priceField.getText());
            String description = descriptionField.getText();
            String address = getFormattedLocation();

            System.out.println("Name: " + name);
            System.out.println("Price: " + price);
            System.out.println("Description: " + description);
            System.out.println("Address: " + address);

            if(name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal dibuat");
                alert.setHeaderText(null);
                alert.setContentText("Nama Kost tidak boleh kosong");
                alert.showAndWait();
                return;
            }

            if (price <= 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal dibuat");
                alert.setHeaderText(null);
                alert.setContentText("Harga tidak boleh kosong");
                alert.showAndWait();
                return;
            }

            if(price < 100000) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal dibuat");
                alert.setHeaderText(null);
                alert.setContentText("Harga Kost tidak boleh dibawah Rp 100.000,00");
                alert.showAndWait();
                return;
            }

            if (address.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal dibuat");
                alert.setHeaderText(null);
                alert.setContentText("Alamat tidak boleh kosong");
                alert.showAndWait();
                return;
            }

            KostEntity kost = new KostEntity();
            kost.setName(name);
            kost.setPrice(price);
            kost.setDescription(description);
            kost.setLocation(address);
            kost.setImage(imageData);

            Response result = kostService.create(kost);

            if (result.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kost Berhasil Dibuat");
                alert.setHeaderText(null);
                alert.setContentText("Kost berhasil dibuat");
                alert.showAndWait();

                // Kembali ke dashboard setelah berhasil
                if (viewManager != null) {
                    viewManager.adminScene();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal dibuat");
                alert.setHeaderText(null);
                alert.setContentText(result.getMessage());
                alert.showAndWait();
            }
            return;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleCancel() {
        // Kembali ke dashboard saat tombol batal ditekan
        if (mainController != null) {
            mainController.loadPage("admin-dashboard.fxml");
        }
    }
}
