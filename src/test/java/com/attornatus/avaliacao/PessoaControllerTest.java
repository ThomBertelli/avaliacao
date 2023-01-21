package com.attornatus.avaliacao;

import com.attornatus.avaliacao.entity.Endereco;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    void testSalvar() throws Exception {

        Endereco endereco1 = new Endereco();
        endereco1.setCidade("Joinville");
        endereco1.setCep("89201-000");
        endereco1.setLogradouro("Rua 12 de julho");
        endereco1.setNumero("5555");

        PessoaRequestDTO pessoa1 = new PessoaRequestDTO();
        pessoa1.setDataNascimento(new Date());
        pessoa1.setNome("Marcos Silva");
        pessoa1.setEnderecoPrincipal(endereco1);



        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(pessoa1);

        // When
        MvcResult result;
        result = mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        // Then
        PessoaResponseDTO response;
        response = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaResponseDTO.class);

        assertEquals(null, response.getNome(), "Marcos Silva");
    }



    @Test
    void testListarPessoas() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult result = mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(result.getResponse().getContentAsString());
        ArrayList<PessoaResponseDTO> pessoaResponseDTOList = objectMapper.convertValue(jsonNode.get("body"), new TypeReference<>() {
        });


        Assertions.assertTrue(pessoaResponseDTOList.size() > 0);
        Assertions.assertEquals("Marcos Silva", pessoaResponseDTOList.get(0).getNome());

    }

//    @Test
//    void testBuscarPorNumeroCadastro() throws Exception {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        // Given
//        Pessoa pessoa = new Pessoa("Jane Doe", LocalDate.of(1985, 2, 2));
//        repository.save(pessoa);
//        String numeroCadastro = pessoa.getNumeroCadastro();
//
//        // When
//        MvcResult result = mockMvc.perform(get("/api")
//                        .param("numeroCadastro", numeroCadastro))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        // Then
//        PessoaResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaResponseDTO.class);
//
//
//    }

}