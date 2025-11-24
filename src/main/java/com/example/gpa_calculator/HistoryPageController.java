package com.example.gpa_calculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HistoryPageController {

    @FXML
    private TableView<GpaRecord> historyTable;
    @FXML
    private TableColumn<GpaRecord, Integer> idColumn;
    @FXML
    private TableColumn<GpaRecord, String> nameColumn;
    @FXML
    private TableColumn<GpaRecord, Double> gpaColumn;
    @FXML
    private TableColumn<GpaRecord, String> dateColumn;

    private DatabaseHandler dbHandler;
    private ObservableList<GpaRecord> recordList;

    @FXML
    public void initialize() {
        dbHandler = new DatabaseHandler();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("timestamp"));

        loadData();
    }

    private void loadData() {
        recordList = FXCollections.observableArrayList(dbHandler.getAllRecords());
        historyTable.setItems(recordList);
    }

    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        GpaRecord selectedRecord = historyTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Record");
            alert.setHeaderText("Are you sure you want to delete this record?");
            alert.setContentText("Student: " + selectedRecord.getStudentName());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                dbHandler.deleteRecord(selectedRecord.getId());
                loadData();
            }
        } else {
            showAlert("No Selection", "Please select a record to delete.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        GpaRecord selectedRecord = historyTable.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            TextInputDialog dialog = new TextInputDialog(selectedRecord.getStudentName());
            dialog.setTitle("Update Name");
            dialog.setHeaderText("Update Student Name");
            dialog.setContentText("Please enter the new name:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    selectedRecord.setStudentName(name);
                    dbHandler.updateRecord(selectedRecord);
                    loadData();
                }
            });
        } else {
            showAlert("No Selection", "Please select a record to update.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
