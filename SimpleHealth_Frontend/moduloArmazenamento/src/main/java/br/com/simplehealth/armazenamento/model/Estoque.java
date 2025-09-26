package br.com.simplehealth.armazenamento.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que representa um estoque.
 * 
 * @version 1.0
 */
public class Estoque {
    
    @JsonProperty("id_estoque")
    private Long idEstoque;
    
    @JsonProperty("local")
    private String local;
    
    @JsonProperty("itens")
    private List<Item> itens;

    // Construtores
    public Estoque() {}

    public Estoque(String local) {
        this.local = local;
    }

    // Getters e Setters
    public Long getIdEstoque() { return idEstoque; }
    public void setIdEstoque(Long idEstoque) { this.idEstoque = idEstoque; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public List<Item> getItens() { return itens; }
    public void setItens(List<Item> itens) { this.itens = itens; }

    /**
     * Método para obter um item específico pelo ID.
     * @param idItem ID do item
     * @return O item encontrado ou null se não existir
     */
    public Item getItem(Long idItem) {
        if (itens != null) {
            return itens.stream()
                    .filter(item -> item.getIdItem().equals(idItem))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Estoque{" +
                "idEstoque=" + idEstoque +
                ", local='" + local + '\'' +
                ", quantidadeItens=" + (itens != null ? itens.size() : 0) +
                '}';
    }
}