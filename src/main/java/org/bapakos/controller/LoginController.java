package org.bapakos.controller;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.bapakos.manager.ViewManager;
import org.bapakos.model.dto.Response;
import org.bapakos.model.entity.UserEntity;
import org.bapakos.service.AuthService;
import org.bapakos.session.Session;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private Text registerLink;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private AuthService authService;
    private ViewManager viewManager;

    public LoginController() {}

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    public void setViewManager(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @FXML
    public void initialize() {
        registerLink.setOnMouseEntered(event -> registerLink.setCursor(Cursor.HAND));

        registerLink.setOnMouseClicked(event -> {
            try {
                viewManager.registerScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void handleLogin() throws SQLException {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Username atau Password tidak boleh kosong");
                alert.showAndWait();
                return;
            }

            if (password.length() < 6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText("Password minimal 6 characters");
                alert.showAndWait();
                return;
            }

            Response result = authService.login(username, password);

            if (result.isSuccess()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Berhasil");
                alert.setHeaderText(null);
                alert.setContentText(result.getMessage());
                alert.showAndWait();
                UserEntity user = Session.get();
                System.out.println(user.getRole());
                if(user.getRole().toString().equalsIgnoreCase("pemilik")) {
                    viewManager.adminScene();
                } else {
                    viewManager.userScene();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText(result.getMessage());
                alert.showAndWait();
            }
            return;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
