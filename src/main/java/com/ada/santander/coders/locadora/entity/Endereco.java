package com.ada.santander.coders.locadora.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CEP")
    private int cep;
    @Column(name = "LOGRADOURO")
    private String logradouro;
    @Column(name = "BAIRRO")
    private String bairro;
    @Column(name = "UF")
    private String uf;
    @Column(name = "CIDADE")
    private String cidade;
    @Column(name = "REGIAO")
    private String regiao;
}
