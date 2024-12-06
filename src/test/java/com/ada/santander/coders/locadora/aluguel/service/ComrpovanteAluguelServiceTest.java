package com.ada.santander.coders.locadora.aluguel.service;
import com.ada.santander.coders.locadora.entity.*;
import com.ada.santander.coders.locadora.repository.*;
import com.ada.santander.coders.locadora.service.ComprovanteAluguelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ComprovanteAluguelServiceTest {

    @Mock private AgenciaRepository agenciaRepository;
    @Mock private VeiculoRepository veiculoRepository;
    @Mock private UserRepository locatarioRepository;
    @Mock private ComprovanteAluguelRepository comprovanteAluguelRepository;
    @InjectMocks private ComprovanteAluguelService comprovanteAluguelService;

    private Agencia agencia;
    private Veiculo veiculo;
    private User locatario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        agencia = new Agencia();
        agencia.setId(1L);

        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setAgencia(agencia);

        locatario = new User();
        locatario.setId(1L);
    }

    @Test
    void criarComprovante_DeveRetornarComprovanteAluguel() {
        when(agenciaRepository.findById(1L)).thenReturn(java.util.Optional.of(agencia));
        when(veiculoRepository.findById(1L)).thenReturn(java.util.Optional.of(veiculo));
        when(locatarioRepository.findById(1L)).thenReturn(java.util.Optional.of(locatario));
        ComprovanteAluguel result = comprovanteAluguelService.criarComprovante(1L, 1L, 1L);
        assertNotNull(result);
        verify(comprovanteAluguelRepository).save(result);
    }

    @Test
    void criarComprovante_DeveLancarErroQuandoVeiculoNaoPertencerAgencia() {
        Veiculo outroVeiculo = new Veiculo();
        outroVeiculo.setId(2L);
        Agencia agenciaDiferente = new Agencia();
        agenciaDiferente.setId(2L);
        outroVeiculo.setAgencia(agenciaDiferente);
        Agencia agencia = new Agencia();
        agencia.setId(1L);
        when(agenciaRepository.findById(1L)).thenReturn(Optional.of(agencia));
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(outroVeiculo));
        User locatario = new User();
        locatario.setId(1L);
        when(locatarioRepository.findById(1L)).thenReturn(Optional.of(locatario));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> comprovanteAluguelService.criarComprovante(1L, 1L, 1L));
        assertEquals("O veículo não pertence à agência informada.", exception.getMessage());
    }

    @Test
    void criarComprovante_DeveLancarErroQuandoAgenciaOuVeiculoOuLocatarioNaoEncontrados() {
        when(agenciaRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> comprovanteAluguelService.criarComprovante(1L, 1L, 1L));
        assertEquals("Agência, Veículo ou Locatário não encontrados com os IDs fornecidos.", exception.getMessage());
    }

    @Test
    void criarComprovante_DeveLancarErroQuandoFalhaNoBanco() {
        when(agenciaRepository.findById(1L)).thenReturn(java.util.Optional.of(agencia));
        when(veiculoRepository.findById(1L)).thenReturn(java.util.Optional.of(veiculo));
        when(locatarioRepository.findById(1L)).thenReturn(java.util.Optional.of(locatario));
        doThrow(new DataAccessException("Erro de banco de dados") {}).when(comprovanteAluguelRepository).save(any());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> comprovanteAluguelService.criarComprovante(1L, 1L, 1L));
        assertTrue(exception.getMessage().contains("Erro ao acessar os dados no banco de dados"));
    }
}