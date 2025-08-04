package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.BookingEntity;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.BookingService;
import org.bapakos.session.Session;

import java.sql.SQLException;
import java.util.List;

public class AdminBookingController {
    private ViewManager viewManager;
    private ServiceManager serviceManager;
    private BookingEntity bookingEntity;
    private KostEntity kostEntity;
    private BookingService bookingService;


    @FXML
    private VBox bookingListVBox;

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
    public void setBookingEntity(BookingEntity bookingEntity) {
        this.bookingEntity = bookingEntity;
    }
    public void setKostEntity(KostEntity kostEntity) {
        this.kostEntity = kostEntity;
    }
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void loadBookingsForOwner(String ownerId) {
        try {
            // Asumsi: bookingService.findAllByOwnerKost() mengembalikan List<FindBookingByOwnerDto>
            List<FindBookingByOwnerDto> bookings = bookingService.findAllByOwnerKost(ownerId);
            bookingListVBox.getChildren().clear();

            if (bookings.isEmpty()) {
                bookingListVBox.getChildren().add(new Label("Tidak ada data booking yang ditemukan."));
                return;
            }

            for (FindBookingByOwnerDto booking : bookings) {
                VBox row = new VBox(5);
                row.setStyle("-fx-padding: 10; -fx-border-color: #E2E8F0; -fx-border-width: 0 0 1px 0;");

                // Asumsi: DTO Anda memiliki metode getName() untuk nama kos
                Label kosLabel = new Label("Nama Kos: " + booking.getName());
                Label userLabel = new Label("Penyewa: " + booking.getUsername());
                Label statusLabel = new Label("Status: " + booking.getStatus().toString());
                statusLabel.setStyle("-fx-font-weight: bold;");

                HBox buttonBox = new HBox(10);

                // Bandingkan dengan enum dari DTO itu sendiri
                if (booking.getStatus() == FindBookingByOwnerDto.Status.pending) {
                    Button approveButton = new Button("Approve");
                    approveButton.setStyle("-fx-background-color: #D1FAE5; -fx-text-fill: #065F46;");

                    Button rejectButton = new Button("Reject");
                    rejectButton.setStyle("-fx-background-color: #FEE2E2; -fx-text-fill: #991B1B;");

                    // PERBAIKAN 1: Tambahkan booking.getKostId() saat memanggil handleStatusUpdate
                    approveButton.setOnAction(e -> handleStatusUpdate(
                            bookingEntity.getId(),
                            bookingEntity.getKostId(), // Asumsi DTO Anda punya getter ini
                            BookingEntity.Status.approve
                    ));

                    rejectButton.setOnAction(e -> handleStatusUpdate(
                            bookingEntity.getId(),
                            bookingEntity.getKostId(), // Asumsi DTO Anda punya getter ini
                            BookingEntity.Status.reject
                    ));

                    buttonBox.getChildren().addAll(approveButton, rejectButton);
                }

                row.getChildren().addAll(kosLabel, userLabel, statusLabel, buttonBox);
                bookingListVBox.getChildren().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", null, "Gagal memuat data booking.");
        }
    }

    /**
     * PERBAIKAN 2: Metode handleStatusUpdate sekarang menerima kostId
     * agar sesuai dengan metode di service layer Anda.
     */
    private void handleStatusUpdate(String bookingId, String kostId, BookingEntity.Status newStatus) {
        try {
            // Panggil metode service 'update' dengan tiga parameter
            Response response = bookingService.update(bookingId, kostId, newStatus);
            if (response.isSuccess()) {
                // Muat ulang data untuk menampilkan perubahan
                loadBookingsForOwner(Session.get().getId());
            } else {
                showAlert(Alert.AlertType.WARNING, "Gagal", null, "Gagal memperbarui status booking: " + response.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", null, "Terjadi kesalahan pada database.");
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
