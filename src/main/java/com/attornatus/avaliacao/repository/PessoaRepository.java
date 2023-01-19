package com.attornatus.avaliacao.repository;

import com.attornatus.avaliacao.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Pessoa findByNumeroCadastro(String numeroCadastro);

}
