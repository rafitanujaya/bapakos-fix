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
    private BookingService bookingService;

    @FXML
    private VBox bookingListVBox;

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void loadBookingsForOwner(String ownerId) {
        try {
            List<FindBookingByOwnerDto> bookings = bookingService.findAllByOwnerKost(ownerId);
            bookingListVBox.getChildren().clear();

            for (FindBookingByOwnerDto booking : bookings) {
                VBox row = new VBox(5);

                Label kosLabel = new Label("Kos: " + booking.getName());
                Label userLabel = new Label("User: " + booking.getUsername());
                Label statusLabel = new Label("Status: " + booking.getStatus().toString());

                HBox buttonBox = new HBox(10);

                if (booking.getStatus() == FindBookingByOwnerDto.Status.pending) {
                    Button approveButton = new Button("Approve");
                    Button rejectButton = new Button("Reject");

                    approveButton.setOnAction(e -> handleStatusUpdate(
                            booking.getId(),
                            booking.getKostId(),
                            BookingEntity.Status.approve
                    ));

                    rejectButton.setOnAction(e -> handleStatusUpdate(
                            booking.getId(),
                            booking.getKostId(),
                            BookingEntity.Status.reject
                    ));

                    buttonBox.getChildren().addAll(approveButton, rejectButton);
                }

                row.getChildren().addAll(kosLabel, userLabel, statusLabel, buttonBox);
                row.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1;");
                bookingListVBox.getChildren().add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", null, "Gagal memuat data booking.");
        }
    }

    private void handleStatusUpdate(String bookingId, String kostId ,BookingEntity.Status status) {
        try {
            Response response = bookingService.update(bookingId, kostId, status);
            if (response.isSuccess()) {
                loadBookingsForOwner(Session.get().getId()); // reload ulang data
            } else {
                showAlert(Alert.AlertType.WARNING, "Gagal", null, "Gagal memperbarui status.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", null, "Terjadi kesalahan.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setKostEntity(KostEntity kostEntity) {
    }

    public void setBookingEntity(BookingEntity bookingEntity) {
    }
}
