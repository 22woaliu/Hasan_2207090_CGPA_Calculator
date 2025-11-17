package com.example.gpa_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scene2Controller {

    private static final List<String> GRADE_ORDER = List.of(
            "A+","A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "F"
    );

    private final Map<String, Double> GRADE_POINTS = new HashMap<>(){{
        put("A+", 4.0);
        put("A", 3.75);
        put("A-", 3.5);
        put("B+", 3.25);
        put("B", 3.00);
        put("B-", 2.75);
        put("C+", 2.5);
        put("C", 2.25);
        put("C-", 2.00);
        put("D+", 1.75);
        put("D", 1.5);
        put("F", 0.0);
    }};

    @FXML private Spinner<Integer> creditSpinner1;
    @FXML private Spinner<Integer> creditSpinner2;
    @FXML private Spinner<Integer> creditSpinner3;
    @FXML private Spinner<Integer> creditSpinner4;
    @FXML private Spinner<Integer> creditSpinner5;

    @FXML private ComboBox<String> gradeCombo1;
    @FXML private ComboBox<String> gradeCombo2;
    @FXML private ComboBox<String> gradeCombo3;
    @FXML private ComboBox<String> gradeCombo4;
    @FXML private ComboBox<String> gradeCombo5;

    @FXML private Button calculateButton;

    private List<Spinner<Integer>> creditSpinners;
    private List<ComboBox<String>> gradeCombos;

    @FXML
    private void initialize() {
        creditSpinners = List.of(creditSpinner1, creditSpinner2, creditSpinner3, creditSpinner4, creditSpinner5);
        gradeCombos = List.of(gradeCombo1, gradeCombo2, gradeCombo3, gradeCombo4, gradeCombo5);

        for (Spinner<Integer> spinner : creditSpinners) {
            spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 3));
            spinner.focusedProperty().addListener((observable, wasFocused, isFocused) -> {
                if (!isFocused) {
                    spinner.increment(0); // commit editor text on focus loss
                }
            });
        }

        ObservableList<String> gradeOptions = FXCollections.observableArrayList(GRADE_ORDER);
        for (ComboBox<String> comboBox : gradeCombos) {
            comboBox.setItems(gradeOptions);
            comboBox.setPromptText("Select");
        }
    }

    @FXML
    private void handleCalculateButton(ActionEvent event) {
        double totalGradePoints = 0.0;
        int totalCredits = 0;

        for (int i = 0; i < creditSpinners.size(); i++) {
            Integer credits = creditSpinners.get(i).getValue();
            String grade = gradeCombos.get(i).getValue();

            if (credits == null || credits <= 0 || grade == null || grade.isBlank()) {
                continue;
            }

            double gradePoint = GRADE_POINTS.getOrDefault(grade, 0.0);
            totalGradePoints += gradePoint * credits;
            totalCredits += credits;
        }

        if (totalCredits == 0) {
            showAlert("Missing course data", "Select at least one grade with credits to calculate GPA.");
            return;
        }

        double gpa = totalGradePoints / totalCredits;
        showResultPage(gpa);
    }

    private void showResultPage(double gpa) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultPage.fxml"));
            Parent root = loader.load();

            ResultPageController controller = loader.getController();
            controller.setGpa(gpa);

            Stage stage = (Stage) calculateButton.getScene().getWindow();
            Scene currentScene = stage.getScene();

            if (currentScene == null) {
                stage.setScene(new Scene(root, 600, 400));
            } else {
                currentScene.setRoot(root);
            }

            stage.setTitle("Your GPA Result");
        } catch (IOException e) {
            showAlert("Unable to load result page", e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}