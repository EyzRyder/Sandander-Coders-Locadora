package com.ada.santander.coders.locadora.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Comprovante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "AGENCIA", nullable = false)
    private Agencia locadora;

    @ManyToOne
    @JoinColumn(name = "CLIENTE", nullable = false)
    private Cliente locatario;

    @ManyToOne
    @JoinColumn(name = "VEICULO")
    private Veiculo veiculo;

    @Column(name = "DATA_HORA_ALUGUEL", nullable = false)
    private LocalDateTime dataHoraAluguel;

}
