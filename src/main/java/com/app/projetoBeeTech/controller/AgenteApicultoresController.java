package com.app.projetoBeeTech.controller;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.model.producao.Apicultor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TextField campoBusca;

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

    private final ApicultorImpl apicultorDAO = new ApicultorImpl();
    private ObservableList<Apicultor> observableList; // lista principal usada no filtro

    @FXML
    void initialize() {
        configurarColunas();
        listarApicultores();
        configurarFiltro();


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

    private void configurarColunas() {
        colunaApicultor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));

        colunaCpfCnpj.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCpfCnpj()));

        colunaTelefone.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTelefone()));

        colunaEndereco.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEndereco()));
    }


    private void listarApicultores() {
        List<Apicultor> lista = apicultorDAO.read();
        observableList = FXCollections.observableArrayList(lista);
        tabelaApicultores.setItems(observableList);
    }

    private void configurarFiltro() {
        if (observableList == null) return;

        FilteredList<Apicultor> listaFiltrada = new FilteredList<>(observableList, p -> true);

        campoBusca.textProperty().addListener((observable, oldValue, newValue) -> {
            listaFiltrada.setPredicate(apicultor -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }

                String filtro = newValue.toLowerCase();
                return apicultor.getNome().toLowerCase().contains(filtro)
                        || apicultor.getCpfCnpj().toLowerCase().contains(filtro);
            });
        });

        SortedList<Apicultor> listaOrdenada = new SortedList<>(listaFiltrada);
        listaOrdenada.comparatorProperty().bind(tabelaApicultores.comparatorProperty());
        tabelaApicultores.setItems(listaOrdenada);
    }

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
        observableList.setAll(apicultorDAO.read()); // atualiza sem perder filtro
        limparCampos();
        mostrarInfo("Apicultor adicionado com sucesso!");
    }

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
        observableList.setAll(apicultorDAO.read());
        limparCampos();
        mostrarInfo("Apicultor atualizado com sucesso!");
    }

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
                observableList.setAll(apicultorDAO.read());
                limparCampos();
                mostrarInfo("Apicultor excluído com sucesso!");
            }
        });
    }

    private void limparCampos() {
        apicultorNome.clear();
        apicultorCpfCnpj.clear();
        apicultorTelefone.clear();
        apicultorEndereco.clear();
    }

    private boolean camposInvalidos() {
        return apicultorNome.getText().isBlank()
                || apicultorCpfCnpj.getText().isBlank()
                || apicultorTelefone.getText().isBlank()
                || apicultorEndereco.getText().isBlank();
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atenção");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
