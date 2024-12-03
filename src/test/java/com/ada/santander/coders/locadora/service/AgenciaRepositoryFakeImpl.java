package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class AgenciaRepositoryFakeImpl  implements AgenciaRepository {

    private List<Agencia> agencias= new ArrayList<>();

    @Override
    public void flush() {

    }

    @Override
    public <S extends Agencia> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Agencia> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Agencia> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Agencia getOne(Long aLong) {
        return null;
    }

    @Override
    public Agencia getById(Long aLong) {
        return null;
    }

    @Override
    public Agencia getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Agencia> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Agencia> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Agencia> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Agencia> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Agencia> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Agencia> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Agencia, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Agencia> S save(S entity) {
        agencias.add(entity);
        return entity;
    }

    @Override
    public <S extends Agencia> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Agencia> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Agencia> findAll() {
        return null;
    }

    @Override
    public List<Agencia> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Agencia entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Agencia> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Agencia> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Agencia> findAll(Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), agencias.size());
        List<Agencia> pagedAgencias = agencias.subList(start, end);

        return new PageImpl<>(pagedAgencias, pageable, agencias.size());
    }
}
