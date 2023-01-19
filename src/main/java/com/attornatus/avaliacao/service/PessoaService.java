package com.attornatus.avaliacao.service;

import com.attornatus.avaliacao.entity.Pessoa;
import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.exception.CadastroInvalidoException;
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

    public ResponseEntity salvar (PessoaRequestDTO pesssoaDTO) throws CadastroInvalidoException {
        Pessoa pessoaSalva = null;
        ObjectMapper mapper = new ObjectMapper();
        Pessoa pessoa = mapper.convertValue(pesssoaDTO, Pessoa.class);

        try{
            pessoaSalva = repository.save(pessoa);
        }catch (Exception ex){
            throw new CadastroInvalidoException("Cadastro inválido");
        }
        return new ResponseEntity(mapper.convertValue(pessoaSalva, PessoaRequestDTO.class), HttpStatus.OK);








    }





    public List<PessoaRequestDTO> listarPessoas(){
        List<Pessoa> listPessoa = repository.findAll();
        List<PessoaRequestDTO> listPessoaRequestDTO = new ArrayList<>();

        for (Pessoa pessoa : listPessoa){
            listPessoaRequestDTO.add(mapper.convertValue(pessoa, PessoaRequestDTO.class));
        }
        return listPessoaRequestDTO;
    }




}
