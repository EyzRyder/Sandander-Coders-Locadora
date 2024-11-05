package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.repository.VeiculoRepository;

import org.springframework.stereotype.Service;

@Service
public class VeiculoService {
    private VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

}
