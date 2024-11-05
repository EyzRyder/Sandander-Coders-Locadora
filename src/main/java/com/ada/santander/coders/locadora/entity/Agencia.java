package com.ada.santander.coders.locadora.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="AGENCIA")
public class Agencia {
    private String cnpj;
    private String razaoSocial;
    private String nomeFantasia;
    private Integer tamanhoMaximoDaFrota;
}
