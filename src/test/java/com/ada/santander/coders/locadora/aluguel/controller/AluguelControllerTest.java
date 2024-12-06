package com.ada.santander.coders.locadora.aluguel.controller;

import com.ada.santander.coders.locadora.controller.AluguelController;
import com.ada.santander.coders.locadora.dto.AluguelDTO;
import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import com.ada.santander.coders.locadora.service.AluguelService;
import com.ada.santander.coders.locadora.service.ComprovanteAluguelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AluguelControllerTest {

    @Mock
    private ComprovanteAluguelService comprovanteAluguelService;

    @Mock
    private AluguelService aluguelService;

    @InjectMocks
    private AluguelController aluguelController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAlugarVeiculo() {
        AluguelDTO aluguelDTO = new AluguelDTO();
        ComprovanteAluguel comprovanteAluguel = new ComprovanteAluguel();
        when(aluguelService.alugarVeiculo(any(AluguelDTO.class))).thenReturn(comprovanteAluguel);
        ResponseEntity<?> response = aluguelController.alugarVeiculo(aluguelDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comprovanteAluguel, response.getBody());
    }

    @Test
    public void testDevolverVeiculo() {
        Long idComprovanteAluguel = 1L;
        ComprovanteDevolucao comprovanteDevolucao = new ComprovanteDevolucao();
        when(aluguelService.devolverVeiculo(idComprovanteAluguel)).thenReturn(comprovanteDevolucao);
        ResponseEntity<?> response = aluguelController.devolverVeiculo(idComprovanteAluguel);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(comprovanteDevolucao, response.getBody());
    }

    @Test
    public void testDevolverVeiculoError() {
        Long idComprovanteAluguel = 1L;
        String errorMessage = "Erro ao devolver veículo";
        when(aluguelService.devolverVeiculo(idComprovanteAluguel)).thenThrow(new RuntimeException(errorMessage));
        ResponseEntity<?> response = aluguelController.devolverVeiculo(idComprovanteAluguel);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao devolver veículo: " + errorMessage, response.getBody());
    }
}
