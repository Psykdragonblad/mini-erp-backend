package com.minierp.backend.controller;

import com.minierp.backend.dto.ProdutoRequestDTO;
import com.minierp.backend.dto.ProdutoResponseDTO;
import com.minierp.backend.model.Produto;
import com.minierp.backend.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoRequestDTO requestDTO) {
        Produto produto = new Produto();
        produto.setNome(requestDTO.getNome());
        produto.setPreco(requestDTO.getPreco());
        produto.setQuantidadeEstoque(requestDTO.getQuantidadeEstoque());

        Produto salvo = produtoRepository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProdutoResponseDTO(salvo));
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        List<ProdutoResponseDTO> produtos = produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(produtos);
    }
}