package com.attornatus.avaliacao.service;

import com.attornatus.avaliacao.entity.Pessoa;
import com.attornatus.avaliacao.entity.dto.PessoaDTO;
import com.attornatus.avaliacao.repository.PessoaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository repository;
    ObjectMapper mapper = new ObjectMapper();

    public ResponseEntity salvar (PessoaDTO pesssoaDTO) {
        Pessoa pessoaSalva = null;
        ObjectMapper mapper = new ObjectMapper();
        Pessoa pessoa = mapper.convertValue(pesssoaDTO, Pessoa.class);

        pessoaSalva = repository.save(pessoa);

        return new ResponseEntity(mapper.convertValue(pessoaSalva,PessoaDTO.class), HttpStatus.OK);






    }





    public List<PessoaDTO> listarPessoas(){
        List<Pessoa> listPessoa = repository.findAll();
        List<PessoaDTO> listPessoaDTO = new ArrayList<>();

        for (Pessoa pessoa : listPessoa){
            listPessoaDTO.add(mapper.convertValue(pessoa, PessoaDTO.class));
        }
        return listPessoaDTO;
    }




}
