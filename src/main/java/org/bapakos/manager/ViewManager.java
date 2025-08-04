package org.bapakos.manager;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bapakos.controller.LoginController;
import org.bapakos.controller.RegisterController;
import org.bapakos.controller.admin.AdminCreateKostController;
import org.bapakos.controller.admin.AdminDashboardController;
import org.bapakos.controller.admin.AdminMainController;
import org.bapakos.controller.user.UserMainController;
import org.bapakos.model.entity.BookingEntity;
import org.bapakos.model.entity.KostEntity;
import org.bapakos.service.BookingServiceImpl;

import java.io.IOException;

public class ViewManager {
    private final Stage primaryStage;
    private final ServiceManager serviceManager;


    public ViewManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.serviceManager = new ServiceManager();

    }

    public void registerScene () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
        Parent root = loader.load();

        RegisterController controller = loader.getController();
        controller.setAuthService(this.serviceManager.getAuthService());
        controller.setViewManager(this);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/register.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Daftar Akun");
        primaryStage.show();
    }

    public void loginScene () throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.setAuthService(this.serviceManager.getAuthService());
        controller.setViewManager(this);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Masuk Akun");
        primaryStage.show();
    }

    public void adminScene () throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin-main.fxml"));
            Parent root = loader.load();

            AdminMainController controller = loader.getController();
            controller.setServiceManager(serviceManager);
            controller.setBookingService(this.serviceManager.getBookingService());
            controller.setBookingEntity(new BookingEntity());
            controller.setKostEntity(new KostEntity());

            controller.setViewManager(this);
            controller.initAfterInject();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style/admin.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin");
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void userScene () throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/user-main.fxml"));
            Parent root = loader.load();

            UserMainController controller = loader.getController();
            controller.setViewManager(this);
            controller.setServiceManager(serviceManager);
            controller.setBookingService(serviceManager.getBookingService());
            controller.initAfterInject();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style/user.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("User");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminDashboardScene () throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin-dashboard.fxml"));
            Parent root = loader.load();

            AdminDashboardController controller = loader.getController();
            controller.setServiceManager(serviceManager);
            controller.setViewManager(this);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style/admin.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin");
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void adminEditScene () throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin-edit.fxml"));
            Parent root = loader.load();

            AdminDashboardController controller = loader.getController();
            controller.setServiceManager(serviceManager);
            controller.setViewManager(this);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style/admin.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setTitle("Admin");
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
