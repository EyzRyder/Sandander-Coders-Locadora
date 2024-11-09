package com.ada.santander.coders.locadora.repository;

import com.ada.santander.coders.locadora.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco,String> {
    Page<Endereco> findAll(Pageable pageable);
}
