package com.ada.santander.coders.locadora.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "VEICULO")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "PLACA")
    private String placa;

    @Column(name = "ANO")
    private int ano;

    @Column(name = "COR")
    private String cor;

    @Column(name = "AGENCIACNPJ")
    private String agenciaCNPJ;

    @Column(name = "TIPOVEICULO")
    private String tipoVeiculo;

    @Column(name = "VEICULODISPONIVELPARALOCACAO")
    private boolean veiculoDisponivelParaLocacao;
}
