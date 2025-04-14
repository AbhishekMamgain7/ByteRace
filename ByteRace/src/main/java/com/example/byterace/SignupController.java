package com.example.byterace;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class SignupController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    private Label messageLabel;

    private final String DB_URL = "jdbc:mysql://localhost:3306/byterace_db";
    private final String DB_USER = "root";
    private final String DB_PASS = "root";

    @FXML
    private void handleSignup(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❗ Please fill all fields");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            // Check if username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                messageLabel.setText("⚠️ Username already taken!");
            } else {
                // Insert new user
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, password);
                stmt.executeUpdate();

                messageLabel.setText("✅ Signup successful! Redirecting to login...");

                // Delay 1 second, then go to login
                PauseTransition delay = new PauseTransition(Duration.seconds(1.5));
                delay.setOnFinished(e -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) signupButton.getScene().getWindow();
                        stage.setScene(new Scene(root));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();
            }

            rs.close();
            checkStmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("❌ Error during signup!");
        }
    }

    @FXML
    private void handleLoginRedirect() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}