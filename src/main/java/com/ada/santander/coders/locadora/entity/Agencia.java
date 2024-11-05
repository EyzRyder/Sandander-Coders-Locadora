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

    @Column(name = "RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "NOME_FANTASIA")
    private String nomeFantasia;

    @Column(name = "TAMANHO_MAXIMO_DA_FROTA")
    private Integer tamanhoMaximoDaFrota;
}
