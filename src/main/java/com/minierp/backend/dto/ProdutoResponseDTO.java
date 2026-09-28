package com.minierp.backend.dto;

import com.minierp.backend.model.Produto;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private LocalDateTime criadoEm;

    public ProdutoResponseDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.preco = produto.getPreco();
        this.quantidadeEstoque = produto.getQuantidadeEstoque();
        this.criadoEm = produto.getCriado_em();
    }

    // Getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getPreco() { return preco; }
    public Integer getQuantidadeEstoque() { return quantidadeEstoque; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
    public String getDescricao() { return descricao; }
}