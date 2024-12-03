package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.mappers.AgenciaMapperImpl;
import com.ada.santander.coders.locadora.response.CepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgenciaServiceTest {

    private AgenciaDTO criarAgenciaDTO(int tamanho,String cep) {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(tamanho);
        agenciaDTO.setCep(cep);
        return agenciaDTO;
    }

    private CepResponse criarEnderecoFake(String cep) {
        CepResponse endereco = new CepResponse();
        endereco.setCep(cep);
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setLocalidade("São Paulo");
        return endereco;
    }

    @Test
    @DisplayName("Deve registrar um agencia novo quando dados tiverem correto")
    void criarAgencia() {
        String cep = "02258010";
        CepResponse enderecoFake = criarEnderecoFake(cep);
        AgenciaService agenciaService = new AgenciaService(
                new AgenciaRepositoryFakeImpl(),
                new EnderecoRepositoryFakeImpl(),
                new AgenciaMapperImpl(),
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10,cep);

        Agencia result = agenciaService.criarAgencia(agenciaDTO);

        assertAll(
                () -> assertNotNull(result, "A agência criada não deveria ser nula."),
                () -> assertEquals(agenciaDTO.getCep(), result.getEndereco().getCep(), "O CEP do endereço não corresponde."),
                () -> assertEquals(agenciaDTO.getTamanhoMaximoDaFrota(), result.getTamanhoMaximoDaFrota(), "O tamanho máximo da frota não corresponde."),
                () -> assertEquals("Rua Bassi", result.getEndereco().getLogradouro(), "O logradouro não corresponde."),
                () -> assertEquals("Vila Constança", result.getEndereco().getBairro(), "O bairro não corresponde.")
        );

    }

    @Test
    @DisplayName("Deve retornar uma pagina de agencias")
    void buscarAgenciaPaginados() {
        String cep = "02258010";
        CepResponse enderecoFake = criarEnderecoFake(cep);
        AgenciaService agenciaService = new AgenciaService(
                new AgenciaRepositoryFakeImpl(),
                new EnderecoRepositoryFakeImpl(),
                new AgenciaMapperImpl(),
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10,cep);
        agenciaService.criarAgencia(agenciaDTO);
        Page<Agencia> pagina = agenciaService.buscarAgenciaPaginados(0, 1);

        assertAll(
                () -> assertNotNull(pagina, "A página não deveria ser nula."),
                () -> assertEquals(1, pagina.getTotalElements(), "Deve conter apenas um elemento."),
                () -> assertEquals(1, pagina.getTotalPages(), "Deve conter exatamente uma página."),
                () -> assertFalse(pagina.isEmpty(), "A página não deve estar vazia."),
                () -> assertEquals("Rua Bassi", pagina.getContent().get(0).getEndereco().getLogradouro(),
                        "O logradouro da agência não corresponde ao esperado.")
        );
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