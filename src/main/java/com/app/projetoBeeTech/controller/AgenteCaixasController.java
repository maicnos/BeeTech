package com.app.projetoBeeTech.controller;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.dao.implemetacoes.ApiarioImpl;
import com.app.projetoBeeTech.dao.implemetacoes.CaixaImpl;
import com.app.projetoBeeTech.model.producao.Apicultor;
import com.app.projetoBeeTech.model.producao.Apiario;
import com.app.projetoBeeTech.model.producao.Caixa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.util.List;

public class AgenteCaixasController {

    @FXML
    private Button buttonAdicionarCaixa;

    @FXML
    private Button buttonAtualizarCaixa;

    @FXML
    private Button buttonExcluirCaixa;

    @FXML
    private TableColumn<Caixa, String> colunaIdentificacaoCaixa;

    @FXML
    private TableColumn<Caixa, String> colunaNomeApiario;

    @FXML
    private ComboBox<Apicultor> comboBoxApicultor;

    @FXML
    private ComboBox<Apiario> comboBoxApiario;

    @FXML
    private TableView<Caixa> tabelaCaixas;

    @FXML
    private TextField textFieldIdentificacaoCaixa;

    private final ApicultorImpl apicultorDAO = new ApicultorImpl();
    private final ApiarioImpl apiarioDAO = new ApiarioImpl();
    private final CaixaImpl caixaDAO = new CaixaImpl();

    private ObservableList<Caixa> listaCaixas;
    private Caixa caixaSelecionada;

    @FXML
    public void initialize() {
        configurarTabela();
        configurarComboBoxes();
        carregarApicultores();

        // Inicia com a tabela vazia
        tabelaCaixas.setItems(FXCollections.observableArrayList());

        // Quando o apicultor muda → carrega apiários
        comboBoxApicultor.setOnAction(e -> carregarApiariosPorApicultor());

        // Quando o apiário muda → carrega caixas
        comboBoxApiario.setOnAction(e -> carregarCaixasPorApiario());

        // Ao clicar na tabela → preencher campos
        tabelaCaixas.setOnMouseClicked(event -> {
            caixaSelecionada = tabelaCaixas.getSelectionModel().getSelectedItem();
            if (caixaSelecionada != null) {
                textFieldIdentificacaoCaixa.setText(caixaSelecionada.getIdentificacao());
                if (caixaSelecionada.getApiario() != null) {
                    Apiario apiario = caixaSelecionada.getApiario();
                    comboBoxApicultor.setValue(apiario.getApicultor());
                    comboBoxApiario.setValue(apiario);
                }
            }
        });
    }


    /** Configura as colunas da tabela de caixas */
    private void configurarTabela() {
        colunaIdentificacaoCaixa.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdentificacao())
        );

        colunaNomeApiario.setCellValueFactory(cellData -> {
            Apiario apiario = cellData.getValue().getApiario();
            return new javafx.beans.property.SimpleStringProperty(
                    apiario != null ? apiario.getNome() : "—"
            );
        });
    }

    /** Configura como os ComboBoxes exibem os dados */
    private void configurarComboBoxes() {
        // Apicultor: exibe "nome (cpf/ cnpj)"
        comboBoxApicultor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Apicultor apicultor) {
                if (apicultor == null) return "";
                return apicultor.getNome() + " (" + apicultor.getCpfCnpj() + ")";
            }

            @Override
            public Apicultor fromString(String string) {
                return null; // não é usado diretamente
            }
        });

        // Apiário: exibe apenas o nome
        comboBoxApiario.setConverter(new StringConverter<>() {
            @Override
            public String toString(Apiario apiario) {
                return apiario == null ? "" : apiario.getNome();
            }

            @Override
            public Apiario fromString(String string) {
                return null;
            }
        });
    }

    /** Carrega todos os apicultores do banco */
    private void carregarApicultores() {
        List<Apicultor> listaApicultores = apicultorDAO.read();
        comboBoxApicultor.setItems(FXCollections.observableArrayList(listaApicultores));
    }

    /** Carrega apenas os apiários do apicultor selecionado */
    private void carregarApiariosPorApicultor() {
        Apicultor apicultorSelecionado = comboBoxApicultor.getValue();
        if (apicultorSelecionado != null) {
            List<Apiario> listaApiarios = apiarioDAO.findByApicultorId(apicultorSelecionado.getId());
            // seta o apicultor explicitamente
            for (Apiario apiario : listaApiarios) {
                apiario.setApicultor(apicultorSelecionado);
            }
            comboBoxApiario.setItems(FXCollections.observableArrayList(listaApiarios));
        } else {
            comboBoxApiario.getItems().clear();
        }
    }

    /** Carrega apenas as caixas do apiário selecionado */
    private void carregarCaixasPorApiario() {
        Apiario apiarioSelecionado = comboBoxApiario.getValue();
        if (apiarioSelecionado != null) {
            List<Caixa> caixas = caixaDAO.findByApiarioId(apiarioSelecionado.getId());
            listaCaixas = FXCollections.observableArrayList(caixas);
            tabelaCaixas.setItems(listaCaixas);
        } else {
            tabelaCaixas.getItems().clear();
        }
    }


    /** Carrega todas as caixas do banco */
    private void carregarCaixas() {
        List<Caixa> caixas = caixaDAO.read();
        listaCaixas = FXCollections.observableArrayList(caixas);
        tabelaCaixas.setItems(listaCaixas);
    }

    /** Botão: adicionar nova caixa */
    @FXML
    void adicionarCaixa(ActionEvent event) {
        Apiario apiarioSelecionado = comboBoxApiario.getValue();
        String identificacao = textFieldIdentificacaoCaixa.getText().trim();

        if (apiarioSelecionado == null || identificacao.isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Selecione um apiário e informe a identificação da caixa.");
            return;
        }

        Caixa novaCaixa = new Caixa(0, identificacao);
        novaCaixa.setApiario(apiarioSelecionado);
        caixaDAO.create(novaCaixa);

        carregarCaixas();
        limparCampos();
    }

    /** Botão: atualizar caixa existente */
    @FXML
    void atualizarCaixa(ActionEvent event) {
        if (caixaSelecionada == null) {
            mostrarAlerta("Seleção necessária", "Selecione uma caixa na tabela para atualizar.");
            return;
        }

        Apiario apiarioSelecionado = comboBoxApiario.getValue();
        String identificacao = textFieldIdentificacaoCaixa.getText().trim();

        if (apiarioSelecionado == null || identificacao.isEmpty()) {
            mostrarAlerta("Campos obrigatórios", "Selecione um apiário e informe a identificação.");
            return;
        }

        caixaSelecionada.setIdentificacao(identificacao);
        caixaSelecionada.setApiario(apiarioSelecionado);
        caixaDAO.update(caixaSelecionada);

        carregarCaixas();
        limparCampos();
    }

    /** Botão: excluir caixa */
    @FXML
    void excluirCaixa(ActionEvent event) {
        if (caixaSelecionada == null) {
            mostrarAlerta("Seleção necessária", "Selecione uma caixa para excluir.");
            return;
        }

        caixaDAO.delete(caixaSelecionada.getId());
        carregarCaixas();
        limparCampos();
    }

    /** Limpa campos e seleções */
    private void limparCampos() {
        textFieldIdentificacaoCaixa.clear();
        comboBoxApiario.getSelectionModel().clearSelection();
        tabelaCaixas.getSelectionModel().clearSelection();
        caixaSelecionada = null;
    }

    /** Exibe alertas simples */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }
}
