package com.attornatus.avaliacao;

import com.attornatus.avaliacao.entity.Endereco;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.ArrayList;
import java.util.Date;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PessoaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testSalvar() throws Exception {

        Endereco endereco2 = new Endereco();
        endereco2.setCidade("Joinville");
        endereco2.setCep("89201-000");
        endereco2.setLogradouro("Rua 12 de julho");
        endereco2.setNumero("5555");

        PessoaRequestDTO pessoa2 = new PessoaRequestDTO();
        pessoa2.setDataNascimento(new Date());
        pessoa2.setNome("Marcos Silva");
        pessoa2.setEnderecoPrincipal(endereco2);



        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(pessoa2);

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

        Endereco endereco3 = new Endereco();
        endereco3.setCidade("Maranguape");
        endereco3.setCep("577899");
        endereco3.setLogradouro("Rua Edson Mil");
        endereco3.setNumero("645");

        PessoaRequestDTO pessoa3 = new PessoaRequestDTO();
        pessoa3.setDataNascimento(new Date());
        pessoa3.setNome("Tulio Cardoroso");
        pessoa3.setEnderecoPrincipal(endereco3);

        String requestJson = objectMapper.writeValueAsString(pessoa3);

        mockMvc.perform(post("/api")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andReturn();


        MvcResult resultList = mockMvc.perform(get("/api"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode jsonNode = objectMapper.readTree(resultList.getResponse().getContentAsString());
        ArrayList<PessoaResponseDTO> pessoaResponseDTOList = objectMapper.convertValue(jsonNode.get("body"), new TypeReference<>() {
        });

        PessoaResponseDTO pessoaResponseDTO =  pessoaResponseDTOList.get(0);
        pessoaResponseDTO.setNome("Brandom Silva");


        String requestJson2 = objectMapper.writeValueAsString(pessoaResponseDTO);

       mockMvc.perform(patch("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson2))
                .andExpect(status().isOk())
                .andReturn();


        Assertions.assertEquals("Brandom Silva",pessoaResponseDTOList.get(0).getNome());
    }


    }