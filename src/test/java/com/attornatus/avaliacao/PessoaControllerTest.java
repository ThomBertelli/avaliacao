package com.attornatus.avaliacao;

import com.attornatus.avaliacao.entity.Endereco;
import com.attornatus.avaliacao.entity.Pessoa;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.attornatus.avaliacao.service.PessoaService;
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
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PessoaService pessoaService;


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

        MvcResult result= mockMvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        PessoaResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), PessoaResponseDTO.class);

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

    @Test
    public void testBuscaNumeroCadastro() throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult resultList = mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(resultList.getResponse().getContentAsString());
        ArrayList<PessoaResponseDTO> pessoaResponseDTOList = objectMapper.convertValue(jsonNode.get("body"), new TypeReference<>() {
        });

        String numeroCadastro =  pessoaResponseDTOList.get(0).getNumeroCadastro();


        String uri = "/api?numeroCadastro=" + numeroCadastro ;
        mockMvc.perform(MockMvcRequestBuilders.get(uri))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body.nome").value(pessoaResponseDTOList.get(0).getNome()))
                .andReturn();

    }

    @Test
    public void testExcluir() throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult resultList = mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(resultList.getResponse().getContentAsString());
        ArrayList<PessoaResponseDTO> pessoaResponseDTOList = objectMapper.convertValue(jsonNode.get("body"), new TypeReference<>() {
        });

        String numeroCadastro =  pessoaResponseDTOList.get(0).getNumeroCadastro();


        String uri = "/api?numeroCadastro=" + numeroCadastro ;
        mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andExpect(status().isOk())
                .andReturn();

        String uri2 = "/api?numeroCadastro=" + numeroCadastro ;
        mockMvc.perform(MockMvcRequestBuilders.get(uri2))
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    public void testEditar() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();

        MvcResult resultList = mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(resultList.getResponse().getContentAsString());
        ArrayList<PessoaResponseDTO> pessoaResponseDTOList = objectMapper.convertValue(jsonNode.get("body"), new TypeReference<>() {
        });

        PessoaResponseDTO pessoaResponseDTO =  pessoaResponseDTOList.get(0);
        pessoaResponseDTO.setNome("Brandom Silva");


        String requestJson = objectMapper.writeValueAsString(pessoaResponseDTO);

        MvcResult result= mockMvc.perform(patch("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();


        Assertions.assertEquals("Brandom Silva",pessoaResponseDTOList.get(0).getNome());
    }


    }