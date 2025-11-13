package com.app.projetoBeeTech.controller.agente;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.dao.implemetacoes.GastoImpl;
import com.app.projetoBeeTech.model.financeiro.Gasto;
import com.app.projetoBeeTech.model.producao.Apicultor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Date;
import java.util.List;

public class AgenteGastosController {

    @FXML private Button buttonAdicionarGasto;
    @FXML private Button buttonAtualizarGasto;
    @FXML private Button buttonExcluirGasto;

    @FXML private TableView<Gasto> tabelaGastos;
    @FXML private TableColumn<Gasto, String> colunaGastoTipo;
    @FXML private TableColumn<Gasto, Double> colunaGastoValor;
    @FXML private TableColumn<Gasto, java.time.LocalDate> colunaGastoData;

    @FXML private ComboBox<Apicultor> comboBoxApicultor;
    @FXML private ComboBox<String> comboBoxGastoTipo;
    @FXML private DatePicker datePickerGastoData;
    @FXML private TextField textFieldGastoValor;

    private final GastoImpl gastoDAO = new GastoImpl();
    private final ApicultorImpl apicultorDAO = new ApicultorImpl();
    private ObservableList<Gasto> listaGastos = FXCollections.observableArrayList();

    private Gasto gastoSelecionado;

    @FXML
    public void initialize() {
        // Configura colunas
        colunaGastoTipo.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getTipo()));
        colunaGastoValor.setCellValueFactory(cell -> new javafx.beans.property.SimpleObjectProperty<>(cell.getValue().getValor()));
        colunaGastoData.setCellValueFactory(cell -> {
            java.util.Date d = cell.getValue().getData();
            java.time.LocalDate ld = (d == null) ? null : new java.sql.Date(d.getTime()).toLocalDate();
            return new javafx.beans.property.SimpleObjectProperty<>(ld);
        });


        // Preenche combos
        comboBoxGastoTipo.setItems(FXCollections.observableArrayList("TRANSPORTE", "INSUMOS", "MANUTENÇAO"));
        comboBoxApicultor.setItems(FXCollections.observableArrayList(apicultorDAO.read()));
        comboBoxApicultor.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(Apicultor apicultor) {
                if (apicultor == null) return "";
                return apicultor.getNome() + " (" + apicultor.getCpfCnpj() + ")";
            }

            @Override
            public Apicultor fromString(String string) {
                // não é usado para seleção direta, então pode retornar null
                return null;
            }
        });


        // Evento de seleção do apicultor
        comboBoxApicultor.setOnAction(e -> carregarGastosPorApicultor());

        // Evento de seleção na tabela
        tabelaGastos.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) preencherCampos(newSel);
        });
    }

    private void carregarGastosPorApicultor() {
        Apicultor apicultor = comboBoxApicultor.getValue();
        if (apicultor != null) {
            List<Gasto> gastos = gastoDAO.readByApicultor(apicultor.getId());
            listaGastos.setAll(gastos);
            tabelaGastos.setItems(listaGastos);
        }
    }

    private void preencherCampos(Gasto gasto) {
        gastoSelecionado = gasto;
        comboBoxGastoTipo.setValue(gasto.getTipo());
        textFieldGastoValor.setText(String.valueOf(gasto.getValor()));
        datePickerGastoData.setValue(((java.sql.Date) gasto.getData()).toLocalDate());
    }

    @FXML
    void adicionarGasto(ActionEvent event) {
        Apicultor apicultor = comboBoxApicultor.getValue();
        if (apicultor == null) return;

        Gasto gasto = new Gasto();
        gasto.setTipo(comboBoxGastoTipo.getValue());
        gasto.setValor(Double.parseDouble(textFieldGastoValor.getText()));
        gasto.setData(Date.valueOf(datePickerGastoData.getValue()));
        gasto.setApicultor(apicultor);

        gastoDAO.create(gasto);
        carregarGastosPorApicultor();
        limparCampos();
    }

    @FXML
    void atualizarGasto(ActionEvent event) {
        if (gastoSelecionado == null) return;

        gastoSelecionado.setTipo(comboBoxGastoTipo.getValue());
        gastoSelecionado.setValor(Double.parseDouble(textFieldGastoValor.getText()));
        gastoSelecionado.setData(Date.valueOf(datePickerGastoData.getValue()));

        gastoDAO.update(gastoSelecionado);
        carregarGastosPorApicultor();
        limparCampos();
    }

    @FXML
    void excluirGasto(ActionEvent event) {
        if (gastoSelecionado == null) return;
        gastoDAO.delete(gastoSelecionado.getId());
        carregarGastosPorApicultor();
        limparCampos();
    }

    private void limparCampos() {
        textFieldGastoValor.clear();
        comboBoxGastoTipo.setValue(null);
        datePickerGastoData.setValue(null);
        gastoSelecionado = null;
        tabelaGastos.getSelectionModel().clearSelection();
    }
}
