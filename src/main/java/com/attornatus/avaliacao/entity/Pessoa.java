package com.attornatus.avaliacao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private Date dataNascimento;

    @OneToMany
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco",nullable = false)
    private Endereco enderecoPrincipal;

    @Column(nullable = false, unique = true)
    private String numeroCadastro;

    public void setEnderecoPrincipal(Endereco endereco){
        this.enderecoPrincipal = endereco;
        if (!enderecos.contains(endereco)) {
            enderecos.add(endereco);
        }
    }

    public void setNumeroCadastro(){
        this.numeroCadastro = UUID.randomUUID().toString();
    }

}
