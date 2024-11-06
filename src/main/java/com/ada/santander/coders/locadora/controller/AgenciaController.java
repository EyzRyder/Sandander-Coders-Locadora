package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.service.AgenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/agencia")
public class AgenciaController {
    private final AgenciaService agenciaService;

    public AgenciaController(AgenciaService agenciaService) {
        this.agenciaService = agenciaService;
    }

    @PostMapping("/criar-agencia")
    public ResponseEntity<Agencia> criarAgencia(@RequestBody Agencia agencia) {
        // TODO add criarAgencia service
        Agencia agenciaNovo = agenciaService.criarAgencia(agencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(agencia);
    }

    @PutMapping("/atualiza-agencia/{id}")
    public ResponseEntity<Agencia> atualizaAgencia(@PathVariable Long id, @RequestBody Agencia agencia) {
        Agencia agenciaAtualizado = agenciaService.atualizarAgencia(id, agencia);
        return ResponseEntity.ok().body(agenciaAtualizado);
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<Agencia>> paginarAgencia(@RequestParam int pagina, @RequestParam int tamanho) {
        Page<Agencia> agenciaList = agenciaService.buscarAgenciaPaginados(pagina, tamanho);
        return ResponseEntity.ok(agenciaList);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Agencia> deletarAgencia(@PathVariable Long id) {
        Agencia agencia = agenciaService.deletarAgencia(id);
        return agencia != null ? ResponseEntity.ok(agencia) : ResponseEntity.notFound().build();
    }

}


