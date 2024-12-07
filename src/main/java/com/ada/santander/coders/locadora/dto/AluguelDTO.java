package com.ada.santander.coders.locadora.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AluguelDTO {

    @NotBlank(message = "O Id do Veiculo é obrigatório")
    private Long idVeiculo;
    @NotBlank(message = "O Id do Locatorio é obrigatório")
    private Long idLocatario;
}
