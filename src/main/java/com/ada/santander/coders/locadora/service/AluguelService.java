package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AluguelDTO;
import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.ComprovanteAluguelRepository;
import com.ada.santander.coders.locadora.repository.ComprovanteDevolucaoRepository;
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
    private final ComprovanteDevolucaoService comprovanteDevolucaoService;
    private final ComprovanteDevolucaoRepository comprovanteDevolucaoRepository;

    @Autowired
    public AluguelService(VeiculoRepository veiculoRepository, AgenciaRepository agenciaRepository, ComprovanteAluguelRepository comprovanteAluguelRepository, ComprovanteAluguelService comprovanteAluguelService, ComprovanteDevolucaoService comprovanteDevolucaoService, ComprovanteDevolucaoRepository comprovanteDevolucaoRepository) {
        this.veiculoRepository = veiculoRepository;
        this.agenciaRepository = agenciaRepository;
        this.comprovanteAluguelRepository = comprovanteAluguelRepository;
        this.comprovanteAluguelService = comprovanteAluguelService;
        this.comprovanteDevolucaoService = comprovanteDevolucaoService;
        this.comprovanteDevolucaoRepository = comprovanteDevolucaoRepository;

    }

    public ComprovanteAluguel alugarVeiculo(AluguelDTO aluguelDTO) {
        try {
            Veiculo veiculo = veiculoRepository.findById(aluguelDTO.getIdVeiculo()).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Veiculo com Id " + aluguelDTO.getIdVeiculo() + " não foi encontrado!"
            ));

            agenciaRepository.findById(veiculo.getAgencia().getId()).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Agencia com Id " + veiculo.getAgencia().getId() + " não foi encontrado!"
            ));

            if (!veiculo.isVeiculoDisponivelParaLocacao()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veiculo com Id " + aluguelDTO.getIdVeiculo() + " não está disponivel para locação.");
            }
            veiculo.setVeiculoDisponivelParaLocacao(false);

            ComprovanteAluguel comprovante = comprovanteAluguelService.criarComprovante(veiculo.getAgencia().getId(), aluguelDTO.getIdVeiculo(), aluguelDTO.getIdLocatario());

            veiculoRepository.save(veiculo);
            comprovanteAluguelRepository.save(comprovante);

            return comprovante;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao tentar alugar o veículo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao tentar alugar o veículo: " + e.getMessage(), e);
        }
    }

    public ComprovanteDevolucao devolverVeiculo(Long idComprovante) {
        try {
            ComprovanteAluguel comprovanteAluguel = comprovanteAluguelRepository.findById(idComprovante).orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Comprovante de aluguel não encontrado com o ID " + idComprovante
            ));

            ComprovanteDevolucao novoComprovanteDevolucao = comprovanteDevolucaoService.criarDevolucao(comprovanteAluguel);

            comprovanteDevolucaoRepository.save(novoComprovanteDevolucao);

            return novoComprovanteDevolucao;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Erro ao tentar devolver o veículo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao tentar devolver o veículo: " + e.getMessage(), e);
        }
    }
}
