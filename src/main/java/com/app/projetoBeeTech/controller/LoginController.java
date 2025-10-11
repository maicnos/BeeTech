package com.app.projetoBeeTech.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button adminLoginButton;

    @FXML
    private PasswordField adminPassWordField;

    @FXML
    private TextField adminUserButton;

    @FXML
    private Button agenteLoginButton;

    @FXML
    private PasswordField agentePassWordField;

    @FXML
    private TextField agenteUserButton;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane loginAdmin;

    @FXML
    private AnchorPane loginAgente;


    public void adminLogin() {

    }

    public void agenteLogin() {

    }


    public void loginAdminShow() {
        loginAdmin.setVisible(true);
        loginAgente.setVisible(false);
    }

    public void loginAgenteShow() {
        loginAgente.setVisible(true);
        loginAdmin.setVisible(false);
    }

    public void cancel() {
        System.exit(0);
    }

    @FXML
    void initialize() {
        assert adminLoginButton != null : "fx:id=\"adminLoginButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert adminPassWordField != null : "fx:id=\"adminPassWordField\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert adminUserButton != null : "fx:id=\"adminUserButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agenteLoginButton != null : "fx:id=\"agenteLoginButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agentePassWordField != null : "fx:id=\"agentePassWordField\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agenteUserButton != null : "fx:id=\"agenteUserButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert loginAdmin != null : "fx:id=\"loginAdmin\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert loginAgente != null : "fx:id=\"loginAgente\" was not injected: check your FXML file 'loginForm.fxml'.";

    }

}
