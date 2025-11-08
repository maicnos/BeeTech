package com.app.projetoBeeTech.controller;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.model.producao.Apicultor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AgenteApicultoresController {

    @FXML
    private TextField apicultorCpfCnpj;

    @FXML
    private TextArea apicultorEndereco;

    @FXML
    private TextField apicultorNome;

    @FXML
    private TextField apicultorTelefone;

    @FXML
    private Button buttonAdicionarApicultor;

    @FXML
    private Button buttonAtualizarApicultor;

    @FXML
    private Button buttonExcluirApicultor;

    @FXML
    private TableColumn<Apicultor, String> colunaApicultor;

    @FXML
    private TableColumn<Apicultor, String> colunaCpfCnpj;

    @FXML
    private TableColumn<Apicultor, String> colunaEndereco;

    @FXML
    private TableColumn<Apicultor, String> colunaTelefone;

    @FXML
    private TableView<Apicultor> tabelaApicultores;

    // DAO responsável pelo CRUD no banco
    private final ApicultorImpl apicultorDAO = new ApicultorImpl();

    /**
     * Inicialização automática do FXML.
     */
    @FXML
    void initialize() {
        configurarColunas();
        listarApicultores();

        // Listener para preencher campos ao selecionar linha
        tabelaApicultores.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                apicultorNome.setText(novo.getNome());
                apicultorCpfCnpj.setText(novo.getCpfCnpj());
                apicultorTelefone.setText(novo.getTelefone());
                apicultorEndereco.setText(novo.getEndereco());
            } else {
                limparCampos();
            }
        });
    }

    /**
     * Configura as colunas da tabela conforme os atributos da model Apicultor.
     */
    private void configurarColunas() {
        colunaApicultor.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpfCnpj.setCellValueFactory(new PropertyValueFactory<>("cpfCnpj"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
    }

    /**
     * Lista os apicultores do banco na TableView.
     */
    private void listarApicultores() {
        List<Apicultor> lista = apicultorDAO.read();
        ObservableList<Apicultor> observableList = FXCollections.observableArrayList(lista);
        tabelaApicultores.setItems(observableList);
    }

    /**
     * Adiciona um novo Apicultor ao banco.
     */
    @FXML
    void adicionarApicultor() {
        if (camposInvalidos()) {
            mostrarAlerta("Preencha todos os campos antes de adicionar.");
            return;
        }

        Apicultor novo = new Apicultor(
                0,
                apicultorNome.getText(),
                apicultorCpfCnpj.getText(),
                apicultorTelefone.getText(),
                apicultorEndereco.getText()
        );

        apicultorDAO.create(novo);
        listarApicultores();
        limparCampos();
        mostrarInfo("Apicultor adicionado com sucesso!");
    }

    /**
     * Atualiza o Apicultor selecionado na tabela.
     */
    @FXML
    void atualizarApicultor() {
        Apicultor selecionado = tabelaApicultores.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um apicultor para atualizar.");
            return;
        }

        if (camposInvalidos()) {
            mostrarAlerta("Preencha todos os campos antes de atualizar.");
            return;
        }

        selecionado.setNome(apicultorNome.getText());
        selecionado.setCpfCnpj(apicultorCpfCnpj.getText());
        selecionado.setTelefone(apicultorTelefone.getText());
        selecionado.setEndereco(apicultorEndereco.getText());

        apicultorDAO.update(selecionado);
        listarApicultores();
        limparCampos();
        mostrarInfo("Apicultor atualizado com sucesso!");
    }

    /**
     * Exclui o Apicultor selecionado.
     */
    @FXML
    void excluirApicultor() {
        Apicultor selecionado = tabelaApicultores.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um apicultor para excluir.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação");
        confirm.setHeaderText("Excluir apicultor");
        confirm.setContentText("Tem certeza que deseja excluir " + selecionado.getNome() + "?");

        confirm.showAndWait().ifPresent(res -> {
            if (res == ButtonType.OK) {
                apicultorDAO.delete(selecionado.getId());
                listarApicultores();
                limparCampos();
                mostrarInfo("Apicultor excluído com sucesso!");
            }
        });
    }

    /**
     * Limpa todos os campos de entrada.
     */
    private void limparCampos() {
        apicultorNome.clear();
        apicultorCpfCnpj.clear();
        apicultorTelefone.clear();
        apicultorEndereco.clear();
    }

    /**
     * Verifica se há campos vazios.
     */
    private boolean camposInvalidos() {
        return apicultorNome.getText().isBlank()
                || apicultorCpfCnpj.getText().isBlank()
                || apicultorTelefone.getText().isBlank()
                || apicultorEndereco.getText().isBlank();
    }

    /**
     * Mostra um alerta de aviso.
     */
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Mostra uma mensagem de informação.
     */
    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
