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
public class ClienteDTO {

    private Long id;


    @NotBlank(message = "O nome é obrigatório")
    private String nome;



    @NotBlank(message = "O CPF é obrigatório")
    private String cpf;


    @NotBlank(message = "O email é obrigatório")
    private String email;


    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;


}


