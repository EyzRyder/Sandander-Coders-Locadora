package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AluguelDTO;
import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.ComprovanteAluguelRepository;
import com.ada.santander.coders.locadora.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AluguelService {

    private final VeiculoRepository veiculoRepository;
    private final AgenciaRepository agenciaRepository;
    private final ComprovanteAluguelRepository comprovanteAluguelRepository;
    private final ComprovanteAluguelService comprovanteAluguelService;

    @Autowired
    public AluguelService(VeiculoRepository veiculoRepository, AgenciaRepository agenciaRepository, ComprovanteAluguelRepository comprovanteAluguelRepository, ComprovanteAluguelService comprovanteAluguelService) {
        this.veiculoRepository = veiculoRepository;
        this.agenciaRepository = agenciaRepository;
        this.comprovanteAluguelRepository = comprovanteAluguelRepository;
        this.comprovanteAluguelService = comprovanteAluguelService;
    }

    public ComprovanteAluguel alugarVeiculo(AluguelDTO aluguelDTO) {

        agenciaRepository.findById(aluguelDTO.getIdAgencia()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Agencia com Id " + aluguelDTO.getIdAgencia() + " não foi encontrado!"
        ));

        Veiculo veiculo = veiculoRepository.findById(aluguelDTO.getIdVeiculo()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Veiculo com Id " + aluguelDTO.getIdVeiculo() + " não foi encontrado!"
        ));

        if (!veiculo.isVeiculoDisponivelParaLocacao()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo com Id " + aluguelDTO.getIdVeiculo() + " não está disponivel.");
        }
        veiculo.setVeiculoDisponivelParaLocacao(false);

        ComprovanteAluguel comprovante = comprovanteAluguelService.criarComprovante(aluguelDTO.getIdAgencia(), aluguelDTO.getIdVeiculo(), aluguelDTO.getIdLocatario());

        veiculoRepository.save(veiculo);
        comprovanteAluguelRepository.save(comprovante);

        return comprovante;
    }
}
