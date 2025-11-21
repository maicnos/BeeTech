package com.app.projetoBeeTech.controller.agente;

import com.app.projetoBeeTech.dao.implemetacoes.*;
import com.app.projetoBeeTech.model.producao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


public class AgenteProducaoController {

    @FXML private DatePicker DatePickerDataProducao;
    @FXML private Button buttonAdicionarProducao;
    @FXML private Button buttonAtualizarProducao;
    @FXML private Button buttonExcluirProducao;
    @FXML private TableColumn<Producao, String> colunaDataProducao;
    @FXML private TableColumn<Producao, Double> colunaQuantidadeProducao;
    @FXML private TableColumn<Producao, String> colunaTipoProducao;

    @FXML private ComboBox<Apicultor> comboBoxApicultor;
    @FXML private ComboBox<Apiario> comboBoxApiario;
    @FXML private ComboBox<Caixa> comboBoxCaixa;
    @FXML private ComboBox<Producao.Tipo> comboBoxTipoProducao;

    @FXML private TableView<Producao> tabelaProducao;
    @FXML private TextField textFieldQuantidadeProducao;

    private final ApicultorImpl apicultorDAO = new ApicultorImpl();
    private final ApiarioImpl apiarioDAO = new ApiarioImpl();
    private final CaixaImpl caixaDAO = new CaixaImpl();
    private final ProducaoImpl producaoDAO = new ProducaoImpl();

    private Producao producaoSelecionada;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarApicultores();
        configurarListeners();
        comboBoxTipoProducao.setItems(FXCollections.observableArrayList(Producao.Tipo.values()));
    }

    private void configurarTabela() {

        colunaTipoProducao.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        cell.getValue().getTipo() != null ? cell.getValue().getTipo().name() : ""
                )
        );

        colunaQuantidadeProducao.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colunaDataProducao.setCellValueFactory(cell -> {

            Date data = cell.getValue().getData();
            if (data == null)
                return new javafx.beans.property.SimpleStringProperty("");

            LocalDate ld;

            try {
                ld = data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (UnsupportedOperationException e) {
                ld = new java.sql.Date(data.getTime()).toLocalDate();
            }

            return new javafx.beans.property.SimpleStringProperty(ld.format(formatter));
        });

        tabelaProducao.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                producaoSelecionada = newV;
                preencherCamposComProducao(newV);
            }
        });
    }


    private void carregarApicultores() {
        List<Apicultor> lista = apicultorDAO.read();
        comboBoxApicultor.setItems(FXCollections.observableArrayList(lista));

        comboBoxApicultor.setCellFactory(combo -> new ListCell<>() {
            @Override
            protected void updateItem(Apicultor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " - " + item.getCpfCnpj());
            }
        });

        comboBoxApicultor.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Apicultor item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome() + " - " + item.getCpfCnpj());
            }
        });
    }

    private void configurarListeners() {

        comboBoxApicultor.valueProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                carregarApiarios(novo.getId());
                comboBoxCaixa.getItems().clear();
                tabelaProducao.getItems().clear();
            }
        });

        comboBoxApiario.valueProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                carregarCaixas(novo.getId());
                tabelaProducao.getItems().clear();
            }
        });

        comboBoxCaixa.valueProperty().addListener((obs, old, novo) -> {
            if (novo != null) {
                carregarProducaoDaCaixa(novo.getId());
            }
        });
    }

    private void carregarApiarios(int idApicultor) {
        List<Apiario> lista = apiarioDAO.findByApicultorId(idApicultor);
        comboBoxApiario.setItems(FXCollections.observableArrayList(lista));

        comboBoxApiario.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Apiario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });

        comboBoxApiario.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Apiario item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNome());
            }
        });
    }

    private void carregarCaixas(int idApiario) {
        List<Caixa> lista = caixaDAO.findByApiarioId(idApiario);
        comboBoxCaixa.setItems(FXCollections.observableArrayList(lista));

        comboBoxCaixa.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Caixa item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getIdentificacao());
            }
        });

        comboBoxCaixa.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Caixa item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getIdentificacao());
            }
        });
    }

    private void carregarProducaoDaCaixa(int idCaixa) {
        ObservableList<Producao> lista = FXCollections.observableArrayList();

        for (Producao p : producaoDAO.read()) {
            if (p.getCaixa() != null && p.getCaixa().getId() == idCaixa) {
                lista.add(p);
            }
        }

        tabelaProducao.setItems(lista);
    }

    private void preencherCamposComProducao(Producao p) {

        comboBoxTipoProducao.setValue(p.getTipo());
        textFieldQuantidadeProducao.setText(String.valueOf(p.getQuantidade()));

        if (p.getData() != null) {
            LocalDate ld;
            try {
                ld = p.getData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } catch (UnsupportedOperationException e) {
                ld = new java.sql.Date(p.getData().getTime()).toLocalDate();
            }
            DatePickerDataProducao.setValue(ld);
        }
    }


    @FXML
    void adicionarProducao(ActionEvent event) {
        try {
            Caixa caixa = comboBoxCaixa.getValue();
            if (caixa == null) return;

            Producao.Tipo tipo = comboBoxTipoProducao.getValue();
            double qtd = Double.parseDouble(textFieldQuantidadeProducao.getText());

            LocalDate ld = DatePickerDataProducao.getValue();
            Date data = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Producao p = new Producao(0, tipo, qtd, data);
            p.setCaixa(caixa);

            producaoDAO.create(p);

            carregarProducaoDaCaixa(caixa.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void atualizarProducao(ActionEvent event) {
        try {
            if (producaoSelecionada == null) return;

            producaoSelecionada.setTipo(comboBoxTipoProducao.getValue());
            producaoSelecionada.setQuantidade(Double.parseDouble(textFieldQuantidadeProducao.getText()));

            LocalDate ld = DatePickerDataProducao.getValue();
            Date data = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            producaoSelecionada.setData(data);

            producaoDAO.update(producaoSelecionada);

            carregarProducaoDaCaixa(producaoSelecionada.getCaixa().getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void excluirProducao(ActionEvent event) {
        try {
            if (producaoSelecionada == null) return;

            producaoDAO.delete(producaoSelecionada.getId());

            carregarProducaoDaCaixa(producaoSelecionada.getCaixa().getId());
            producaoSelecionada = null;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
