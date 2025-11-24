package com.example.gpa_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ResultPageController {

    private static final DecimalFormat GPA_FORMAT = new DecimalFormat("0.00");
    private double currentGpa = 0.0;

    @FXML
    private Label gpaLabel;

    @FXML
    private void initialize() {
        gpaLabel.setText(GPA_FORMAT.format(0.0));
    }

    public void setGpa(double gpa) {
        this.currentGpa = gpa;
        if (gpaLabel != null) {
            gpaLabel.setText(GPA_FORMAT.format(gpa));
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("Student");
        dialog.setTitle("Save Result");
        dialog.setHeaderText("Save GPA Result");
        dialog.setContentText("Please enter student name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            if (!name.trim().isEmpty()) {
                saveToDatabase(name, currentGpa);
            }
        });
    }

    private void saveToDatabase(String name, double gpa) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        GpaRecord record = new GpaRecord(name, gpa, timestamp);
        dbHandler.insertRecord(record);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Result saved successfully!");
        alert.showAndWait();
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
