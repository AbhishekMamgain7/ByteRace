package com.example.byterace;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("signup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("ByteRace");
        stage.setWidth(800);  // Set width
        stage.setHeight(600); // Set height

        // Optionally, you can set the minimum size
        stage.setMinWidth(400);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
