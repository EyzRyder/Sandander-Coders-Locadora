package com.ada.santander.coders.locadora.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAMANHO_MAXIMO_DA_FROTA")
    private int tamanhoMaximoDaFrota;

    @OneToOne
    @JoinColumn(name = "ENDERECO_ID", referencedColumnName = "CEP")
    private Endereco endereco;

    @JsonIgnore
    @OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Veiculo> veiculos;
}
