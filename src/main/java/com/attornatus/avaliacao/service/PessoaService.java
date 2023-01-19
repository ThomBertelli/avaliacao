package com.attornatus.avaliacao.service;

import com.attornatus.avaliacao.entity.Pessoa;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.attornatus.avaliacao.exception.CadastroInvalidoException;
import com.attornatus.avaliacao.exception.handlers.ResourceNotFoundException;
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

    public ResponseEntity salvar (PessoaRequestDTO pesssoaRequestDTO) throws CadastroInvalidoException {
        Pessoa pessoaSalva = null;
        ObjectMapper mapper = new ObjectMapper();
        Pessoa pessoa = mapper.convertValue(pesssoaRequestDTO, Pessoa.class);

        try{
            pessoaSalva = repository.save(pessoa);
        }catch (Exception ex){
            throw new CadastroInvalidoException("Cadastro inválido");
        }
        return new ResponseEntity(mapper.convertValue(pessoaSalva, PessoaResponseDTO.class), HttpStatus.OK);

    }


    public ResponseEntity listarPessoas () throws ResourceNotFoundException {
        List<Pessoa> pessoaList = repository.findAll();
        if(pessoaList.isEmpty()){
            throw  new ResourceNotFoundException("Nenhuma pessoa foi cadastrada.")
        }

        List<PessoaResponseDTO> pessoaResponseDTOList = new ArrayList<>();

        for (Pessoa pessoa : pessoaList){
            pessoaResponseDTOList.add(mapper.convertValue(pessoa, PessoaResponseDTO.class));
        }
        return new ResponseEntity(pessoaResponseDTOList,HttpStatus.OK);
    }




}
