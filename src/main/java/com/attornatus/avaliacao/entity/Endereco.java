package com.attornatus.avaliacao.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Endereco {

    private String logradouro;
    private String cep;
    private String numero;
    private String cidade;

}
