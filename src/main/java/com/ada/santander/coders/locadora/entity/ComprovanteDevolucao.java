package com.ada.santander.coders.locadora.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@Entity
@Table(name = "COMPROVANTE_DE_DEVOLUÇÃO")
public class ComprovanteDevolucao extends Comprovante{

    @Column(name = "DATA_HORA_DEVOLUCAO", nullable = false)
    private LocalDateTime dataHoraDevolucao;

    @Column(name = "VALOR_DA_LOCACAO", nullable = false)
    private BigDecimal valorDaLocacao;

}
