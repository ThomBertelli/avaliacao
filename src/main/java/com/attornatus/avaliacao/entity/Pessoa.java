package com.attornatus.avaliacao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco",nullable = false)
    private Endereco enderecoPrincipal;

    public void setEnderecoPrincipal(Endereco endereco){
        this.enderecoPrincipal = endereco;
        if (!enderecos.contains(endereco)) {
            enderecos.add(endereco);
        }
    }


}
