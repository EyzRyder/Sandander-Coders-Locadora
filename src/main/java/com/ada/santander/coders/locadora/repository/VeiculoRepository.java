package com.ada.santander.coders.locadora.repository;

import com.ada.santander.coders.locadora.entity.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    Page<Veiculo> findAll(Pageable pageable);
}
