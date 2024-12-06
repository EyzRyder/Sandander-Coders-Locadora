package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @InjectMocks
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarTodos() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "joao@gmail.com", "999999999");
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientes = clienteService.listarTodos();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("João Silva", clientes.get(0).getNome());
    }

    @Test
    void testBuscarPorId() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "joao@gmail.com", "999999999");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Optional<Cliente> result = clienteService.buscarPorId(1L);
        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getNome());
    }

    @Test
    void testSalvarClienteValido() {
        Cliente cliente = new Cliente(null, "João Silva", "12345678901", "joao@gmail.com", "999999999");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente savedCliente = clienteService.salvar(cliente);
        assertNotNull(savedCliente);
        assertEquals("João Silva", savedCliente.getNome());
    }

    @Test
    void testSalvarClienteInvalido() {
        Cliente cliente = new Cliente(null, "Jo", "123", "invalido", "123");
        assertThrows(IllegalArgumentException.class, () -> clienteService.salvar(cliente));
    }

    @Test
    void testAtualizarCliente() {
        Cliente cliente = new Cliente(1L, "João Silva", "12345678901", "joao@gmail.com", "999999999");
        Cliente clienteAtualizado = new Cliente(1L, "João Atualizado", "12345678901", "joao@gmail.com", "999999999");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        Cliente updatedCliente = clienteService.atualizar(1L, clienteAtualizado);
        assertNotNull(updatedCliente);
        assertEquals("João Atualizado", updatedCliente.getNome());
    }

    @Test
    void testDeletarCliente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deletar(1L);
        verify(clienteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletarClienteNaoExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> clienteService.deletar(1L));
    }
}
