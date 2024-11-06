package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.mappers.AgenciaMapper;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AgenciaService {

    private AgenciaRepository agenciaRepository;
    private AgenciaMapper agenciaMapper;

    public AgenciaService(AgenciaRepository agenciaRepository, AgenciaMapper agenciaMapper) {
        this.agenciaRepository = agenciaRepository;
        this.agenciaMapper = agenciaMapper;
    }

    public Agencia criarAgencia(Agencia agencia) {
        // TODO verificar se existe a gencia já
        return agenciaRepository.save(agencia);
    }

    public Page<Agencia> buscarAgenciaPaginados(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return agenciaRepository.findAll(pageable);
    }

    public Agencia atualizarAgencia(Long id, Agencia agenciaAtualizado) {
        Agencia agenciaExistente = this.buscarAgenciaPorId(id);
        agenciaMapper.atualizarAgencia(agenciaAtualizado,agenciaExistente);
        return agenciaRepository.save(agenciaExistente);
    }

    public Agencia deletarAgencia(Long id) {
       // TODO adicionar verificação
        Agencia agenciaExistente = this.buscarAgenciaPorId(id);
        agenciaRepository.delete(agenciaExistente);
        return agenciaExistente;
    }

    private Agencia buscarAgenciaPorId(Long id){
        return agenciaRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agencia com Id "+id
                ));
    }
}
