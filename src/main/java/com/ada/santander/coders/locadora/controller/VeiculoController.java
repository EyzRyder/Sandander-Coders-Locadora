package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.VeiculoDTO;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.service.VeiculoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @PostMapping
    public ResponseEntity<Veiculo> createVeiculo(@RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        // Mapear os campos do DTO para a entidade Veiculo
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setCor(veiculoDTO.getCor());
        veiculo.setTipoVeiculo(veiculoDTO.getTipoVeiculo());
        veiculo.setVeiculoDisponivelParaLocacao(veiculoDTO.isVeiculoDisponivelParaLocacao());
        Veiculo novoVeiculo = veiculoService.createVeiculo(veiculo);
        return new ResponseEntity<>(novoVeiculo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Veiculo>> getAllVeiculos(Pageable pageable) {
        Page<Veiculo> veiculos = veiculoService.getAllVeiculos(pageable);
        return new ResponseEntity<>(veiculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculoById(@PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.getVeiculoById(id);
        return veiculo.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(@PathVariable Long id, @RequestBody VeiculoDTO veiculoDTO) {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(veiculoDTO.getModelo());
        veiculo.setPlaca(veiculoDTO.getPlaca());
        veiculo.setAno(veiculoDTO.getAno());
        veiculo.setCor(veiculoDTO.getCor());
        veiculo.setTipoVeiculo(veiculoDTO.getTipoVeiculo());
        veiculo.setVeiculoDisponivelParaLocacao(veiculoDTO.isVeiculoDisponivelParaLocacao());

        Veiculo atualizado = veiculoService.updateVeiculo(id, veiculo);
        return new ResponseEntity<>(atualizado, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        veiculoService.deleteVeiculo(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
