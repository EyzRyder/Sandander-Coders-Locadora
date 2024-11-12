package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.ComprovanteAluguelRepository;
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

    public ComprovanteAluguel alugarVeiculo(Long idAgencia, Long idVeiculo, Long idLocatario) {
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(idVeiculo);

        if (veiculoOpt.isPresent()) {
            Veiculo veiculo = veiculoOpt.get();

            if (veiculo.isVeiculoDisponivelParaLocacao()) {
                veiculo.setVeiculoDisponivelParaLocacao(false);

                ComprovanteAluguel comprovante = comprovanteAluguelService.criarComprovante(idAgencia, idVeiculo, idLocatario);

                veiculoRepository.save(veiculo);
                comprovanteAluguelRepository.save(comprovante);

                return comprovante;
            }
        }
        return null;
    }
}
