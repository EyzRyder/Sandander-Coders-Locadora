package com.ada.santander.coders.locadora.aluguel.service;

import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.service.ComprovanteDevolucaoService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ComprovanteDevolucaoServiceTest {

    private final ComprovanteDevolucaoService comprovanteDevolucaoService = new ComprovanteDevolucaoService();

    @Test
    void criarDevolucao_DeveLancarErroQuandoComprovanteAluguelForNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            comprovanteDevolucaoService.criarDevolucao(null);
        });
        assertEquals("Erro ao criar o comprovante de devolução: Comprovante de aluguel não pode ser nulo.", exception.getMessage());
    }

    @Test
    void criarDevolucao_DeveLancarErroQuandoDataDeDevolucaoForAnteriorAoAluguel() {
        ComprovanteAluguel comprovanteAluguel = new ComprovanteAluguel();
        comprovanteAluguel.setDataHoraAluguel(LocalDateTime.now().plusDays(1));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            comprovanteDevolucaoService.criarDevolucao(comprovanteAluguel);
        });
        assertEquals("Erro ao criar o comprovante de devolução: A data de devolução não pode ser anterior à data de aluguel.", exception.getMessage());
    }

    @Test
    void criarDevolucao_DeveCalcularValorCorretamente() {
        ComprovanteAluguel comprovanteAluguel = new ComprovanteAluguel();
        comprovanteAluguel.setDataHoraAluguel(LocalDateTime.now().minusDays(5));
        Veiculo veiculo = new Veiculo();
        comprovanteAluguel.setVeiculo(veiculo);
        ComprovanteDevolucao comprovanteDevolucao = comprovanteDevolucaoService.criarDevolucao(comprovanteAluguel);
        assertEquals(new BigDecimal("600.00"), comprovanteDevolucao.getValorDaLocacao());
    }

    @Test
    void criarDevolucao_DeveMarcarVeiculoComoDisponivel() {
        ComprovanteAluguel comprovanteAluguel = new ComprovanteAluguel();
        comprovanteAluguel.setDataHoraAluguel(LocalDateTime.now().minusDays(5));
        Veiculo veiculo = new Veiculo();
        veiculo.setVeiculoDisponivelParaLocacao(false);
        comprovanteAluguel.setVeiculo(veiculo);
        ComprovanteDevolucao comprovanteDevolucao = comprovanteDevolucaoService.criarDevolucao(comprovanteAluguel);
        assertTrue(veiculo.isVeiculoDisponivelParaLocacao());
    }
}
