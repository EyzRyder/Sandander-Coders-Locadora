package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.ClienteDTO;
import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Cliente", description = "API para gestão de cliente. OLD VERSION")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista de todos os clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public List<Cliente> listarTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Salvar um novo cliente", description = "Salva um novo cliente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping
    public Cliente salvar(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @Operation(summary = "Atualizar um cliente existente", description = "Atualiza as informações de um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteAtualizado) {
        try {
            Cliente cliente = clienteService.atualizar(id, clienteAtualizado);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }





    @Operation(summary = "Deletar um cliente", description = "Deleta um cliente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }


}
