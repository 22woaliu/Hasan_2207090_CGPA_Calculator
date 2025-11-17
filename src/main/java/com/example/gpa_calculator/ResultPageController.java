package com.example.gpa_calculator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class ResultPageController {

    private static final DecimalFormat GPA_FORMAT = new DecimalFormat("0.00");

    @FXML
    private Label gpaLabel;

    @FXML
    private void initialize() {
        gpaLabel.setText(GPA_FORMAT.format(0.0));
    }

    public void setGpa(double gpa) {
        if (gpaLabel != null) {
            gpaLabel.setText(GPA_FORMAT.format(gpa));
        }
    }
}
