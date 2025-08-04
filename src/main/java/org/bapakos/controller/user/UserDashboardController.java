package org.bapakos.controller.user;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.BookingService;
import org.bapakos.service.KostService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class UserDashboardController {
    @FXML
    private FlowPane kosListContainer;
    private ViewManager viewManager;
    private ServiceManager serviceManager;
    private KostService kostService;
    private UserMainController mainController;
    private BookingService bookingService;


    public void setMainController(UserMainController mainController) { this.mainController = mainController; }
    public void setViewManager(ViewManager viewManager) { this.viewManager = viewManager; }
    public void setServiceManager(ServiceManager serviceManager) { this.serviceManager = serviceManager; }
    public void setKostService(KostService kostService) { this.kostService = kostService; }
    public void setBookingService(BookingService bookingService) { this.bookingService = bookingService; }

    @FXML
    public void initialize() {}

    public void initAfterInject(ServiceManager serviceManager) {
        System.out.println("ServiceManager: " + serviceManager);
        if (serviceManager != null) {
            loadPage();
        } else {
            System.out.println("ServiceManager belum di-set!");
        }
    }

    public void loadPage() {
        try {
            List<KostEntity> daftarKos = serviceManager.getKostService().findAll();
            kosListContainer.getChildren().clear();

            for (KostEntity kos : daftarKos) {
                Node card = createKosCard(kos);
                kosListContainer.getChildren().add(card);
                card.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/kos-detail-popup.fxml"));
                        ScrollPane popupContent = loader.load();

                        DetailPopupController controller = loader.getController();
                        controller.setBookingService(bookingService);
                        controller.setServiceManager(serviceManager);
                        controller.setViewManager(viewManager);
                        controller.setKos(kos); // isi data detail

                        // Tampilkan di dalam dialog atau popup window
                        Stage popupStage = new Stage();
                        popupStage.setTitle("Detail Kos");
                        popupStage.setScene(new Scene(popupContent));
                        popupStage.initModality(Modality.APPLICATION_MODAL);
                        popupStage.showAndWait();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Atau tampilkan alert jika perlu
        }
    }

    public Node createKosCard(KostEntity kos) {
        StackPane card = new StackPane();
        card.getStyleClass().add("kos-card-user");
        card.setPrefSize(300, 290);

        // Buat sudut membulat
        Rectangle clip = new Rectangle(300, 290);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        card.setClip(clip);

        // Gambar kos
        ImageView imageView = new ImageView();
        byte[] imageData = kos.getImage();
        if (imageData != null && imageData.length > 0) {
            imageView.setImage(new Image(new ByteArrayInputStream(imageData)));
        }
        imageView.setFitWidth(300);
        imageView.setFitHeight(290);
        imageView.setPreserveRatio(false);

        // Panel teks di bawah
        VBox textPanel = new VBox(5);
        textPanel.setPadding(new Insets(12));
        textPanel.getStyleClass().add("kos-card-text-panel");

        // Baris 1: Nama kos
        Label nama = new Label(kos.getName());
        nama.getStyleClass().add("kos-card-nama");

        // Baris 2: Lokasi (icon + teks)
        ImageView locationIcon = new ImageView(new Image("/image/location-icon.png")); // Sesuaikan path
        locationIcon.setFitWidth(12);
        locationIcon.setFitHeight(12);
        Label alamat = new Label(kos.getLocation());
        alamat.getStyleClass().add("kos-card-alamat");

        HBox alamatBox = new HBox(5, locationIcon, alamat);
        alamatBox.setAlignment(Pos.CENTER_LEFT);

        // Baris 3: Garis pemisah
        Separator separator = new Separator();

        // Baris 4: Harga
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        Label harga = new Label(formatter.format(kos.getPrice()));
        harga.getStyleClass().add("kos-card-nama");

        Label perBulan = new Label("/perbulan");
        perBulan.getStyleClass().add("kos-card-alamat"); // Boleh pakai gaya sama

        HBox hargaBox = new HBox(5, harga, perBulan);
        hargaBox.setAlignment(Pos.CENTER_LEFT);

        textPanel.getChildren().addAll(nama, alamatBox, separator, hargaBox);

        VBox wrapper = new VBox(textPanel);
        wrapper.setAlignment(Pos.BOTTOM_LEFT);
        wrapper.setPadding(new Insets(10));

        card.getChildren().addAll(imageView, wrapper);
        return card;
    }

    public void showKostCards(List<KostEntity> kostList) {
        kosListContainer.getChildren().clear();
        for (KostEntity kos : kostList) {
            Node card = createKosCard(kos);
            kosListContainer.getChildren().add(card);
        }
    }


}
