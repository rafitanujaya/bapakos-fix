package org.bapakos;

import javafx.application.Application;
import javafx.stage.Stage;
import org.bapakos.manager.ViewManager;

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