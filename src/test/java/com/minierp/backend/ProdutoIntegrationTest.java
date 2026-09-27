package com.minierp.backend;

import com.minierp.backend.dto.ProdutoRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasItem;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void devePersistirEListarProdutosNoBancoReal() throws Exception {
        ProdutoRequestDTO request = new ProdutoRequestDTO();
        request.setNome("Caderno Espiral");
        request.setPreco(new BigDecimal("18.90"));
        request.setQuantidadeEstoque(50);

        // 1. Cadastra o produto (Passa pelo Controller, JPA e Banco)
        mockMvc.perform(post("/produtos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Caderno Espiral"));

        // 2. Lista os produtos e valida se veio do banco
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$[*].nome", hasItem("Caderno Espiral")));
    }
}