package com.ada.santander.coders.locadora.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AGENCIA")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CNPJ")
    private String cnpj;

    @Column(name = "RAZAOSOCIAL")
    private String razaoSocial;

    @Column(name = "NOMEFANTASIA")
    private String nomeFantasia;

    @Column(name = "TAMANHOMAXIMODAFROTA")
    private Integer tamanhoMaximoDaFrota;
}
