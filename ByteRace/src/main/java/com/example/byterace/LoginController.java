package com.example.byterace;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button loginButton;

    private final String DB_URL = "jdbc:mysql://localhost:3306/byterace_db";
    private final String DB_USER = "root";
    private final String DB_PASS = "root";

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❗ Please fill all fields");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Check if username and password match
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                messageLabel.setText("✅ Login successful!");
                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard-view.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) loginButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();
            } else {
                messageLabel.setText("❌ Invalid username or password!");
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("❌ Error during login!");
        }
    }
}