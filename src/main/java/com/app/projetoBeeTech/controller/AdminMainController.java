package com.app.projetoBeeTech.controller;

import com.app.projetoBeeTech.dao.implemetacoes.AgenteNegociosImpl;
import com.app.projetoBeeTech.model.usuario.AgenteNegocios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.awt.event.ActionEvent;
import java.util.List;

public class AdminMainController {

    @FXML
    private Pane AgentPane;

    @FXML
    private TextField agentCpfInput;

    @FXML
    private TextField agentNomeInput;

    @FXML
    private TextField agentSenhaConfirm;

    @FXML
    private TextField agentSenhaInput;

    @FXML
    private TextField agentTelInput;

    @FXML
    private Button buttonAdminSair;

    @FXML
    private Button buttonAgentPane;

    @FXML
    private TableView<AgenteNegocios> tabelaAgentes;

    @FXML
    private TableColumn<AgenteNegocios, String> colunaAgentCpf;

    @FXML
    private TableColumn<AgenteNegocios, Integer> colunaAgentID;

    @FXML
    private TableColumn<AgenteNegocios, String> colunaAgentNome;

    @FXML
    private TableColumn<AgenteNegocios, String> colunaAgentTel;

    @FXML
    private Button buttonAdicionarAgent;

    @FXML
    private Button buttonDeleteAgent;

    @FXML
    private Button buttonUpdateAgent;



    public void adicionarAgente() {

    }

    public void atualizarAgente() {

    }

    public void deletarAgente() {

    }



    public void sair() {
        //ConnectionFactory.getInstance().closeConnection(); // Fecha a conexão com o banco
        System.exit(0);
    }


    private void listarAgentes() {
        AgenteNegociosImpl AgenteDao = new AgenteNegociosImpl();
        List<AgenteNegocios> listaAgentes = AgenteDao.read();
        ObservableList<AgenteNegocios> obsLista = FXCollections.observableArrayList(listaAgentes);
        tabelaAgentes.setItems(obsLista);


    }




    @FXML
    void initialize() {

        colunaAgentID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaAgentNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaAgentCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaAgentTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        listarAgentes();

        tabelaAgentes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                agentNomeInput.setText(newSelection.getNome());
                agentCpfInput.setText(newSelection.getCpf());
                agentTelInput.setText(newSelection.getTelefone());
                // agentSenhaInput.setText();
                // agentSenhaConfirm.setText();
            } else {
                agentNomeInput.clear();
                agentCpfInput.clear();
                agentTelInput.clear();
            }
        });



    }

}

