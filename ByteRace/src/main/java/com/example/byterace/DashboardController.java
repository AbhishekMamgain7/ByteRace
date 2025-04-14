package com.example.byterace;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DashboardController {

    @FXML
    private Label statusLabel;

    @FXML
    private void handleShare() {
        // Logic for sharing goes here
        statusLabel.setText("✅ Share button clicked!");
        // You can add more functionality here, such as opening a share dialog or performing an action.
    }
}