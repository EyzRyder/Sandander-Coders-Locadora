package com.ada.santander.coders.locadora.repository;

import com.ada.santander.coders.locadora.entity.ComprovanteAluguel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComprovanteAluguelRepository extends JpaRepository<ComprovanteAluguel, Long> {
    Page<ComprovanteAluguel> findAllByLocatario_Id(Long idLocatario, Pageable pageable);
}
