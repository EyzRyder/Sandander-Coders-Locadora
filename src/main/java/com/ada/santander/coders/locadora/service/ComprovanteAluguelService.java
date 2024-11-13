package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.*;
import com.ada.santander.coders.locadora.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ComprovanteAluguelService {

    @Autowired
    private AgenciaRepository agenciaRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private UserRepository locatarioRepository;

    @Autowired
    private ComprovanteAluguelRepository comprovanteAluguelRepository;

    public ComprovanteAluguel criarComprovante(Long idAgencia, Long idVeiculo, Long idLocatario) {
        try {
            Optional<Agencia> agenciaOpt = agenciaRepository.findById(idAgencia);
            Optional<Veiculo> veiculoOpt = veiculoRepository.findById(idVeiculo);
            Optional<User> locatarioOpt = locatarioRepository.findById(idLocatario);

            if (agenciaOpt.isPresent() && veiculoOpt.isPresent() && locatarioOpt.isPresent()) {
                Agencia locadora = agenciaOpt.get();
                Veiculo veiculo = veiculoOpt.get();
                User locatario = locatarioOpt.get();

                if (!veiculo.getAgencia().getId().equals(locadora.getId())) {
                    throw new IllegalArgumentException("O veículo não pertence à agência informada.");
                }

                ComprovanteAluguel comprovante = new ComprovanteAluguel();
                comprovante.setLocadora(locadora);
                comprovante.setLocatario(locatario);
                comprovante.setVeiculo(veiculo);
                comprovante.setDataHoraAluguel(LocalDateTime.now());

                comprovanteAluguelRepository.save(comprovante);

                return comprovante;
            } else {
                throw new IllegalArgumentException("Agência, Veículo ou Locatário não encontrados com os IDs fornecidos.");
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (DataAccessException e) {
            throw new RuntimeException("Erro ao acessar os dados no banco de dados: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado ao criar o comprovante de aluguel: " + e.getMessage(), e);
        }
    }
}
