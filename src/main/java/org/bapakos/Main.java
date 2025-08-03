package org.bapakos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
        Parent root = loader.load();

        // Buat scene dan set title
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style/register.css").toExternalForm());
        System.out.println(getClass().getResource("/image/banner.png"));
        primaryStage.setTitle("Register Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}