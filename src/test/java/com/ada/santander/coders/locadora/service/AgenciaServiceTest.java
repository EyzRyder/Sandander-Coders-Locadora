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
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgenciaServiceTest {

    private AgenciaDTO criarAgenciaDTO(int tamanho) {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(tamanho);
        agenciaDTO.setCep("02258010");
        return agenciaDTO;
    }

    private CepResponse criarEnderecoFake() {
        CepResponse endereco = new CepResponse();
        endereco.setCep("02258010");
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setLocalidade("São Paulo");
        return endereco;
    }

    public AgenciaService criarAgenciaService() {
        CepResponse enderecoFake = criarEnderecoFake();
        return new AgenciaService(
                new AgenciaRepositoryFakeImpl(),
                new EnderecoRepositoryFakeImpl(),
                new AgenciaMapperImpl(),
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
    }

    @Test
    @DisplayName("Deve registrar um agencia novo quando dados tiverem correto")
    void criarAgencia() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);

        Agencia result = agenciaService.criarAgencia(agenciaDTO);

        assertAll(
                () -> assertNotNull(result, "A agência criada não deveria ser nula."),
                () -> assertEquals(agenciaDTO.getCep(), result.getEndereco().getCep(), "O CEP do endereço não corresponde."),
                () -> assertEquals(agenciaDTO.getTamanhoMaximoDaFrota(), result.getTamanhoMaximoDaFrota(), "O tamanho máximo da frota não corresponde."),
                () -> assertEquals("Rua Bassi", result.getEndereco().getLogradouro(), "O logradouro não corresponde."),
                () -> assertEquals("Vila Constança", result.getEndereco().getBairro(), "O bairro não corresponde."),
                () -> assertNotNull(result.getId(), "O ID da agência deveria ter sido gerado.")
        );

    }

    @Test
    @DisplayName("Deve atualizar um agencia quando dados tiverem correto")
    void atualizarAgencia() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTOAntigo = criarAgenciaDTO(10);
        Agencia agenciaAntiga = agenciaService.criarAgencia(agenciaDTOAntigo);

        AgenciaDTO agenciaDTONovo = criarAgenciaDTO(1);
        Agencia result = agenciaService.atualizarAgencia(1l, agenciaDTONovo);

        assertAll(
                () -> assertNotNull(result, "A agência atualizada não deveria ser nula."),
                () -> assertEquals(agenciaAntiga.getId(), result.getId(), "O ID da agência não deveria ser alterado."),
                () -> assertEquals(agenciaDTONovo.getTamanhoMaximoDaFrota(), result.getTamanhoMaximoDaFrota(), "O tamanho máximo da frota não foi atualizado corretamente."),
                () -> assertEquals(agenciaDTOAntigo.getCep(), result.getEndereco().getCep(), "O CEP não deveria ser alterado."),
                () -> assertEquals(agenciaAntiga.getEndereco().getLogradouro(), result.getEndereco().getLogradouro(), "O logradouro não deveria ser alterado.")
        );
    }

    @Test
    @DisplayName("Deve retornar uma pagina de agencias")
    void buscarAgenciaPaginados() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);
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
    @DisplayName("Deve retornar uma agencia")
    void buscarAgenciaPorId() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);

        Agencia agenciaCriada = agenciaService.criarAgencia(agenciaDTO);

        Agencia result = agenciaService.buscarAgenciaPorId(agenciaCriada.getId()).get();

        assertAll(
                () -> assertNotNull(result, "A agência retornada não deveria ser nula."),
                () -> assertEquals(agenciaCriada.getId(), result.getId(), "O ID da agência retornada está incorreto."),
                () -> assertEquals(agenciaDTO.getCep(), result.getEndereco().getCep(), "O CEP do endereço não corresponde."),
                () -> assertEquals(agenciaDTO.getTamanhoMaximoDaFrota(), result.getTamanhoMaximoDaFrota(), "O tamanho máximo da frota não corresponde."));
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar uma agência inexistente")
    void deveLancarExcecaoAoBuscarAgenciaInexistente() {
        AgenciaService agenciaService = criarAgenciaService();

        assertFalse(agenciaService.buscarAgenciaPorId(999L).isPresent(),
                "Deveria lançar exceção ao buscar uma agência deletada.");
    }

    @Test
    @DisplayName("Deve atualizar um agencia quando dados tiverem correto")
    void deletarAgencia() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);

        Agencia agenciaCriada = agenciaService.criarAgencia(agenciaDTO);
        agenciaService.deletarAgencia(agenciaCriada.getId());

        assertFalse(agenciaService.buscarAgenciaPorId(agenciaCriada.getId()).isPresent(),
                "Deveria lançar exceção ao buscar uma agência deletada.");
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma agência inexistente")
    void deveLancarExcecaoAoDeletarAgenciaInexistente() {
        AgenciaService agenciaService = criarAgenciaService();

        assertFalse(agenciaService.buscarAgenciaPorId(999L).isPresent(),
                "Deveria lançar exceção ao buscar uma agência deletada.");
    }
}