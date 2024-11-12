package com.ada.santander.coders.locadora.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "AGENCIA")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Agencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TAMANHO_MAXIMO_DA_FROTA")
    private int tamanhoMaximoDaFrota;

    @OneToOne
    @JoinColumn(name = "ENDERECO_ID", referencedColumnName = "CEP")
    private Endereco endereco;

    @OneToMany(mappedBy = "agencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Veiculo> veiculos = new ArrayList<>();
}
