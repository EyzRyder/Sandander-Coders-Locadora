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
public class AgenciaDTO {

    @NotBlank(message = "O nome é obrigatório")
    private int tamanhoMaximoDaFrota;

    @NotBlank(message = "O nome é obrigatório")
    private String cep;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("tamanhoMaximoDaFrota=").append(tamanhoMaximoDaFrota);
        sb.append(", cep='").append(cep).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
