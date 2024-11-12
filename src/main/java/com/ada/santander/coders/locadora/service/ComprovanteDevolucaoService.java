package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import com.ada.santander.coders.locadora.entity.Veiculo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ComprovanteDevolucaoService {

    public ComprovanteDevolucao criarDevolucao(ComprovanteAluguel comprovanteAluguel) {
        try {
            ComprovanteDevolucao comprovanteDevolucao = new ComprovanteDevolucao();

            if (comprovanteAluguel == null) {
                throw new IllegalArgumentException("Comprovante de aluguel não pode ser nulo.");
            }

            comprovanteDevolucao.setLocadora(comprovanteAluguel.getLocadora());
            comprovanteDevolucao.setVeiculo(comprovanteAluguel.getVeiculo());
            comprovanteDevolucao.setLocatario(comprovanteAluguel.getLocatario());
            comprovanteDevolucao.setDataHoraAluguel(comprovanteAluguel.getDataHoraAluguel());
            comprovanteDevolucao.setDataHoraDevolucao(LocalDateTime.now());

            LocalDateTime dataHoraAluguel = comprovanteAluguel.getDataHoraAluguel();
            LocalDateTime dataHoraDevolucao = comprovanteDevolucao.getDataHoraDevolucao();

            if (dataHoraDevolucao.isBefore(dataHoraAluguel)) {
                throw new IllegalArgumentException("A data de devolução não pode ser anterior à data de aluguel.");
            }

            long diasDeAluguel = Duration.between(dataHoraAluguel, dataHoraDevolucao).toDays();
            if (diasDeAluguel < 0) {
                throw new IllegalArgumentException("O número de dias de aluguel não pode ser negativo.");
            }

            BigDecimal valorBase = new BigDecimal("100.00");
            BigDecimal valorPorDia = new BigDecimal("100.00");

            BigDecimal valorTotal = valorBase.add(valorPorDia.multiply(BigDecimal.valueOf(diasDeAluguel)));

            Optional<Veiculo> veiculoOpt = Optional.ofNullable(comprovanteAluguel.getVeiculo());
            veiculoOpt.ifPresent(veiculo -> veiculo.setVeiculoDisponivelParaLocacao(true));

            comprovanteDevolucao.setValorDaLocacao(valorTotal);

            return comprovanteDevolucao;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao criar o comprovante de devolução: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar o comprovante de devolução: " + e.getMessage(), e);
        }
    }
}
