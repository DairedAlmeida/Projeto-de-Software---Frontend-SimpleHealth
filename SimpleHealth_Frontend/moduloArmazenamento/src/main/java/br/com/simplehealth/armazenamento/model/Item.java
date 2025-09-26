package br.com.simplehealth.armazenamento.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

/**
 * Classe base para todos os itens do estoque.
 * Representa um item genérico com propriedades comuns.
 * 
 * @version 1.0
 */
public class Item {
    
    @JsonProperty("id_item")
    private Long idItem;
    
    @JsonProperty("nome")
    private String nome;
    
    @JsonProperty("descricao")
    private String descricao;
    
    @JsonProperty("tipo")
    private String tipo;
    
    @JsonProperty("unidade_medida")
    private String unidadeMedida;
    
    @JsonProperty("quantidade_total")
    private Integer quantidadeTotal;
    
    @JsonProperty("validade")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validade;
    
    @JsonProperty("lote")
    private String lote;

    // Construtores
    public Item() {}

    public Item(String nome, String descricao, String tipo, String unidadeMedida, 
                Integer quantidadeTotal, LocalDate validade, String lote) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
        this.unidadeMedida = unidadeMedida;
        this.quantidadeTotal = quantidadeTotal;
        this.validade = validade;
        this.lote = lote;
    }

    // Getters e Setters
    public Long getIdItem() { return idItem; }
    public void setIdItem(Long idItem) { this.idItem = idItem; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public Integer getQuantidadeTotal() { return quantidadeTotal; }
    public void setQuantidadeTotal(Integer quantidadeTotal) { this.quantidadeTotal = quantidadeTotal; }

    public LocalDate getValidade() { return validade; }
    public void setValidade(LocalDate validade) { this.validade = validade; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    @Override
    public String toString() {
        return "Item{" +
                "idItem=" + idItem +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", quantidadeTotal=" + quantidadeTotal +
                '}';
    }
}