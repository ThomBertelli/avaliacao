package com.attornatus.avaliacao.controller;


import com.attornatus.avaliacao.entity.dto.PessoaRequestDTO;
import com.attornatus.avaliacao.entity.dto.PessoaResponseDTO;
import com.attornatus.avaliacao.exception.CadastroInvalidoException;
import com.attornatus.avaliacao.exception.handlers.ResourceNotFoundException;
import com.attornatus.avaliacao.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody PessoaRequestDTO pessoaRequestDTO) throws CadastroInvalidoException {
        return pessoaService.salvar(pessoaRequestDTO);
    }

    @GetMapping
    public ResponseEntity buscar(@RequestParam(value = "numeroCadastro", required = false)String numeroCadastro) throws ResourceNotFoundException {
        if(numeroCadastro != null) {
            return new ResponseEntity(pessoaService.buscaPorNumeroCadastro(numeroCadastro), HttpStatus.OK);
        } else {
            return new ResponseEntity(pessoaService.listarPessoas(), HttpStatus.OK);
        }
    }

    @DeleteMapping
    public void excluir(@RequestParam("numeroCadastro") String numeroCadastro) throws ResourceNotFoundException {
        pessoaService.excluir(numeroCadastro);}

    @PatchMapping
    public ResponseEntity editar(@RequestBody PessoaResponseDTO pessoaResponseDTO) throws ResourceNotFoundException {
        return pessoaService.editar(pessoaResponseDTO);
    }


}
