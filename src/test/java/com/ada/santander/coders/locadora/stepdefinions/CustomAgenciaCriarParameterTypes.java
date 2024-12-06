package com.ada.santander.coders.locadora.stepdefinions;

import io.cucumber.java.ParameterType;

public class CustomAgenciaCriarParameterTypes {


    @ParameterType(".*")
    public Integer tamanho(String tamanho) {
        return Integer.parseInt(tamanho);
    }

    @ParameterType(".*")
    public String cep(String cep) {
        return cep;
    }

    @ParameterType(".*")
    public String uf(String uf) {
        return uf;
    }

    @ParameterType(".*")
    public String bairro(String bairro) {
        return bairro;
    }

    @ParameterType(".*")
    public String logradouro(String logradouro) {
        return logradouro;
    }

    @ParameterType(".*")
    public String regiao(String regiao) {
        return regiao;
    }

    @ParameterType(".*")
    public String cidade(String cidade) {
        return cidade;
    }

}
