package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.service.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/agencia")
@Tag(name = "Agencia", description = "API para gestão de agencias")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @PostMapping("/criar-agencia")
    @Operation(summary = "Salvar um novo Agencia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agencia created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Agencia> criarAgencia(@RequestBody AgenciaDTO agencia) {
        Agencia agenciaNovo = agenciaService.criarAgencia(agencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaNovo);
    }

    @PutMapping("/atualiza-agencia/{id}")
    @Operation(summary = "Atualizar um agencia existente", description = "Atualizar um agencia existente pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agencia updated successfully"),
            @ApiResponse(responseCode = "404", description = "Agencia not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Agencia> atualizaAgencia(@PathVariable Long id, @RequestBody AgenciaDTO agencia) {
        Agencia agenciaAtualizado = agenciaService.atualizarAgencia(id, agencia);
        return ResponseEntity.ok().body(agenciaAtualizado);
    }

    @GetMapping("/buscar")
    @Operation(summary = "Paginate Agencias", description = "Retorne uma paginação de Agencias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list retrieved successfully")
    })
    public ResponseEntity<Page<Agencia>> paginarAgencia(@RequestParam int pagina, @RequestParam int tamanho) {
        Page<Agencia> agenciaList = agenciaService.buscarAgenciaPaginados(pagina, tamanho);
        return ResponseEntity.ok(agenciaList);
    }


    @Operation(summary = "Buscar agencia por ID", description = "Busca um agencia pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agencia encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Agencia não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Agencia> buscarAgenciaPorId(@PathVariable Long id){
        return ResponseEntity.ok(agenciaService.buscarAgenciaPorId(id));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar um Agencia", description = "Deletar um Agencia pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agencia deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Agencia not found")
    })
    public ResponseEntity<Agencia> deletarAgencia(@PathVariable Long id) {
        Agencia agencia = agenciaService.deletarAgencia(id);
        return agencia != null ? ResponseEntity.ok(agencia) : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
    }
}


