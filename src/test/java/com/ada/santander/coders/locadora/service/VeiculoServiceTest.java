package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.VeiculoDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.entity.enums.TipoVeiculo;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @Mock
    private AgenciaRepository agenciaRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createVeiculo_DeveCriarVeiculoComSucesso() {
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setModelo("Civic");
        veiculoDTO.setPlaca("ABC1234");
        veiculoDTO.setAno(2020);
        veiculoDTO.setCor("Preto");
        veiculoDTO.setTipoVeiculo(TipoVeiculo.CARRO);
        veiculoDTO.setAgenciaId(1L);

        Agencia agencia = new Agencia();
        agencia.setId(1L);
        agencia.setTamanhoMaximoDaFrota(5);
        agencia.setVeiculos(Collections.emptyList());

        when(agenciaRepository.findById(1L)).thenReturn(Optional.of(agencia));
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Veiculo veiculoCriado = veiculoService.createVeiculo(veiculoDTO);

        assertNotNull(veiculoCriado);
        assertEquals("Civic", veiculoCriado.getModelo());
        assertEquals("ABC1234", veiculoCriado.getPlaca());
        verify(veiculoRepository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void createVeiculo_DeveLancarExcecaoSeAgenciaNaoEncontrada() {
        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setAgenciaId(99L);

        when(agenciaRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> veiculoService.createVeiculo(veiculoDTO));

        assertEquals("404 NOT_FOUND \"Agencia com Id 99 não foi encontrado!\"", exception.getMessage());
    }

    @Test
    void getAllVeiculos_DeveRetornarListaDeVeiculos() {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo("Civic");

        Page<Veiculo> veiculos = new PageImpl<>(Collections.singletonList(veiculo));
        when(veiculoRepository.findAll(any(PageRequest.class))).thenReturn(veiculos);

        Page<Veiculo> resultado = veiculoService.getAllVeiculos(0, 1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("Civic", resultado.getContent().get(0).getModelo());
    }

    @Test
    void getVeiculoById_DeveRetornarVeiculoQuandoEncontrado() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setModelo("Civic");

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        Optional<Veiculo> resultado = veiculoService.getVeiculoById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Civic", resultado.get().getModelo());
    }

    @Test
    void getVeiculoById_DeveRetornarVazioQuandoNaoEncontrado() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Veiculo> resultado = veiculoService.getVeiculoById(1L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void updateVeiculo_DeveAtualizarVeiculoComSucesso() {
        Veiculo veiculoExistente = new Veiculo();
        veiculoExistente.setId(1L);
        veiculoExistente.setModelo("Civic");

        VeiculoDTO veiculoDTO = new VeiculoDTO();
        veiculoDTO.setModelo("Corolla");

        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculoExistente));
        when(veiculoRepository.save(any(Veiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Veiculo atualizado = veiculoService.updateVeiculo(1L, veiculoDTO);

        assertNotNull(atualizado);
        assertEquals("Corolla", atualizado.getModelo());
        verify(veiculoRepository, times(1)).save(veiculoExistente);
    }

    @Test
    void deleteVeiculo_DeveRemoverVeiculo() {
        doNothing().when(veiculoRepository).deleteById(1L);

        veiculoService.deleteVeiculo(1L);

        verify(veiculoRepository, times(1)).deleteById(1L);
    }
}
