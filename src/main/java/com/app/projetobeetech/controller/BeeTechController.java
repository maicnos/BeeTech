package com.app.projetobeetech.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BeeTechController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bem vindo ao BeeTECH");
    }
}