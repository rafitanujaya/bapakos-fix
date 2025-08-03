package org.bapakos.controller.user;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.bapakos.model.entity.KostEntity;

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

    private KostEntity kos;

    public void setKos(KostEntity kos) {
        this.kos = kos;

        namaKosLabel.setText(kos.getName());
        hargaLabel.setText("Rp " + kos.getPrice());
        alamatLabel.setText(kos.getLocation());
        deskripsiLabel.setText(kos.getDescription());

        if (kos.getImage() != null) {
            Image image = new Image(new java.io.ByteArrayInputStream(kos.getImage()));
            mainImageView.setImage(image);
        }

        // Jika ada fasilitas-fasilitas, bisa ditambahkan di sini
        // fasilitasFlowPane.getChildren().add(new Label("WiFi")); dll

        // Order Button
        orderButton.setOnAction(e -> {
            System.out.println("Order kos: " + kos.getName());
            // Tambahkan logika order di sini
        });
    }
}
