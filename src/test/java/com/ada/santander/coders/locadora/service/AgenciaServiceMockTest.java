package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.mappers.AgenciaMapper;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.EnderecoRepository;
import com.ada.santander.coders.locadora.response.CepResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgenciaServiceMockTest {

    @InjectMocks
    private AgenciaService agenciaService;

    @Mock
    private AgenciaRepository agenciaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private AgenciaMapper agenciaMapper;


    private AgenciaDTO criarAgenciaDTO(int tamanho) {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(tamanho);
        agenciaDTO.setCep("02258010");
        return agenciaDTO;
    }

    private CepResponse criarCepResponseFake() {
        CepResponse endereco = new CepResponse();
        endereco.setCep("02258010");
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setLocalidade("São Paulo");
        return endereco;
    }

    public Endereco criarEnderecoFake(){
        Endereco endereco = new Endereco();
        endereco.setCep("02258010");
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setCidade("São Paulo");
        return endereco;
    }

    public Agencia criarAgenciaFake(Long id, int tamanho, Endereco endereco){
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setId(id);
        agenciaNovo.setTamanhoMaximoDaFrota(tamanho);
        agenciaNovo.setVeiculos(new ArrayList<>());
        agenciaNovo.setEndereco(endereco);
        return agenciaNovo;
    }

    public AgenciaService criarAgenciaService(){
        CepResponse enderecoFake = criarCepResponseFake();
        return new AgenciaService(
                agenciaRepository,
                enderecoRepository,
                agenciaMapper,
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
    }

    @Test
    @DisplayName("Deve registrar um agencia novo quando dados tiverem correto")
    void criarAgencia() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);
        Agencia agenciaNovo = criarAgenciaFake(1l,agenciaDTO.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        //Mockito.when(agenciaRepository.save(Mockito.any())).thenReturn(agenciaNovo);
        Mockito.doReturn(agenciaNovo).when(agenciaRepository).save(Mockito.any());

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
        Agencia agenciaAntigoEsperado =criarAgenciaFake(1l,agenciaDTOAntigo.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        Mockito.doReturn(agenciaAntigoEsperado).when(agenciaRepository).save(Mockito.any());
        Agencia agenciaAntiga = agenciaService.criarAgencia(agenciaDTOAntigo);


        AgenciaDTO agenciaDTONovo = criarAgenciaDTO(1);
        Agencia agenciaNovoEsperado =criarAgenciaFake(1l,agenciaDTONovo.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        Mockito.doReturn(agenciaNovoEsperado).when(agenciaRepository).save(Mockito.any());
        Mockito.doReturn(Optional.of(agenciaAntiga)).when(agenciaRepository).findById(1l);
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
        Agencia agenciaEsperado = criarAgenciaFake(1l,agenciaDTO.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        Mockito.doReturn(agenciaEsperado).when(agenciaRepository).save(Mockito.any());
        agenciaService.criarAgencia(agenciaDTO);

        Pageable pageable = PageRequest.of(0, 1);
        List<Agencia> pagedAgencias = new ArrayList<>();
        pagedAgencias.add(agenciaEsperado);

        Page<Agencia> page =  new PageImpl<>(pagedAgencias, pageable, 1);
        Mockito.doReturn(page).when(agenciaRepository).findAll(pageable);
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
        Agencia agenciaEsperado = criarAgenciaFake(1l,agenciaDTO.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        Mockito.doReturn(agenciaEsperado).when(agenciaRepository).save(Mockito.any());
        Agencia agenciaCriada = agenciaService.criarAgencia(agenciaDTO);

        Mockito.doReturn(Optional.of(agenciaEsperado)).when(agenciaRepository).findById(agenciaCriada.getId());
        Agencia result = agenciaService.buscarAgenciaPorId(agenciaCriada.getId());

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

        Mockito.doThrow(ResponseStatusException.class).when(agenciaRepository).findById(999L);
        assertThrows(ResponseStatusException.class,
                () -> agenciaService.buscarAgenciaPorId(999L),
                "Deveria lançar exceção ao buscar uma agência inexistente.");
    }


    @Test
    @DisplayName("Deve atualizar um agencia quando dados tiverem correto")
    void deletarAgencia() {
        AgenciaService agenciaService = criarAgenciaService();
        AgenciaDTO agenciaDTO = criarAgenciaDTO(10);
        Agencia agenciaEsperado = criarAgenciaFake(1l,agenciaDTO.getTamanhoMaximoDaFrota(),criarEnderecoFake());

        Mockito.doReturn(agenciaEsperado).when(agenciaRepository).save(Mockito.any());
        Agencia agenciaCriada = agenciaService.criarAgencia(agenciaDTO);


        Mockito.doReturn(Optional.of(agenciaEsperado)).when(agenciaRepository).findById(agenciaCriada.getId());
        agenciaService.deletarAgencia(agenciaCriada.getId());

        Mockito.doThrow(ResponseStatusException.class).when(agenciaRepository).findById(agenciaCriada.getId());
        assertThrows(ResponseStatusException.class,
                () -> agenciaService.buscarAgenciaPorId(agenciaCriada.getId()),
                "Deveria lançar exceção ao buscar uma agência deletada.");
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma agência inexistente")
    void deveLancarExcecaoAoDeletarAgenciaInexistente() {
        AgenciaService agenciaService = criarAgenciaService();

        Mockito.doThrow(ResponseStatusException.class).when(agenciaRepository).findById(999L);
        assertThrows(ResponseStatusException.class,
                () -> agenciaService.deletarAgencia(999L),
                "Deveria lançar exceção ao tentar deletar uma agência inexistente.");
    }
}