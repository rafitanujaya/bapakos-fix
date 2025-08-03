package org.bapakos.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.service.AuthService;

import java.sql.SQLException;

public class RegisterController {
    @FXML
    private Text loginLink;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private CheckBox roleCheckbox;
    private AuthService authService;

    public RegisterController() {}

    public void setAuthService( AuthService authService ) {
        this.authService = authService;
    }


    @FXML
    public void initialize() {
        loginLink.setOnMouseEntered(event -> loginLink.setCursor(Cursor.HAND));
    }

    public void handleRegister() throws SQLException {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean role = roleCheckbox.isSelected();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Username atau Password tidak boleh kosong");
                alert.showAndWait();
                return;
            }

            if (password.length() < 6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Password minimal 6 characters");
                alert.showAndWait();
                return;
            }

            String userRole = role ? "penyewa" : "pemilik";

            Response result = authService.register(username, password, UserEntity.Role.valueOf(userRole));

            if (result.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pendaftaran Berhasil");
                alert.setHeaderText(null);
                alert.setContentText(result.getMessage());
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Gagal");
                alert.setHeaderText(null);
                alert.setContentText(result.getMessage());
                alert.showAndWait();
            }
            System.out.println("Register: " + result);
            return;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
