package com.app.projetoBeeTech.controller.agente;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.dao.implemetacoes.ApiarioImpl;
import com.app.projetoBeeTech.model.producao.Apicultor;
import com.app.projetoBeeTech.model.producao.Apiario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class AgenteApiariosController {

    @FXML
    private Button buttonAdicionarApiario;
    @FXML
    private Button buttonAtualizarApiario;
    @FXML
    private Button buttonExcluirApiario;

    @FXML
    private TableColumn<Apiario, String> colunaLocalizacaoApiario;
    @FXML
    private TableColumn<Apiario, String> colunaNomeApiario;
    @FXML
    private TableColumn<Apiario, String> colunaNomeApicultor;

    @FXML
    private ComboBox<Apicultor> comboBoxSelecionarApicultor;

    @FXML
    private TableView<Apiario> tabelaApicultorApiario;

    @FXML
    private TextArea textAreaLocalizacaoApiario;

    @FXML
    private TextField textFieldNomeApiario;

    private final ApiarioImpl apiarioDAO = new ApiarioImpl();
    private final ApicultorImpl apicultorDAO = new ApicultorImpl();

    private ObservableList<Apicultor> apicultores;
    private ObservableList<Apiario> apiarios;

    private Apiario apiarioSelecionado;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarApicultores();

        comboBoxSelecionarApicultor.setOnAction(event -> carregarApiariosPorApicultor());
        tabelaApicultorApiario.setOnMouseClicked(this::selecionarApiario);
    }


    private void configurarTabela() {
        colunaNomeApiario.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        colunaLocalizacaoApiario.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getLocalizacao()));
        colunaNomeApicultor.setCellValueFactory(data -> {
            Apicultor apicultor = data.getValue().getApicultor();
            String nomeApicultor = (apicultor != null) ? apicultor.getNome() : "";
            return new javafx.beans.property.SimpleStringProperty(nomeApicultor);
        });
    }


    private void carregarApicultores() {
        apicultores = FXCollections.observableArrayList(apicultorDAO.read());
        comboBoxSelecionarApicultor.setItems(apicultores);

        comboBoxSelecionarApicultor.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Apicultor apicultor, boolean empty) {
                super.updateItem(apicultor, empty);
                if (empty || apicultor == null) {
                    setText(null);
                } else {
                    setText(apicultor.getNome() + " - " + apicultor.getCpfCnpj());
                }
            }
        });

        comboBoxSelecionarApicultor.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Apicultor apicultor, boolean empty) {
                super.updateItem(apicultor, empty);
                if (empty || apicultor == null) {
                    setText(null);
                } else {
                    setText(apicultor.getNome() + " - " + apicultor.getCpfCnpj());
                }
            }
        });
    }


    private void carregarApiariosPorApicultor() {
        Apicultor apicultorSelecionado = comboBoxSelecionarApicultor.getValue();
        if (apicultorSelecionado != null) {
            apiarios = FXCollections.observableArrayList(apiarioDAO.findByApicultorId(apicultorSelecionado.getId()));


            for (Apiario apiario : apiarios) {
                apiario.setApicultor(apicultorSelecionado);
            }


            apicultorSelecionado.setListaApiarios(apiarios);

            tabelaApicultorApiario.setItems(apiarios);
        } else {
            tabelaApicultorApiario.getItems().clear();
        }
    }

    @FXML
    private void adicionarApiario(ActionEvent event) {
        Apicultor apicultorSelecionado = comboBoxSelecionarApicultor.getValue();
        if (apicultorSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um apicultor antes de adicionar um apiário.", Alert.AlertType.WARNING);
            return;
        }

        String nome = textFieldNomeApiario.getText();
        String localizacao = textAreaLocalizacaoApiario.getText();

        if (nome.isEmpty() || localizacao.isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Preencha todos os campos para adicionar o apiário.", Alert.AlertType.WARNING);
            return;
        }

        Apiario novo = new Apiario(0, nome, localizacao);
        novo.setApicultor(apicultorSelecionado);

        apiarioDAO.create(novo);
        carregarApiariosPorApicultor();
        limparCampos();
    }


    @FXML
    private void atualizarApiario(ActionEvent event) {
        if (apiarioSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um apiário na tabela para atualizar.", Alert.AlertType.WARNING);
            return;
        }

        apiarioSelecionado.setNome(textFieldNomeApiario.getText());
        apiarioSelecionado.setLocalizacao(textAreaLocalizacaoApiario.getText());
        apiarioSelecionado.setApicultor(comboBoxSelecionarApicultor.getValue());

        apiarioDAO.update(apiarioSelecionado);
        carregarApiariosPorApicultor();
        limparCampos();
    }


    @FXML
    private void excluirApiario(ActionEvent event) {
        if (apiarioSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um apiário para excluir.", Alert.AlertType.WARNING);
            return;
        }

        apiarioDAO.delete(apiarioSelecionado.getId());
        carregarApiariosPorApicultor();
        limparCampos();
    }


    private void selecionarApiario(MouseEvent event) {
        apiarioSelecionado = tabelaApicultorApiario.getSelectionModel().getSelectedItem();
        if (apiarioSelecionado != null) {
            textFieldNomeApiario.setText(apiarioSelecionado.getNome());
            textAreaLocalizacaoApiario.setText(apiarioSelecionado.getLocalizacao());
        }
    }

    private void limparCampos() {
        textFieldNomeApiario.clear();
        textAreaLocalizacaoApiario.clear();
        apiarioSelecionado = null;
        tabelaApicultorApiario.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
