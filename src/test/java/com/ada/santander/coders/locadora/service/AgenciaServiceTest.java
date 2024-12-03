package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.mappers.AgenciaMapperImpl;
import com.ada.santander.coders.locadora.response.CepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgenciaServiceTest {

    @Test
    @DisplayName("Deve registrar um agencia novo quando dados tiverem correto")
    void criarAgencia() {
        String cep = "02258010";
        CepResponse enderecoFake = new CepResponse();
        enderecoFake.setCep(cep);
        enderecoFake.setUf("SP");
        enderecoFake.setBairro("Vila Constança");
        enderecoFake.setLogradouro("Rua Bassi");
        enderecoFake.setRegiao("Sudeste");
        enderecoFake.setLocalidade("São Paulo");
        AgenciaService agenciaService = new AgenciaService(
                new AgenciaRepositoryFakeImpl(),
                new EnderecoRepositoryFakeImpl(),
                new AgenciaMapperImpl(),
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep(cep);

        Agencia result = agenciaService.criarAgencia(agenciaDTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(agenciaDTO.getCep(), result.getEndereco().getCep()),
                () -> assertEquals(agenciaDTO.getTamanhoMaximoDaFrota(), result.getTamanhoMaximoDaFrota())
        );

    }

    @Test
    @DisplayName("Deve retornar uma pagina de agencias")
    void buscarAgenciaPaginados() {
    }

    @Test
    @DisplayName("Deve atualizar um agencia quando dados tiverem correto")
    void atualizarAgencia() {
    }

    @Test
    @DisplayName("Deve atualizar um agencia quando dados tiverem correto")
    void deletarAgencia() {
    }

    @Test
    @DisplayName("Deve retornar uma agencia")
    void buscarAgenciaPorId() {

    }
}