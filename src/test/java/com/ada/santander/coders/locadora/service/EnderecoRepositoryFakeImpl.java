package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.repository.EnderecoRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EnderecoRepositoryFakeImpl implements EnderecoRepository {

    private List<Endereco> enderecos = new ArrayList<>();

    @Override
    public Page<Endereco> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Endereco> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Endereco> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteInBatch(Iterable<Endereco> entities) {
        EnderecoRepository.super.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch(Iterable<Endereco> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Endereco getOne(String s) {
        return null;
    }

    @Override
    public Endereco getById(String s) {
        return null;
    }

    @Override
    public Endereco getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Endereco> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Endereco> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Endereco> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Endereco> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Endereco> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Endereco> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Endereco, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Endereco> S save(S entity) {
        enderecos.add(entity);
        return entity;
    }

    @Override
    public <S extends Endereco> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Endereco> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Endereco> findAll() {
        return List.of();
    }

    @Override
    public List<Endereco> findAllById(Iterable<String> strings) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Endereco entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Endereco> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Endereco> findAll(Sort sort) {
        return List.of();
    }
}
