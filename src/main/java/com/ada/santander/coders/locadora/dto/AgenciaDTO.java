package com.ada.santander.coders.locadora.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgenciaDTO {

    @NotBlank(message = "O nome é obrigatório")
    private int tamanhoMaximoDaFrota;

    @NotBlank(message = "O nome é obrigatório")
    private String cep;
}
