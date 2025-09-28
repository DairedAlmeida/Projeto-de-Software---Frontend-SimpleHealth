package br.com.simplehealth.armazenamento.controller;

import br.com.simplehealth.armazenamento.model.Estoque;
import br.com.simplehealth.armazenamento.model.Item;
import br.com.simplehealth.armazenamento.service.EstoqueService;
import br.com.simplehealth.armazenamento.service.ItemService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Controlador para operações CRUD de Estoque.
 */
public class EstoqueController extends AbstractCrudController<Estoque, br.com.simplehealth.armazenamento.view.Estoque, Long> {
    
    private static final Logger logger = LoggerFactory.getLogger(EstoqueController.class);

    // Campos do formulário
    @FXML private TextField localField;
    
    // Lista de itens do estoque
    @FXML private ListView<Item> itensListView;
    @FXML private ComboBox<Item> itensComboBox;
    @FXML private Button adicionarItemButton;
    @FXML private Button removerItemButton;
    
    // Colunas da tabela
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Estoque, Long> idColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Estoque, String> localColumn;
    @FXML private TableColumn<br.com.simplehealth.armazenamento.view.Estoque, Integer> quantidadeItensColumn;
    
    // Container do formulário
    @FXML private VBox formularioContainer;
    
    private final EstoqueService estoqueService;
    private final ItemService itemService;
    private ObservableList<Item> itensDoEstoque;
    private ObservableList<Item> itensDisponiveis;

    public EstoqueController() {
        this.estoqueService = new EstoqueService();
        this.itemService = new ItemService();
    }

    @Override
    public void initialize() {
        super.initialize();
        configurarColunas();
        configurarListaItens();
        inicializarObservableLists();
        carregarItensDisponiveis();
    }
    
    private void configurarColunas() {
        if (idColumn != null) idColumn.setCellValueFactory(cellData -> cellData.getValue().idEstoqueProperty().asObject());
        if (localColumn != null) localColumn.setCellValueFactory(cellData -> cellData.getValue().localProperty());
        if (quantidadeItensColumn != null) quantidadeItensColumn.setCellValueFactory(cellData -> cellData.getValue().quantidadeItensProperty().asObject());
    }
    
    private void configurarListaItens() {
        if (itensListView != null) {
            itensListView.setCellFactory(param -> new ListCell<Item>() {
                @Override
                protected void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome() + " - " + item.getTipo());
                    }
                }
            });
        }
        
        if (itensComboBox != null) {
            itensComboBox.setCellFactory(param -> new ListCell<Item>() {
                @Override
                protected void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome() + " - " + item.getTipo());
                    }
                }
            });
            
            itensComboBox.setButtonCell(new ListCell<Item>() {
                @Override
                protected void updateItem(Item item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getNome() + " - " + item.getTipo());
                    }
                }
            });
        }
        
        // Configurar botões
        if (adicionarItemButton != null) {
            adicionarItemButton.setOnAction(e -> adicionarItem());
        }
        if (removerItemButton != null) {
            removerItemButton.setOnAction(e -> removerItem());
        }
    }
    
    private void inicializarObservableLists() {
        itensDoEstoque = FXCollections.observableArrayList();
        itensDisponiveis = FXCollections.observableArrayList();
        
        if (itensListView != null) {
            itensListView.setItems(itensDoEstoque);
        }
        if (itensComboBox != null) {
            itensComboBox.setItems(itensDisponiveis);
        }
    }
    
    private void carregarItensDisponiveis() {
        try {
            List<Item> itens = itemService.buscarTodos();
            itensDisponiveis.setAll(itens);
        } catch (IOException e) {
            logger.error("Erro ao carregar itens disponíveis", e);
        }
    }

    // Implementação dos métodos abstratos
    
    @Override
    protected List<Estoque> buscarTodosViaApi() throws IOException {
        return estoqueService.buscarTodos();
    }

    @Override
    protected Estoque buscarPorIdViaApi(Long id) throws IOException {
        return estoqueService.buscarPorId(id);
    }

    @Override
    protected Estoque criarViaApi(Estoque estoque) throws IOException {
        return estoqueService.criar(estoque);
    }

    @Override
    protected Estoque atualizarViaApi(Long id, Estoque estoque) throws IOException {
        return estoqueService.atualizar(id, estoque);
    }

    @Override
    protected boolean deletarViaApi(Long id) throws IOException {
        return estoqueService.deletar(id);
    }

    @Override
    protected br.com.simplehealth.armazenamento.view.Estoque modelToView(Estoque estoque) {
        int quantidadeItens = estoque.getItens() != null ? estoque.getItens().size() : 0;
        return new br.com.simplehealth.armazenamento.view.Estoque(
            estoque.getIdEstoque(),
            estoque.getLocal(),
            quantidadeItens
        );
    }

    @Override
    protected Estoque viewToModel(br.com.simplehealth.armazenamento.view.Estoque estoqueView) {
        Estoque estoque = new Estoque();
        estoque.setIdEstoque(estoqueView.getIdEstoque());
        estoque.setLocal(estoqueView.getLocal());
        return estoque;
    }

    @Override
    protected void preencherCampos(br.com.simplehealth.armazenamento.view.Estoque estoqueView) {
        if (localField != null) {
            localField.setText(estoqueView.getLocal());
        }
        
        // Carregar os itens do estoque
        try {
            Estoque estoque = buscarPorIdViaApi(estoqueView.getIdEstoque());
            if (estoque != null && estoque.getItens() != null) {
                itensDoEstoque.setAll(estoque.getItens());
            } else {
                itensDoEstoque.clear();
            }
        } catch (IOException e) {
            logger.error("Erro ao carregar itens do estoque", e);
            itensDoEstoque.clear();
        }
    }

    @Override
    protected void limparCampos() {
        if (localField != null) {
            localField.clear();
        }
        itensDoEstoque.clear();
    }

    @Override
    protected void desabilitarCampos(boolean desabilitado) {
        if (localField != null) {
            localField.setDisable(desabilitado);
        }
        if (itensComboBox != null) {
            itensComboBox.setDisable(desabilitado);
        }
        if (adicionarItemButton != null) {
            adicionarItemButton.setDisable(desabilitado);
        }
        if (removerItemButton != null) {
            removerItemButton.setDisable(desabilitado);
        }
    }

    @Override
    protected Long getIdFromViewModel(br.com.simplehealth.armazenamento.view.Estoque estoqueView) {
        return estoqueView.getIdEstoque();
    }

    @Override
    protected boolean validarCampos() {
        if (localField == null || localField.getText() == null || localField.getText().trim().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    protected Estoque criarEntidadeDosCampos() {
        Estoque estoque = new Estoque();
        if (localField != null) {
            estoque.setLocal(localField.getText().trim());
        }
        estoque.setItens(List.copyOf(itensDoEstoque));
        return estoque;
    }
    
    @FXML
    private void adicionarItem() {
        Item itemSelecionado = itensComboBox.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null && !itensDoEstoque.contains(itemSelecionado)) {
            itensDoEstoque.add(itemSelecionado);
            itensComboBox.getSelectionModel().clearSelection();
        }
    }
    
    @FXML
    private void removerItem() {
        Item itemSelecionado = itensListView.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            itensDoEstoque.remove(itemSelecionado);
        }
    }
}