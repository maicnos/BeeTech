package com.app.projetoBeeTech;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BeeTechApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BeeTechApp.class.getResource("loginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 691, 469);
        stage.setTitle("BeeTECH - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}