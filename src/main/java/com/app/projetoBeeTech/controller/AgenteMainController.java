package com.app.projetoBeeTech.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AgenteMainController {

    @FXML
    private Button buttonSair;

    @FXML
    private Button buttonViewApiario;

    @FXML
    private Button buttonViewApicultor;

    @FXML
    private Button buttonViewCaixa;

    @FXML
    private Button buttonViewGasto;

    @FXML
    private Button buttonViewInventario;

    @FXML
    private Button buttonViewRelatorio;

    /**
     * Método utilitário para abrir uma nova janela (Stage) com o FXML informado.
     */
    private void abrirNovaJanela(String fxmlPath, String tituloJanela) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(tituloJanela);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Idealmente, use um Alert em vez de printStackTrace em produção
        }
    }

    @FXML
    void abrirTelaApiario(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteApiariosView.fxml.fxml", "Gerenciar Apiários");
    }

    @FXML
    void abrirTelaApicultor(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteApicultoresView.fxml.fxml", "Gerenciar Apicultores");
    }

    @FXML
    void abrirTelaCaixas(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteCaixasView.fxml.fxml", "Gerenciar Caixas");
    }

    @FXML
    void abrirTelaGastos(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteGastosView.fxml", "Gerenciar Gastos");
    }

    @FXML
    void abrirTelaInventario(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteInventarioView.fxml", "Gerenciar Inventário");
    }

    @FXML
    void abrirTelaRelatorio(ActionEvent event) {
        abrirNovaJanela("/com/app/projetoBeeTech/agenteRelatorioView.fxml.fxml", "Relatórios");
    }

    @FXML
    void sair(ActionEvent event) {
        // Fecha apenas a janela atual
        Stage stage = (Stage) buttonSair.getScene().getWindow();
        stage.close();
    }
}
