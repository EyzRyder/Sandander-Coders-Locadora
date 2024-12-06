package com.ada.santander.coders.locadora.aluguel.service;

import com.ada.santander.coders.locadora.dto.AluguelDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.ComprovanteAluguelRepository;
import com.ada.santander.coders.locadora.repository.VeiculoRepository;
import com.ada.santander.coders.locadora.service.AluguelService;
import com.ada.santander.coders.locadora.service.ComprovanteAluguelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AluguelServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;
    @Mock private AgenciaRepository agenciaRepository;
    @Mock private ComprovanteAluguelRepository comprovanteAluguelRepository;
    @Mock private ComprovanteAluguelService comprovanteAluguelService;
    @InjectMocks
    private AluguelService aluguelService;

    private Veiculo veiculo;
    private AluguelDTO aluguelDTO;
    private Agencia agencia;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        agencia = new Agencia();
        agencia.setId(1L);
        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setVeiculoDisponivelParaLocacao(true);
        veiculo.setAgencia(agencia);
        aluguelDTO = new AluguelDTO(1L, 1L);
    }

    @Test
    void alugarVeiculo_DeveRetornarComprovanteAluguel() {
        ComprovanteAluguel comprovante = new ComprovanteAluguel();
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(agenciaRepository.findById(1L)).thenReturn(Optional.of(agencia));
        when(comprovanteAluguelService.criarComprovante(1L, 1L, 1L)).thenReturn(comprovante);
        ComprovanteAluguel result = aluguelService.alugarVeiculo(aluguelDTO);
        assertNotNull(result);
        verify(veiculoRepository).save(veiculo);
        verify(comprovanteAluguelRepository).save(comprovante);
    }

    @Test
    void alugarVeiculo_DeveLancarErroQuandoVeiculoNaoDisponivel() {
        veiculo.setVeiculoDisponivelParaLocacao(false);
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(agenciaRepository.findById(veiculo.getAgencia().getId())).thenReturn(Optional.of(veiculo.getAgencia()));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> aluguelService.alugarVeiculo(aluguelDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Veículo não disponível para locação.", exception.getReason());
    }

    @Test
    void alugarVeiculo_DeveLancarErroQuandoVeiculoNaoEncontrado() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> aluguelService.alugarVeiculo(aluguelDTO));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Veículo com Id 1 não foi encontrado!", exception.getReason());
    }
}
