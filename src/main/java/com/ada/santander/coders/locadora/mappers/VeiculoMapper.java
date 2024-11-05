package com.ada.santander.coders.locadora.mappers;

import com.ada.santander.coders.locadora.entity.Veiculo;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void atualizarVeiculo(Veiculo veiculoAtualizado, @MappingTarget Veiculo veiculoExistente);

}
