package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.mappers.AgenciaMapper;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.EnderecoRepository;
import com.ada.santander.coders.locadora.response.CepResponse;
import com.ada.santander.coders.locadora.service.AgenciaService;
import com.ada.santander.coders.locadora.service.ClienteWebFakeImpl;
import com.ada.santander.coders.locadora.service.WebClientFakeImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AgenciaControllerTest {
    @InjectMocks
    private AgenciaController agenciaController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AgenciaRepository agenciaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private AgenciaMapper agenciaMapper;

    private AgenciaService agenciaService;

    public AgenciaService criarAgenciaService() {
        CepResponse enderecoFake = new CepResponse();
        enderecoFake.setCep("02258010");
        enderecoFake.setUf("SP");
        enderecoFake.setBairro("Vila Constança");
        enderecoFake.setLogradouro("Rua Bassi");
        enderecoFake.setRegiao("Sudeste");
        enderecoFake.setLocalidade("São Paulo");
        return new AgenciaService(
                agenciaRepository,
                enderecoRepository,
                agenciaMapper,
                new ClienteWebFakeImpl(new WebClientFakeImpl(), enderecoFake));
    }


    public Agencia criarAgenciaFake(Long id, int tamanho) {
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setId(id);
        agenciaNovo.setTamanhoMaximoDaFrota(tamanho);
        agenciaNovo.setVeiculos(new ArrayList<>());

        Endereco endereco = new Endereco();
        endereco.setCep("02258010");
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setCidade("São Paulo");
        agenciaNovo.setEndereco(endereco);
        return agenciaNovo;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        agenciaService = criarAgenciaService();
        agenciaController = new AgenciaController(agenciaService);
        mockMvc = MockMvcBuilders.standaloneSetup(agenciaController).build();
    }

    @Test
    @DisplayName("Cadastrar agencia quando passar dados corretamente")
    void agenciaCadastro() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep("02258010");

        JSONObject agenciaJson = new JSONObject(agenciaDTO.toString());

        mockMvc.perform(post("/agencia/criar-agencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agenciaJson.toString())
                )
                .andExpect(status().isCreated());
    }


    @Test
    @DisplayName("Atualizar agencia quando passar dados corretamente")
    void agenciaAtualizar() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(1);
        agenciaDTO.setCep("02258010");
        AgenciaDTO agenciaDTOAtualizado = new AgenciaDTO();
        agenciaDTOAtualizado.setTamanhoMaximoDaFrota(10);
        agenciaDTOAtualizado.setCep("02258010");

        agenciaService.criarAgencia(agenciaDTO);

        JSONObject agenciaJson = new JSONObject(agenciaDTOAtualizado.toString());

        mockMvc.perform(put("/agencia/atualiza-agencia/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agenciaJson.toString())
                )
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar uma pagina de agencias")
    void agenciaPaginar() throws Exception {

        mockMvc.perform(get("/agencia/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pagina", "0")
                        .param("tamanho", "1")
                )
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar uma agencia")
    void agenciaPorId() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep("02258010");

        JSONObject agenciaJson = new JSONObject(agenciaDTO.toString());

        Agencia agencia = criarAgenciaFake(1L, 10);
        Mockito.when(agenciaRepository.save(Mockito.any(Agencia.class))).thenReturn(agencia);
        mockMvc.perform(post("/agencia/criar-agencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agenciaJson.toString())
        ).andExpect(status().isCreated());

        Mockito.when(agenciaService.buscarAgenciaPorId(1L)).thenReturn(Optional.of(agencia));
        Mockito.when(agenciaRepository.findById(1L)).thenReturn(Optional.of(agencia));
        mockMvc.perform(get("/agencia/buscar/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deletar agencia quando passar dados corretamente")
    void agenciaDeletar() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep("02258010");

        JSONObject agenciaJson = new JSONObject(agenciaDTO.toString());

        Agencia agencia = criarAgenciaFake(1L, 10);

        Mockito.when(agenciaRepository.save(Mockito.any(Agencia.class))).thenReturn(agencia);

        mockMvc.perform(post("/agencia/criar-agencia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(agenciaJson.toString())
        ).andExpect(status().isCreated());


        Mockito.doNothing().when(agenciaRepository).delete(agencia);
        Mockito.when(agenciaRepository.findById(agencia.getId())).thenReturn(Optional.of(agencia));

        mockMvc.perform(delete("/agencia/deletar/{id}", agencia.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

    }
}