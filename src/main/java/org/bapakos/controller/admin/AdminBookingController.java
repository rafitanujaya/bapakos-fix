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
    private VBox rowsContainer;

    @FXML
    public void initialize() {

    }


    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public void initAfterInject() {
        loadBookingsForOwner(Session.get().getId());
    }

    public void loadBookingsForOwner(String ownerId) {
        try {
            List<FindBookingByOwnerDto> bookings = bookingService.findAllByOwnerKost(ownerId);
            rowsContainer.getChildren().clear();

            for (FindBookingByOwnerDto booking : bookings) {
                HBox row = new HBox(20); // elemen dalam satu baris horizontal
                row.getStyleClass().add("booking-row");
                row.setPrefHeight(40); // opsional: tinggi baris tetap

                Label kosLabel = new Label("Kos: " + booking.getName());
                kosLabel.setPrefWidth(250);
                Label userLabel = new Label("User: " + booking.getUsername());
                userLabel.setPrefWidth(250);
                Label statusLabel = new Label("Status: " + booking.getStatus().toString());
                statusLabel.setPrefWidth(250);

                row.getChildren().addAll(kosLabel, userLabel, statusLabel);

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
                    approveButton.getStyleClass().add("button-approve");
                    rejectButton.getStyleClass().add("button-reject");


                    row.getChildren().addAll(approveButton, rejectButton);
                }

                rowsContainer.getChildren().add(row);
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
