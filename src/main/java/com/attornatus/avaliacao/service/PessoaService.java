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
            pessoa.setNumeroCadastro();
            pessoaSalva = repository.save(pessoa);
        }catch (Exception ex){
            throw new CadastroInvalidoException("Cadastro inválido");
        }
        return new ResponseEntity(mapper.convertValue(pessoaSalva, PessoaResponseDTO.class), HttpStatus.OK);

    }


    public ResponseEntity listarPessoas () throws ResourceNotFoundException {
        List<Pessoa> pessoaList = repository.findAll();
        if(pessoaList.isEmpty()){
            throw  new ResourceNotFoundException("Nenhuma pessoa foi cadastrada.");
        }

        List<PessoaResponseDTO> pessoaResponseDTOList = new ArrayList<>();

        for (Pessoa pessoa : pessoaList){
            pessoaResponseDTOList.add(mapper.convertValue(pessoa, PessoaResponseDTO.class));
        }
        return new ResponseEntity(pessoaResponseDTOList,HttpStatus.OK);
    }

    public void excluir(String numeroCadastro) throws ResourceNotFoundException {
        try{
            Pessoa pessoa = repository.findByNumeroCadastro(numeroCadastro);
            repository.delete(pessoa);
        }catch (Exception exception){

            throw  new ResourceNotFoundException("Pessoa não encontrada");
        }
    }

    public ResponseEntity buscaPorNumeroCadastro (String numeroCadastro) throws ResourceNotFoundException {
        Pessoa pessoa = repository.findByNumeroCadastro(numeroCadastro);
        if(pessoa == null){
            throw  new ResourceNotFoundException("Pessoa não encontrado");
        }
        return new ResponseEntity(mapper.convertValue(pessoa,PessoaResponseDTO.class), HttpStatus.OK);

    }

    public ResponseEntity editar(PessoaResponseDTO pessoaResponseDTO) throws ResourceNotFoundException {

        try {
            Pessoa pessoaEditada = repository.findByNumeroCadastro(pessoaResponseDTO.getNumeroCadastro());
            if (pessoaResponseDTO.getNome() != null) {
                pessoaEditada.setNome(pessoaResponseDTO.getNome());
            }
            if (pessoaResponseDTO.getDataNascimento() != null) {
                pessoaEditada.setDataNascimento(pessoaResponseDTO.getDataNascimento());
            }
            if (pessoaResponseDTO.getEnderecoPrincipal() != null) {
                pessoaEditada.setEnderecoPrincipal(pessoaResponseDTO.getEnderecoPrincipal());
            }
            return new ResponseEntity(mapper.convertValue(repository.save(pessoaEditada),PessoaResponseDTO.class), HttpStatus.OK);
        } catch (Exception ex) {
            throw new ResourceNotFoundException("Erro ao editar pessoa.");
        }
    }
    }
