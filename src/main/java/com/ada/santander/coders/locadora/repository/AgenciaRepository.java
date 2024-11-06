package com.ada.santander.coders.locadora.repository;

import com.ada.santander.coders.locadora.entity.Agencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgenciaRepository extends JpaRepository<Agencia,Long> {
    Page<Agencia> findAll(Pageable pageable);
    Optional<Agencia> findAgenciaByRazaoSocial(String razaoSocial);
}
