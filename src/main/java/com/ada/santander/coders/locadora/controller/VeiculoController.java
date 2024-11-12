package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.VeiculoDTO;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.service.VeiculoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/veiculo")
@Tag(name = "Veiculos", description = "API para gestão de veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    @Operation(summary = "Salvar um novo veiculo", description = "Salva um novo veicolo em uma agencia no sistema")
    public ResponseEntity<Veiculo> createVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        Veiculo novoVeiculo = veiculoService.createVeiculo(veiculoDTO);
        return new ResponseEntity<>(novoVeiculo, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos os veiculos", description = "Retorna uma lista de todos os veiculos")
    public ResponseEntity<Page<Veiculo>> getAllVeiculos(@RequestParam int pagina, @RequestParam int tamanho) {
        Page<Veiculo> veiculos = veiculoService.getAllVeiculos(pagina, tamanho);
        return new ResponseEntity<>(veiculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar veiculo por ID", description = "Busca um veiculo pelo ID")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        return veiculo.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um veiculo existente", description = "Atualiza as informações de um veiculo pelo ID")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo atualizado = veiculoService.updateVeiculo(id, veiculoDTO);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um veiculo", description = "Deleta um veiculo pelo ID")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        veiculoService.deleteVeiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
