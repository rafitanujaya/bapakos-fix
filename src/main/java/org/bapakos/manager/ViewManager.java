package org.bapakos.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bapakos.controller.RegisterController;

import java.io.IOException;

public class ViewManager {
    private final Stage primaryStage;
    private final ServiceManager serviceManager;

    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.serviceManager = new ServiceManager();
    }

    public void show () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
        Parent root = loader.load();

        RegisterController controller = loader.getController();
        controller.setAuthService(this.serviceManager.getAuthService());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/register.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Daftar Akun");
        primaryStage.show();
    }

}
