package com.app.projetoBeeTech.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
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
    private TextField adminUsername;

    @FXML
    private Button agenteLoginButton;

    @FXML
    private PasswordField agentePassWordField;

    @FXML
    private TextField agenteUsername;

    @FXML
    private Button cancelButton;

    @FXML
    private AnchorPane loginAdmin;

    @FXML
    private AnchorPane loginAgente;

    @FXML
    private Label loginAlert;

    private final Auth auth = new Auth(new AdministradorImpl(), new AgenteNegociosImpl());


    public void adminLogin() {
        String cpf = adminUsername.getText();
        String senha = adminPassWordField.getText();


        if (auth.autenticarAdmin(cpf,senha)) {
            loginAlert.setText("Login de administrador realizado com sucesso!");
            // Redirecionar para a tela principal do admin
        } else {
            loginAlert.setText("Erro, CPF ou senha incorretos.");
        }

    }

    public void agenteLogin() {
        String cpf = agenteUsername.getText();
        String senha = agentePassWordField.getText();
        System.out.println("Aqui foi");


        if (auth.autenticarAgente(cpf, senha)) {
            loginAlert.setText("Login de Agente de Negócios realizado com sucesso!");
            System.out.println("Sucesso");
            // Redirecionar para a tela principal do agente

            try {
                // carrega o fxml do agente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/projetoBeeTech/agenteMain.fxml"));
                Parent root = loader.load();

                // Pega o controller da tela do agente
                AgenteMainController controller = loader.getController();
                //controller.setAgente(agente); // passa o objeto agente para o controller

                // Cria um novo Stage
                Stage agenteStage = new Stage();
                agenteStage.setTitle("Painel do Agente de Negócios");
                agenteStage.setScene(new Scene(root));
                agenteStage.setResizable(true);
                agenteStage.show();

                // Fecha a tela de login
                Stage loginStage = (Stage) agenteLoginButton.getScene().getWindow();
                loginStage.close();

            } catch (IOException e) {
                e.printStackTrace();
                loginAlert.setText("Erro ao abrir a tela do agente.");
            }
        } else {
            loginAlert.setText("Erro, CPF ou senha incorretos.");
            System.out.println("Erro");
        }

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
        //ConnectionFactory.getInstance().closeConnection(); // Fecha a conexão com o banco
        System.exit(0);
    }

    @FXML
    void initialize() {
        assert adminLoginButton != null : "fx:id=\"adminLoginButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert adminPassWordField != null : "fx:id=\"adminPassWordField\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert adminUsername != null : "fx:id=\"adminUserButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agenteLoginButton != null : "fx:id=\"agenteLoginButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agentePassWordField != null : "fx:id=\"agentePassWordField\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert agenteUsername != null : "fx:id=\"agenteUserButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert loginAdmin != null : "fx:id=\"loginAdmin\" was not injected: check your FXML file 'loginForm.fxml'.";
        assert loginAgente != null : "fx:id=\"loginAgente\" was not injected: check your FXML file 'loginForm.fxml'.";

    }

}
