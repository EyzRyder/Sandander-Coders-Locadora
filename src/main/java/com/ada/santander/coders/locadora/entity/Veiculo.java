package com.ada.santander.coders.locadora.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="VEICULO")
public class Veiculo {
    private String modelo;
    private String placa;
    private int ano;
    private String cor;
    private String agenciaCNPJ;
    private String tipoVeiculo;
    private boolean veiculoDisponivelParaLocacao;
}
