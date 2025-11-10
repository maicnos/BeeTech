package com.app.projetoBeeTech.controller;

import com.app.projetoBeeTech.dao.implemetacoes.ApicultorImpl;
import com.app.projetoBeeTech.dao.implemetacoes.BemImpl;
import com.app.projetoBeeTech.dao.implemetacoes.InventarioImpl;
import com.app.projetoBeeTech.model.financeiro.Bem;
import com.app.projetoBeeTech.model.financeiro.Inventario;
import com.app.projetoBeeTech.model.producao.Apicultor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class AgenteInventarioController {

    @FXML
    private Button buttonAdicionarBem;

    @FXML
    private Button buttonAtualizarBem;

    @FXML
    private Button buttonExcluirBem;

    @FXML
    private TableColumn<Bem, String> colunaBemItem;

    @FXML
    private TableColumn<Bem, Integer> colunaBensQuantidade;

    @FXML
    private TableColumn<Bem, Double> colunaBensValor;

    @FXML
    private TableColumn<Bem, String> colunaBensData;

    @FXML
    private TableColumn<Bem, String> colunaBensDescricao;

    @FXML
    private ComboBox<Apicultor> comboBoxApicultor;

    @FXML
    private DatePicker datePickerBemData;

    @FXML
    private TableView<Bem> tabelaBensInventario;

    @FXML
    private TextArea textAreaBemDescricao;

    @FXML
    private TextField textFieldBemItem;

    @FXML
    private TextField textFieldBemQuantidade;

    @FXML
    private TextField textFieldBemValor;

    private final ApicultorImpl apicultorDAO = new ApicultorImpl();
    private final InventarioImpl inventarioDAO = new InventarioImpl();
    private final BemImpl bemDAO = new BemImpl();

    private ObservableList<Bem> listaBens = FXCollections.observableArrayList();

    private Inventario inventarioSelecionado;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarApicultores();

        comboBoxApicultor.setOnAction(e -> carregarInventarioApicultor());
        tabelaBensInventario.setItems(listaBens);

        tabelaBensInventario.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSel, newSel) -> preencherCampos(newSel)
        );


    }

    private void configurarTabela() {
        colunaBemItem.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getItem()
                )
        );

        colunaBensQuantidade.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleIntegerProperty(
                        cellData.getValue().getQuantidade()
                ).asObject()
        );

        colunaBensValor.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleDoubleProperty(
                        cellData.getValue().getValor()
                ).asObject()
        );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        colunaBensData.setCellValueFactory(
                cellData -> {
                    LocalDate data = cellData.getValue().getData();
                    String texto = (data != null) ? data.format(formatter) : "";
                    return new javafx.beans.property.SimpleStringProperty(texto);
                }
        );

        colunaBensDescricao.setCellValueFactory(
                cellData -> new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getDescricao()
                )
        );
    }


    private void carregarApicultores() {
        List<Apicultor> apicultores = apicultorDAO.read();
        comboBoxApicultor.setItems(FXCollections.observableArrayList(apicultores));

        comboBoxApicultor.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Apicultor item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNome() + " (" + item.getCpfCnpj() + ")");
                }
            }
        });

        comboBoxApicultor.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Apicultor item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Selecione um apicultor");
                } else {
                    setText(item.getNome() + " (" + item.getCpfCnpj() + ")");
                }
            }
        });
    }

    private void carregarInventarioApicultor() {
        Apicultor apicultor = comboBoxApicultor.getValue();
        if (apicultor == null) return;

        List<Inventario> inventarios = inventarioDAO.read();
        inventarioSelecionado = inventarios.stream()
                .filter(inv -> inv.getApicultor() != null && inv.getApicultor().getId() == apicultor.getId())
                .findFirst()
                .orElseGet(() -> {
                    // Cria inventário novo se não existir
                    Inventario novo = new Inventario();
                    novo.setApicultor(apicultor);
                    return inventarioDAO.create(novo);
                });

        atualizarTabela();
    }

    private void atualizarTabela() {
        listaBens.clear();
        if (inventarioSelecionado == null) return;

        List<Bem> bens = bemDAO.readByInventario(inventarioSelecionado.getId());
        listaBens.addAll(bens);
    }


    private void preencherCampos(Bem bem) {
        if (bem != null) {
            textFieldBemItem.setText(bem.getItem());
            textFieldBemQuantidade.setText(String.valueOf(bem.getQuantidade()));
            textFieldBemValor.setText(String.valueOf(bem.getValor()));
            datePickerBemData.setValue(bem.getData());
            textAreaBemDescricao.setText(bem.getDescricao());
        }
    }

    @FXML
    void adicionarBem(ActionEvent event) {
        if (inventarioSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um apicultor primeiro.", Alert.AlertType.WARNING);
            return;
        }

        try {
            Bem bem = new Bem();
            bem.setItem(textFieldBemItem.getText());
            bem.setQuantidade(Integer.parseInt(textFieldBemQuantidade.getText()));
            bem.setValor(Double.parseDouble(textFieldBemValor.getText()));
            bem.setDescricao(textAreaBemDescricao.getText());
            bem.setData(datePickerBemData.getValue());
            bem.setInventario(inventarioSelecionado);

            bemDAO.create(bem);
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível adicionar o bem.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void atualizarBem(ActionEvent event) {
        Bem bemSelecionado = tabelaBensInventario.getSelectionModel().getSelectedItem();
        if (bemSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um bem para atualizar.", Alert.AlertType.WARNING);
            return;
        }

        try {
            bemSelecionado.setItem(textFieldBemItem.getText());
            bemSelecionado.setQuantidade(Integer.parseInt(textFieldBemQuantidade.getText()));
            bemSelecionado.setValor(Double.parseDouble(textFieldBemValor.getText()));
            bemSelecionado.setDescricao(textAreaBemDescricao.getText());
            bemSelecionado.setData(datePickerBemData.getValue());

            bemDAO.update(bemSelecionado);
            atualizarTabela();
            limparCampos();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível atualizar o bem.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void excluirBem(ActionEvent event) {
        Bem bemSelecionado = tabelaBensInventario.getSelectionModel().getSelectedItem();
        if (bemSelecionado == null) {
            mostrarAlerta("Seleção necessária", "Selecione um bem para excluir.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION, "Deseja realmente excluir este bem?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> resultado = confirmacao.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.YES) {
            bemDAO.delete(bemSelecionado.getId());
            atualizarTabela();
            limparCampos();
        }
    }

    private void limparCampos() {
        textFieldBemItem.clear();
        textFieldBemQuantidade.clear();
        textFieldBemValor.clear();
        textAreaBemDescricao.clear();
        datePickerBemData.setValue(null);
    }

    private void mostrarAlerta(String titulo, String mensagem, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
