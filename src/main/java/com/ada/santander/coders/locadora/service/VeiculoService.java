package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.VeiculoDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.VeiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final AgenciaRepository agenciaRepository;

    @Autowired
    public VeiculoService(VeiculoRepository veiculoRepository, AgenciaRepository agenciaRepository) {
        this.veiculoRepository = veiculoRepository;
        this.agenciaRepository = agenciaRepository;
    }

    public Veiculo createVeiculo(VeiculoDTO veiculoDTO) {
        Veiculo veiculoNovo = new Veiculo();
        // Mapear os campos do DTO para a entidade Veiculo
        veiculoNovo.setModelo(veiculoDTO.getModelo());
        veiculoNovo.setPlaca(veiculoDTO.getPlaca());
        veiculoNovo.setAno(veiculoDTO.getAno());
        veiculoNovo.setCor(veiculoDTO.getCor());
        veiculoNovo.setTipoVeiculo(veiculoDTO.getTipoVeiculo());
        veiculoNovo.setVeiculoDisponivelParaLocacao(true);

        Agencia agencia = agenciaRepository.findById(veiculoDTO.getAgenciaId()).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Agencia com Id " + veiculoDTO.getAgenciaId()+" não foi encontrado!"
        ));

        if(agencia.getTamanhoMaximoDaFrota()== agencia.getVeiculos().size()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Agencia com Id " + veiculoDTO.getAgenciaId() + " já está comm capacidade maxima.");
        }

        return veiculoRepository.save(veiculoNovo);
    }

    public Page<Veiculo> getAllVeiculos(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return veiculoRepository.findAll(pageable);
    }

    public Optional<Veiculo> getVeiculoById(Long id) {
        return veiculoRepository.findById(id);
    }

    public Veiculo updateVeiculo(Long id, VeiculoDTO veiculoDTO) {
        Veiculo veiculoDetails = new Veiculo();
        veiculoDetails.setModelo(veiculoDTO.getModelo());
        veiculoDetails.setPlaca(veiculoDTO.getPlaca());
        veiculoDetails.setAno(veiculoDTO.getAno());
        veiculoDetails.setCor(veiculoDTO.getCor());
        veiculoDetails.setTipoVeiculo(veiculoDTO.getTipoVeiculo());
        veiculoDetails.setVeiculoDisponivelParaLocacao(true);

        return veiculoRepository.findById(id).map(veiculo -> {
            veiculo.setModelo(veiculoDetails.getModelo());
            veiculo.setPlaca(veiculoDetails.getPlaca());
            veiculo.setAno(veiculoDetails.getAno());
            veiculo.setCor(veiculoDetails.getCor());
            veiculo.setTipoVeiculo(veiculoDetails.getTipoVeiculo());
            veiculo.setVeiculoDisponivelParaLocacao(veiculoDetails.isVeiculoDisponivelParaLocacao());
            return veiculoRepository.save(veiculo);
        }).orElseThrow(() -> new RuntimeException("Veículo não encontrado com o ID: " + id));
    }

    public void deleteVeiculo(Long id) {
        veiculoRepository.deleteById(id);
    }
}
