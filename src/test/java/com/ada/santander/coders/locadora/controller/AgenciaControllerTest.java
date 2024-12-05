package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

public class AgenciaControllerTest {

    @InjectMocks
    private AgenciaController agenciaController;

    private MockMvc mockMvc;

    @Mock
    private AgenciaRepository agenciaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private AgenciaMapper agenciaMapper;


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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(agenciaController).build();
    }

    @Test
    @DisplayName("Cadastrar agencia quando passar dados corretamente")
    void agenciaCadastro() throws Exception {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep("02258010");
        AgenciaService agenciaService = criarAgenciaService();

        Mockito.when(agenciaService.criarAgencia(Mockito.any())).thenReturn(Mockito.any(Agencia.class));

        JSONObject agenciaJson = new JSONObject(agenciaDTO.toString());
        mockMvc.perform(post("/agencia")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(agenciaJson.toString()))
                .andExpect(status().isOk());
    }
}
