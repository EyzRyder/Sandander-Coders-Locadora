package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.entity.Comprovante;
import com.ada.santander.coders.locadora.service.AluguelService;
import com.ada.santander.coders.locadora.service.ComprovanteAluguelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluguel")
public class AluguelController {

    @Autowired
    ComprovanteAluguelService comprovanteAluguelService;

    @Autowired
    AluguelService aluguelService;

    @PostMapping("/alugar")
    public ResponseEntity<Comprovante> alugarVeiculo(
            @RequestParam Long idUsuario,
            @RequestParam Long idAgencia,
            @RequestParam Long idVeiculo)
    {
        try {
            Comprovante comprovanteAluguel = aluguelService.alugarVeiculo(idUsuario, idAgencia, idVeiculo);
            return ResponseEntity.status(HttpStatus.CREATED).body(comprovanteAluguel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
