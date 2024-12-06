package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
    }

    @Test
    void listarTodos() throws Exception {
        Cliente cliente1 = new Cliente(1L, "João", "12345678901", "joao@email.com", "123456789");
        Cliente cliente2 = new Cliente(2L, "Maria", "09876543210", "maria@email.com", "987654321");
        when(clienteService.listarTodos()).thenReturn(Arrays.asList(cliente1, cliente2));

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }

    @Test
    void buscarPorId() throws Exception {
        Cliente cliente = new Cliente(1L, "João", "12345678901", "joao@email.com", "123456789");
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void buscarPorId_NotFound() throws Exception {
        when(clienteService.buscarPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/clientes/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void salvar() throws Exception {
        Cliente cliente = new Cliente(null, "João", "12345678901", "joao@email.com", "123456789");
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João\",\"cpf\":\"12345678901\",\"email\":\"joao@email.com\",\"telefone\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }


    @Test
    void atualizar() throws Exception {
        Cliente cliente = new Cliente(1L, "João", "12345678901", "joao@email.com", "123456789");

        when(clienteService.atualizar(1L, cliente)).thenReturn(cliente);

        mockMvc.perform(put("/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João\",\"cpf\":\"12345678901\",\"email\":\"joao@email.com\",\"telefone\":\"123456789\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }




    @Test
    void deletar() throws Exception {
        doNothing().when(clienteService).deletar(1L);

        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());
    }
}
