package com.ada.santander.coders.locadora.dto;

import com.ada.santander.coders.locadora.entity.enums.TipoVeiculo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoDTO {

    @NotBlank(message = "O modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "A placa é obrigatória")
    private String placa;

    @NotBlank(message = "O ano é obrigatório")
    private int ano;

    @NotBlank(message = "A cor é obrigatório")
    private String cor;

    @NotBlank(message = "O tipo de veiculo é obrigatório")
    private TipoVeiculo tipoVeiculo;

    @NotBlank(message = "Agencia é obrigatório")
    private Long agenciaId;
}
