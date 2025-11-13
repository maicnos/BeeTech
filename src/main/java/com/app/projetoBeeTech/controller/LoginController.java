package com.app.projetoBeeTech.controller;

import java.io.IOException;

import com.app.projetoBeeTech.model.usuario.Administrador;
import com.app.projetoBeeTech.model.usuario.AgenteNegocios;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import com.app.projetoBeeTech.dao.implemetacoes.AdministradorImpl;
import com.app.projetoBeeTech.dao.implemetacoes.AgenteNegociosImpl;
import com.app.projetoBeeTech.util.Auth;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private Button buttonLogin;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane loginAdmin;

    @FXML
    private Label loginAlert;

    @FXML
    private PasswordField textFieldPasswd;

    @FXML
    private TextField textFieldUser;

    private final Auth auth = new Auth(new AdministradorImpl(), new AgenteNegociosImpl());



    private void pressionarEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            execLogin();
        }
    }



    @FXML
    void cancelar(ActionEvent event) {
        System.exit(0);

    }

    @FXML
    void login(ActionEvent event) {
        execLogin();


    }


    private void execLogin(){

        String cpfCnpj = textFieldUser.getText().trim();
        String senha = textFieldPasswd.getText().trim();

        if (cpfCnpj.isEmpty() || senha.isEmpty()) {
            loginAlert.setText("Preencha todos os campos.");
            return;
        }

        try {

            Administrador admin = auth.autenticarAdmin(cpfCnpj, senha);
            if (admin != null) {
                abrirTela("/com/app/projetoBeeTech/adminMain.fxml", "Painel de Administração do Sistema");
                return;
            }


            AgenteNegocios agente = auth.autenticarAgente(cpfCnpj, senha);
            if (agente != null) {
                abrirTela("/com/app/projetoBeeTech/agenteMain.fxml", "Painel do Agente de Negócios");
                return;
            }


            loginAlert.setText("Usuário ou senha incorretos.");

        } catch (Exception e) {
            e.printStackTrace();
            loginAlert.setText("Erro ao tentar realizar login.");
        }

    }


    private void abrirTela(String caminhoFXML, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.setResizable(true);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();


        Stage loginStage = (Stage) buttonLogin.getScene().getWindow();
        loginStage.close();
    }

    @FXML
    void initialize() {
        textFieldUser.setOnKeyPressed(this::pressionarEnter);
        textFieldPasswd.setOnKeyPressed(this::pressionarEnter);
    }

}

