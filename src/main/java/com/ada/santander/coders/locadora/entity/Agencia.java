package com.ada.santander.coders.locadora.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "AGENCIA")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TAMANHO_MAXIMO_DA_FROTA")
    private int tamanhoMaximoDaFrota;

    @OneToOne
    @JoinColumn(name = "ENDERECO_ID", referencedColumnName = "id")
    private Endereco endereco;

    @OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "VEICULO_ID", referencedColumnName = "id")
    private List<Veiculo> veiculos;
}
