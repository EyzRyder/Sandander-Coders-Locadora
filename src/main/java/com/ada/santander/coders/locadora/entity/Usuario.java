package com.ada.santander.coders.locadora.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="USUARIO")
public class Usuario {
    private String nome;
    private String telefone;
    private String email;
    private String tipo;
    private String senha;
}
