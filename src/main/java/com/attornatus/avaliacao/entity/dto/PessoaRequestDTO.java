package com.attornatus.avaliacao.entity.dto;

import com.attornatus.avaliacao.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PessoaRequestDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private Date dataNascimento;

    @NotBlank
    private List<Endereco> enderecos = new ArrayList<>();
    @NotBlank
    private Endereco enderecoPrincipal;



}
