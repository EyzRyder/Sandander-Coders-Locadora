package com.ada.santander.coders.locadora.repository;

import com.ada.santander.coders.locadora.entity.ComprovanteDevolucao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ComprovanteDevolucaoRepository extends JpaRepository<ComprovanteDevolucao, Long> {
    Page<ComprovanteDevolucao> findAllByLocatario_Id(Long idLocatario, Pageable pageable);
}
