package com.app.projetoBeeTech.controller.agente;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AgenteProducaoController {

    @FXML
    private DatePicker DatePickerDataProducao;

    @FXML
    private Button buttonAdicionarProducao;

    @FXML
    private Button buttonAtualizarProducao;

    @FXML
    private Button buttonExcluirProducao;

    @FXML
    private TableColumn<?, ?> colunaDataProducao;

    @FXML
    private TableColumn<?, ?> colunaQuantidadeProducao;

    @FXML
    private TableColumn<?, ?> colunaTipoProducao;

    @FXML
    private ComboBox<?> comboBoxApiario;

    @FXML
    private ComboBox<?> comboBoxApicultor;

    @FXML
    private ComboBox<?> comboBoxCaixa;

    @FXML
    private ComboBox<?> comboBoxTipoProducao;

    @FXML
    private TableView<?> tabelaProducao;

    @FXML
    private TextField textFieldQuantidadeProducao;

    @FXML
    void adicionarProducao(ActionEvent event) {

    }

    @FXML
    void atualizarProducao(ActionEvent event) {

    }

    @FXML
    void excluirProducao(ActionEvent event) {

    }

}
