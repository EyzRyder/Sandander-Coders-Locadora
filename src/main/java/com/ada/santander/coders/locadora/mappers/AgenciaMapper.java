package com.ada.santander.coders.locadora.mappers;

import com.ada.santander.coders.locadora.entity.Agencia;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AgenciaMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarAgencia(Agencia agenciaAtualizado, @MappingTarget Agencia agenciaExistente);
}
