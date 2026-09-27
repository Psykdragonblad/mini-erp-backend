package com.minierp.backend;

import com.minierp.backend.controller.ProdutoController;
import com.minierp.backend.dto.ProdutoRequestDTO;
import com.minierp.backend.model.Produto;
import com.minierp.backend.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoRepository produtoRepository;

    @Test
    public void deveRetornarCriadoAoCadastrarProdutoValido() throws Exception {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Caneta Azul");
        request.setPreco(new BigDecimal("2.50"));
        request.setQuantidadeEstoque(100);

        Produto produtoSalvo = new Produto();
        produtoSalvo.setId(1L);
        produtoSalvo.setNome(request.getNome());
        produtoSalvo.setPreco(request.getPreco());
        produtoSalvo.setQuantidadeEstoque(request.getQuantidadeEstoque());
        produtoSalvo.setCriado_em(LocalDateTime.now());

        when(produtoRepository.save(ArgumentMatchers.any(Produto.class))).thenReturn(produtoSalvo);

        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Caneta Azul"));
    }
}