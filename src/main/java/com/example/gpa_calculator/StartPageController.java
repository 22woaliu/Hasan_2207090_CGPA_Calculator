package com.example.gpa_calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartPageController {

    @FXML
    private Button startButton;

    public void start(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("scene_2.fxml");
    }
}
