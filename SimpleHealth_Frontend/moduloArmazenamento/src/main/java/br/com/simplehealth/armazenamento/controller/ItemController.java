package br.com.simplehealth.armazenamento.controller;

import br.com.simplehealth.armazenamento.model.*;
import br.com.simplehealth.armazenamento.service.ItemService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlador para gerenciamento de itens do estoque.
 * 
 * @version 1.0
 */
public class ItemController extends AbstractCrudController<Item, br.com.simplehealth.armazenamento.view.Item, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final ItemService itemService;

    // Campos da interface
    @FXML private TextField nomeField;
    @FXML private TextArea descricaoField;
    @FXML private ComboBox<String> tipoComboBox;
    @FXML private TextField unidadeMedidaField;
    @FXML private TextField quantidadeTotalField;
    @FXML private DatePicker validadeDatePicker;
    @FXML private TextField loteField;
    
    // Campos específicos para tipos
    @FXML private TextField prescricaoField;
    @FXML private TextArea composicaoField;
    @FXML private TextArea bulaField;
    @FXML private ComboBox<String> tarjaComboBox;
    @FXML private TextField modoConsumoField;
    @FXML private CheckBox descartavelCheckBox;
    @FXML private ComboBox<String> usoComboBox;
    @FXML private TextField alergenicosField;
    @FXML private ComboBox<String> tipoArmazenamentoComboBox;

    // Colunas da tabela
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, Long> idColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, String> nomeColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, String> tipoColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, String> unidadeMedidaColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, Integer> quantidadeColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, String> validadeColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Item, String> loteColumn;

    public ItemController() {
        this.itemService = new ItemService();
    }

    @Override
    public void initialize() {
        super.initialize();
        configurarComboBoxes();
        configurarColunas();
        configurarTipoListener();
    }

    private void configurarComboBoxes() {
        if (tipoComboBox != null) {
            tipoComboBox.getItems().addAll("MEDICAMENTO", "HOSPITALAR", "ALIMENTO");
        }
        
        if (tarjaComboBox != null) {
            tarjaComboBox.getItems().addAll("LIVRE", "VERMELHA", "PRETA");
        }
        
        if (usoComboBox != null) {
            usoComboBox.getItems().addAll("GERAL", "CIRÚRGICO", "CURATIVO");
        }
        
        if (tipoArmazenamentoComboBox != null) {
            tipoArmazenamentoComboBox.getItems().addAll("PERECÍVEL", "NÃO PERECÍVEL", "REFRIGERADO");
        }
    }

    private void configurarColunas() {
        if (idColumn != null) idColumn.setCellValueFactory(cellData -> cellData.getValue().idItemProperty().asObject());
        if (nomeColumn != null) nomeColumn.setCellValueFactory(cellData -> cellData.getValue().nomeProperty());
        if (tipoColumn != null) tipoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
        if (unidadeMedidaColumn != null) unidadeMedidaColumn.setCellValueFactory(cellData -> cellData.getValue().unidadeMedidaProperty());
        if (quantidadeColumn != null) quantidadeColumn.setCellValueFactory(cellData -> cellData.getValue().quantidadeTotalProperty().asObject());
        if (validadeColumn != null) validadeColumn.setCellValueFactory(cellData -> cellData.getValue().validadeProperty());
        if (loteColumn != null) loteColumn.setCellValueFactory(cellData -> cellData.getValue().loteProperty());
    }

    private void configurarTipoListener() {
        if (tipoComboBox != null) {
            tipoComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
                mostrarCamposEspecificos(newValue);
            });
        }
    }

    private void mostrarCamposEspecificos(String tipo) {
        // Ocultar todos os campos específicos primeiro
        setVisibilidadeCamposMedicamento(false);
        setVisibilidadeCamposHospitalar(false);
        setVisibilidadeCamposAlimento(false);

        // Mostrar campos específicos baseado no tipo
        switch (tipo) {
            case "MEDICAMENTO" -> setVisibilidadeCamposMedicamento(true);
            case "HOSPITALAR" -> setVisibilidadeCamposHospitalar(true);
            case "ALIMENTO" -> setVisibilidadeCamposAlimento(true);
        }
    }

    private void setVisibilidadeCamposMedicamento(boolean visivel) {
        if (prescricaoField != null) prescricaoField.setVisible(visivel);
        if (composicaoField != null) composicaoField.setVisible(visivel);
        if (bulaField != null) bulaField.setVisible(visivel);
        if (tarjaComboBox != null) tarjaComboBox.setVisible(visivel);
        if (modoConsumoField != null) modoConsumoField.setVisible(visivel);
    }

    private void setVisibilidadeCamposHospitalar(boolean visivel) {
        if (descartavelCheckBox != null) descartavelCheckBox.setVisible(visivel);
        if (usoComboBox != null) usoComboBox.setVisible(visivel);
    }

    private void setVisibilidadeCamposAlimento(boolean visivel) {
        if (alergenicosField != null) alergenicosField.setVisible(visivel);
        if (tipoArmazenamentoComboBox != null) tipoArmazenamentoComboBox.setVisible(visivel);
    }

    @Override
    protected List<Item> buscarTodosViaApi() throws IOException {
        return itemService.buscarTodos();
    }

    @Override
    protected Item buscarPorIdViaApi(Long id) throws IOException {
        return itemService.buscarPorId(id);
    }

    @Override
    protected Item criarViaApi(Item entidade) throws IOException {
        return itemService.criar(entidade);
    }

    @Override
    protected Item atualizarViaApi(Long id, Item entidade) throws IOException {
        return itemService.atualizar(id, entidade);
    }

    @Override
    protected boolean deletarViaApi(Long id) throws IOException {
        return itemService.deletar(id);
    }

    @Override
    protected br.com.simplehealth.armazenamento.view.Item modelToView(Item entidade) {
        String validadeStr = entidade.getValidade() != null ? 
            entidade.getValidade().format(DATE_FORMATTER) : "";
        
        return new br.com.simplehealth.armazenamento.view.Item(
            entidade.getIdItem(),
            entidade.getNome(),
            entidade.getTipo(),
            entidade.getUnidadeMedida(),
            entidade.getQuantidadeTotal(),
            validadeStr,
            entidade.getLote()
        );
    }

    @Override
    protected Item viewToModel(br.com.simplehealth.armazenamento.view.Item viewModel) {
        Item item = new Item();
        item.setIdItem(viewModel.getIdItem());
        item.setNome(viewModel.getNome());
        item.setTipo(viewModel.getTipo());
        item.setUnidadeMedida(viewModel.getUnidadeMedida());
        item.setQuantidadeTotal(viewModel.getQuantidadeTotal());
        item.setLote(viewModel.getLote());
        
        if (viewModel.getValidade() != null && !viewModel.getValidade().isEmpty()) {
            item.setValidade(LocalDate.parse(viewModel.getValidade(), DATE_FORMATTER));
        }
        
        return item;
    }

    @Override
    protected void preencherCampos(br.com.simplehealth.armazenamento.view.Item item) {
        if (nomeField != null) nomeField.setText(item.getNome());
        if (tipoComboBox != null) tipoComboBox.setValue(item.getTipo());
        if (unidadeMedidaField != null) unidadeMedidaField.setText(item.getUnidadeMedida());
        if (quantidadeTotalField != null) quantidadeTotalField.setText(String.valueOf(item.getQuantidadeTotal()));
        if (loteField != null) loteField.setText(item.getLote());
        
        if (validadeDatePicker != null && item.getValidade() != null && !item.getValidade().isEmpty()) {
            validadeDatePicker.setValue(LocalDate.parse(item.getValidade(), DATE_FORMATTER));
        }
        
        mostrarCamposEspecificos(item.getTipo());
        
        // Buscar dados específicos do tipo via API se necessário
        try {
            Item itemCompleto = buscarPorIdViaApi(item.getIdItem());
            preencherCamposEspecificos(itemCompleto);
        } catch (IOException e) {
            logger.error("Erro ao buscar dados específicos do item: ", e);
        }
    }

    private void preencherCamposEspecificos(Item item) {
        if (item instanceof Medicamento medicamento) {
            if (prescricaoField != null) prescricaoField.setText(medicamento.getPrescricao());
            if (composicaoField != null) composicaoField.setText(medicamento.getComposicao());
            if (bulaField != null) bulaField.setText(medicamento.getBula());
            if (tarjaComboBox != null) tarjaComboBox.setValue(medicamento.getTarja());
            if (modoConsumoField != null) modoConsumoField.setText(medicamento.getModoConsumo());
        } else if (item instanceof Hospitalar hospitalar) {
            if (descartavelCheckBox != null) descartavelCheckBox.setSelected(Boolean.TRUE.equals(hospitalar.getDescartavel()));
            if (usoComboBox != null) usoComboBox.setValue(hospitalar.getUso());
        } else if (item instanceof Alimento alimento) {
            if (alergenicosField != null) alergenicosField.setText(alimento.getAlergenicos());
            if (tipoArmazenamentoComboBox != null) tipoArmazenamentoComboBox.setValue(alimento.getTipoArmazenamento());
        }
    }

    @Override
    protected void limparCampos() {
        if (nomeField != null) nomeField.clear();
        if (descricaoField != null) descricaoField.clear();
        if (tipoComboBox != null) tipoComboBox.setValue(null);
        if (unidadeMedidaField != null) unidadeMedidaField.clear();
        if (quantidadeTotalField != null) quantidadeTotalField.clear();
        if (validadeDatePicker != null) validadeDatePicker.setValue(null);
        if (loteField != null) loteField.clear();
        
        // Limpar campos específicos
        if (prescricaoField != null) prescricaoField.clear();
        if (composicaoField != null) composicaoField.clear();
        if (bulaField != null) bulaField.clear();
        if (tarjaComboBox != null) tarjaComboBox.setValue(null);
        if (modoConsumoField != null) modoConsumoField.clear();
        if (descartavelCheckBox != null) descartavelCheckBox.setSelected(false);
        if (usoComboBox != null) usoComboBox.setValue(null);
        if (alergenicosField != null) alergenicosField.clear();
        if (tipoArmazenamentoComboBox != null) tipoArmazenamentoComboBox.setValue(null);
        
        mostrarCamposEspecificos(null);
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        if (nomeField != null) nomeField.setDisable(desabilitado);
        if (descricaoField != null) descricaoField.setDisable(desabilitado);
        if (tipoComboBox != null) tipoComboBox.setDisable(desabilitado);
        if (unidadeMedidaField != null) unidadeMedidaField.setDisable(desabilitado);
        if (quantidadeTotalField != null) quantidadeTotalField.setDisable(desabilitado);
        if (validadeDatePicker != null) validadeDatePicker.setDisable(desabilitado);
        if (loteField != null) loteField.setDisable(desabilitado);
        
        // Desabilitar campos específicos
        if (prescricaoField != null) prescricaoField.setDisable(desabilitado);
        if (composicaoField != null) composicaoField.setDisable(desabilitado);
        if (bulaField != null) bulaField.setDisable(desabilitado);
        if (tarjaComboBox != null) tarjaComboBox.setDisable(desabilitado);
        if (modoConsumoField != null) modoConsumoField.setDisable(desabilitado);
        if (descartavelCheckBox != null) descartavelCheckBox.setDisable(desabilitado);
        if (usoComboBox != null) usoComboBox.setDisable(desabilitado);
        if (alergenicosField != null) alergenicosField.setDisable(desabilitado);
        if (tipoArmazenamentoComboBox != null) tipoArmazenamentoComboBox.setDisable(desabilitado);
    }

    @Override
    protected Long getIdFromViewModel(br.com.simplehealth.armazenamento.view.Item viewModel) {
        return viewModel.getIdItem();
    }

    @Override
    protected boolean validarCampos() {
        return nomeField != null && !nomeField.getText().trim().isEmpty() &&
               tipoComboBox != null && tipoComboBox.getValue() != null &&
               unidadeMedidaField != null && !unidadeMedidaField.getText().trim().isEmpty();
    }

    @Override
    protected Item criarEntidadeDosCampos() {
        String tipo = tipoComboBox.getValue();
        
        return switch (tipo) {
            case "MEDICAMENTO" -> criarMedicamento();
            case "HOSPITALAR" -> criarHospitalar();
            case "ALIMENTO" -> criarAlimento();
            default -> criarItemGenerico();
        };
    }

    private Item criarItemGenerico() {
        Item item = new Item();
        preencherCamposComuns(item);
        return item;
    }

    private Medicamento criarMedicamento() {
        Medicamento medicamento = new Medicamento();
        preencherCamposComuns(medicamento);
        
        if (prescricaoField != null) medicamento.setPrescricao(prescricaoField.getText());
        if (composicaoField != null) medicamento.setComposicao(composicaoField.getText());
        if (bulaField != null) medicamento.setBula(bulaField.getText());
        if (tarjaComboBox != null) medicamento.setTarja(tarjaComboBox.getValue());
        if (modoConsumoField != null) medicamento.setModoConsumo(modoConsumoField.getText());
        
        return medicamento;
    }

    private Hospitalar criarHospitalar() {
        Hospitalar hospitalar = new Hospitalar();
        preencherCamposComuns(hospitalar);
        
        if (descartavelCheckBox != null) hospitalar.setDescartavel(descartavelCheckBox.isSelected());
        if (usoComboBox != null) hospitalar.setUso(usoComboBox.getValue());
        
        return hospitalar;
    }

    private Alimento criarAlimento() {
        Alimento alimento = new Alimento();
        preencherCamposComuns(alimento);
        
        if (alergenicosField != null) alimento.setAlergenicos(alergenicosField.getText());
        if (tipoArmazenamentoComboBox != null) alimento.setTipoArmazenamento(tipoArmazenamentoComboBox.getValue());
        
        return alimento;
    }

    private void preencherCamposComuns(Item item) {
        if (nomeField != null) item.setNome(nomeField.getText());
        if (descricaoField != null) item.setDescricao(descricaoField.getText());
        if (tipoComboBox != null) item.setTipo(tipoComboBox.getValue());
        if (unidadeMedidaField != null) item.setUnidadeMedida(unidadeMedidaField.getText());
        if (loteField != null) item.setLote(loteField.getText());
        if (validadeDatePicker != null) item.setValidade(validadeDatePicker.getValue());
        
        if (quantidadeTotalField != null && !quantidadeTotalField.getText().trim().isEmpty()) {
            try {
                item.setQuantidadeTotal(Integer.parseInt(quantidadeTotalField.getText()));
            } catch (NumberFormatException e) {
                item.setQuantidadeTotal(0);
            }
        }
    }
}