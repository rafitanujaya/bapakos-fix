package org.bapakos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bapakos.config.DBConfig;
import org.bapakos.controller.RegisterController;
import org.bapakos.dao.UserDao;
import org.bapakos.dao.UserDaoImpl;
import org.bapakos.manager.ViewManager;
import org.bapakos.service.AuthService;
import org.bapakos.service.AuthServiceImpl;

import java.sql.Connection;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ViewManager viewManager = new ViewManager(primaryStage);
        viewManager.loginScene();
    }
}