package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.ComprovanteAluguelRepository;
import com.ada.santander.coders.locadora.repository.ComprovanteDevolucaoRepository;
import com.ada.santander.coders.locadora.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AluguelService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ComprovanteAluguelRepository comprovanteAluguelRepository;

    @Autowired
    private ComprovanteAluguelService comprovanteAluguelService;

    @Autowired
    private ComprovanteDevolucaoService comprovanteDevolucaoService;

    @Autowired
    private ComprovanteDevolucaoRepository comprovanteDevolucaoRepository;

    public ComprovanteAluguel alugarVeiculo(Long idAgencia, Long idVeiculo, Long idLocatario) {
        try {
            Optional<Veiculo> veiculoOpt = veiculoRepository.findById(idVeiculo);

            if (veiculoOpt.isEmpty()) {
                throw new IllegalArgumentException("Veículo não encontrado com o ID fornecido.");
            }

            Veiculo veiculo = veiculoOpt.get();

            if (!veiculo.isVeiculoDisponivelParaLocacao()) {
                throw new IllegalStateException("Veículo não está disponível para locação.");
            }

            veiculo.setVeiculoDisponivelParaLocacao(false);

            ComprovanteAluguel comprovante = comprovanteAluguelService.criarComprovante(idAgencia, idVeiculo, idLocatario);

            veiculoRepository.save(veiculo);
            comprovanteAluguelRepository.save(comprovante);

            return comprovante;
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw new IllegalArgumentException("Erro ao tentar alugar o veículo: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao tentar alugar o veículo: " + e.getMessage(), e);
        }
    }

    public ComprovanteDevolucao devolverVeiculo(Long idComprovante) {
        try {
            Optional<ComprovanteAluguel> comprovanteAluguelOpt = comprovanteAluguelRepository.findById(idComprovante);

            if (comprovanteAluguelOpt.isEmpty()) {
                throw new IllegalArgumentException("Comprovante de aluguel não encontrado com o ID fornecido.");
            }

            ComprovanteAluguel comprovanteAluguel = comprovanteAluguelOpt.get();

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
