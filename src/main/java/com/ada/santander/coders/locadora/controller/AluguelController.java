package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.entity.Comprovante;
import com.ada.santander.coders.locadora.service.AluguelService;
import com.ada.santander.coders.locadora.service.ComprovanteAluguelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluguel")
@Tag(name = "Alugeul", description = "API para gestão de aluguel")
public class AluguelController {

    ComprovanteAluguelService comprovanteAluguelService;
    AluguelService aluguelService;

    @Autowired
    public AluguelController(ComprovanteAluguelService comprovanteAluguelService, AluguelService aluguelService) {
        this.comprovanteAluguelService = comprovanteAluguelService;
        this.aluguelService = aluguelService;
    }

    @PostMapping("/alugar")
    @Operation(summary = "Alugar Veiculo", description = "descrição")
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
