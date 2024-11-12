package com.ada.santander.coders.locadora.entity;


import com.ada.santander.coders.locadora.entity.enums.TipoVeiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "VEICULO")
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MODELO")
    private String modelo;

    @Column(name = "PLACA")
    private String placa;

    @Column(name = "ANO")
    private int ano;

    @Column(name = "COR")
    private String cor;


    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_VEICULO", nullable = false)
    protected TipoVeiculo tipoVeiculo;

    @Column(name = "VEICULO_DISPONIVEL_PARA_LOCACAO")
    private boolean veiculoDisponivelParaLocacao;

    @ManyToOne
    @JoinColumn(name = "AGENCIA_ID")
    @JsonIgnore
    private Agencia agencia;
}
