package org.bapakos.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.bapakos.dao.BookingDao;
import org.bapakos.manager.ServiceManager;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.FindBookingByOwnerDto;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.BookingEntity;
import org.bapakos.service.BookingService;

import java.sql.SQLException;
import java.util.List;

public class AdminTransactionController {

    @FXML
    private VBox rowsContainer;

    private BookingService bookingService;
    private String ownerId;
    private ServiceManager serviceManager;
    private ViewManager viewManager;

    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public AdminTransactionController(BookingService bookingService, String ownerId) {
        this.bookingService = bookingService;
        this.ownerId = ownerId;
    }

    @FXML
    public void initialize() {
        try {
            List<FindBookingByOwnerDto> bookings = bookingService.findAllByOwnerKost(ownerId);
            populateRows(bookings);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateRows(List<FindBookingByOwnerDto> bookings) {
        rowsContainer.getChildren().clear();

        int index = 1;
        for (FindBookingByOwnerDto booking : bookings) {
            HBox row = new HBox();
            row.getStyleClass().add("kos-table-row");

            Label noLabel = new Label(String.valueOf(index++));
            noLabel.setPrefWidth(70);

            Label nameLabel = new Label(booking.getName());
            nameLabel.setPrefWidth(270);

            Label userLabel = new Label(booking.getUsername());
            userLabel.setPrefWidth(370);

            Label statusLabel = new Label(booking.getStatus().name());
            statusLabel.setPrefWidth(100);

            HBox buttonGroup = new HBox(10);
            buttonGroup.setPrefWidth(275);

            if (booking.getStatus() == FindBookingByOwnerDto.Status.pending) {
                Button approveButton = new Button("Approve");
                Button rejectButton = new Button("Reject");

                approveButton.getStyleClass().add("approve-button");
                rejectButton.getStyleClass().add("reject-button");

                approveButton.setOnAction(e -> updateStatus(booking, BookingEntity.Status.approve));
                rejectButton.setOnAction(e -> updateStatus(booking, BookingEntity.Status.reject));

                buttonGroup.getChildren().addAll(approveButton, rejectButton);
            } else {
                Label doneLabel = new Label("✓");
                buttonGroup.getChildren().add(doneLabel);
            }

            row.getChildren().addAll(noLabel, nameLabel, userLabel, buttonGroup, statusLabel);
            rowsContainer.getChildren().add(row);
        }
    }

    private void updateStatus(FindBookingByOwnerDto booking, BookingEntity.Status newStatus) {
        try {
            Response response = bookingService.update(
                    booking.getId(),
                    null,
                    newStatus
            );
            if (response.isSuccess()) {
                initialize(); // refresh data
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
