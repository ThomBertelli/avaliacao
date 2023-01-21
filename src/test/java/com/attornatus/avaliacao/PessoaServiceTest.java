package com.attornatus.avaliacao;

import com.attornatus.avaliacao.entity.Endereco;
import com.attornatus.avaliacao.entity.Pessoa;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.attornatus.avaliacao.service.PessoaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PessoaService pessoaService;


    @Test
    public void testListarPessoas() throws Exception {
        Endereco endereco1 = new Endereco();
        endereco1.setCidade("Joinville");
        endereco1.setCep("89201-000");
        endereco1.setLogradouro("Rua 12 de julho");
        endereco1.setNumero("5555");

        PessoaRequestDTO pessoa1 = new PessoaRequestDTO();
        pessoa1.setDataNascimento(new Date());
        pessoa1.setNome("Marcos Silva");
        pessoa1.setEnderecoPrincipal(endereco1);
        pessoa1.setNumeroCadastro("5443534534");

        pessoaService.salvar(pessoa1);



        MvcResult mvcResult =
                this.mockMvc.perform(MockMvcRequestBuilders.get("/api"))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                        .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        PessoaResponseDTO[] pessoaResponseDTOArray = mapper.readValue(mvcResult.getResponse().getContentAsString(), PessoaResponseDTO[].class);

        Assertions.assertTrue(pessoaResponseDTOArray.length > 0);
    }



}