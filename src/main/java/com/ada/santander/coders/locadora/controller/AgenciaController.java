package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.service.AgenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {

    @Autowired
    private AgenciaService agenciaService;

    @PostMapping("/criar-agencia/{cep}")
    @Operation(summary = "Create a new Agencia", description = "Creates a new Agencia in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Agencia created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Agencia> criarAgencia(@RequestBody AgenciaDTO agencia, @PathVariable String cep) {
        Agencia agenciaNovo = agenciaService.criarAgencia(agencia,cep);
        return ResponseEntity.status(HttpStatus.CREATED).body(agenciaNovo);
    }

    @PutMapping("/atualiza-agencia/{id}")
    @Operation(summary = "Update an existing Agencia", description = "Updates an existing Agencia by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Agencia updated successfully"),
            @ApiResponse(responseCode = "404", description = "Agencia not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Agencia> atualizaAgencia(@PathVariable Long id, @RequestBody Agencia agencia) {
        Agencia agenciaAtualizado = agenciaService.atualizarAgencia(id, agencia);
        return ResponseEntity.ok().body(agenciaAtualizado);
    }

    @GetMapping("/busca")
    @Operation(summary = "Paginate Agencias", description = "Retrieves a paginated list of Agencias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paginated list retrieved successfully")
    })
    public ResponseEntity<Page<Agencia>> paginarAgencia(@RequestParam int pagina, @RequestParam int tamanho) {
        Page<Agencia> agenciaList = agenciaService.buscarAgenciaPaginados(pagina, tamanho);
        return ResponseEntity.ok(agenciaList);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Delete an Agencia", description = "Deletes an Agencia by its ID.")
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


