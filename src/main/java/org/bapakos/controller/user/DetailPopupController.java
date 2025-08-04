package org.bapakos.controller.user;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.service.BookingService;
import org.bapakos.session.Session;

public class DetailPopupController {

    @FXML private ImageView mainImageView;
    @FXML private VBox thumbnailsBox;
    @FXML private Label namaKosLabel;
    @FXML private Label hargaLabel;
    @FXML private Label alamatLabel;
    @FXML private FlowPane fasilitasFlowPane;
    @FXML private Label deskripsiLabel;
    @FXML private ImageView mapImageView;
    @FXML private Button orderButton;

    private BookingService bookingService;
    private ViewManager viewManager;
    private ServiceManager serviceManager;
    private KostEntity kos;

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setKos(KostEntity kos) {
        this.kos = kos;

        namaKosLabel.setText(kos.getName());
        hargaLabel.setText("Rp " + kos.getPrice());
        alamatLabel.setText(kos.getLocation());
        deskripsiLabel.setText(kos.getDescription());

        // Gambar utama
        if (kos.getImage() != null) {
            Image image = new Image(new java.io.ByteArrayInputStream(kos.getImage()));
            mainImageView.setImage(image);

            // Thumbnail sebagai contoh duplikat gambar utama (kalau belum ada list image lain)
            thumbnailsBox.getChildren().clear();
            for (int i = 0; i < 3; i++) {
                ImageView thumb = new ImageView(image);
                thumb.setFitWidth(60);
                thumb.setFitHeight(60);
                thumb.setPreserveRatio(true);
                int finalI = i;
                thumb.setOnMouseClicked(e -> mainImageView.setImage(thumb.getImage()));
                thumbnailsBox.getChildren().add(thumb);
            }
        }

        // Order Button
        orderButton.setOnAction(e -> {
            System.out.println("Order kos: " + kos.getName());
            handleOrderNow();
        });
    }

    public void handleOrderNow() {
        try {
            System.out.println(bookingService);
            System.out.println(kos.getId());
            System.out.println(Session.get());
            if (bookingService == null || kos.getId() == null || Session.get() == null) {
                showAlert(Alert.AlertType.ERROR, "Kesalahan", null, "Data booking tidak lengkap.");
                return;
            }

            Response response = bookingService.create(kos.getId(), Session.get().getId());

            if (response.isSuccess()) {
                showAlert(Alert.AlertType.INFORMATION, "Sukses", null, "Booking berhasil dibuat.");
                // Tutup popup jika mau
                Stage stage = (Stage) orderButton.getScene().getWindow();
                stage.close();
            } else {
                showAlert(Alert.AlertType.WARNING, "Gagal", null, response.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Kesalahan", null, "Terjadi kesalahan saat booking.");
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
