package com.ada.santander.coders.locadora.stepdefinions;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.EnderecoRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@CucumberContextConfiguration
@DataJpaTest
public class DefinicaoDePassosAgencia {

    @Autowired
    AgenciaRepository agenciaRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    @Given("O BD deve estar limpo")
    public void oBancoDeDadosEstaLimpo() {
        agenciaRepository.deleteAll();
        enderecoRepository.deleteAll();
    }

    public Agencia criarAgenciaFake(int tamanho, Endereco endereco) {
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setTamanhoMaximoDaFrota(tamanho);
        agenciaNovo.setVeiculos(new ArrayList<>());
        agenciaNovo.setEndereco(endereco);
        return agenciaNovo;
    }

    public Endereco criarEndereco(String cep,
                                  String uf,
                                  String bairro,
                                  String logradouro,
                                  String regiao,
                                  String cidade) {
        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setUf(uf);
        endereco.setBairro(bairro);
        endereco.setLogradouro(logradouro);
        endereco.setRegiao(regiao);
        endereco.setCidade(cidade);
        return endereco;
    }

    @When("eu cadastro uma agencia com a capacidade de {tamanho} veiculos, cep {cep}, Uf {uf}, Bairro {bairro}, Lougradouro {logradouro}, Região {regiao}, Cidade {cidade}")
    public void cadastroAgancia(
            int tamanho,
            String cep,
            String uf,
            String bairro,
            String logradouro,
            String regiao,
            String cidade) {
        Endereco endereco = criarEndereco(cep, uf, bairro, logradouro, regiao, cidade);

        enderecoRepository.save(endereco);
        Agencia agencia = criarAgenciaFake(tamanho, endereco);
        agenciaRepository.save(agencia);
    }

    @Then("a agencia com a capacidade de {tamanho}, cep {cep}, Uf {uf}, Bairro {bairro}, Lougradouro {logradouro}, Região {regiao}, Cidade {cidade} deve estar salvo no banco de dados")
    public void verificarAgenciaCadastrada(
            int tamanho,
            String cep,
            String uf,
            String bairro,
            String logradouro,
            String regiao,
            String cidade) {

        Optional<Agencia> agenciaOptional = agenciaRepository.findById(11L);

        if (agenciaOptional.isEmpty()) {
            throw new IllegalStateException("Entidade não encontrada para o ID: " + 11);
        }

        Agencia agencia = agenciaOptional.get();

        assertAll(
                () -> assertEquals(tamanho, agencia.getTamanhoMaximoDaFrota()),
                () -> assertEquals(cep, agencia.getEndereco().getCep()),
                () -> assertEquals(uf, agencia.getEndereco().getUf()),
                () -> assertEquals(bairro, agencia.getEndereco().getBairro()),
                () -> assertEquals(logradouro, agencia.getEndereco().getLogradouro()),
                () -> assertEquals(regiao, agencia.getEndereco().getRegiao()),
                () -> assertEquals(cidade, agencia.getEndereco().getCidade())
        );
    }

    @When("eu atualizo a capacidade para {tamanho} da agencia")
    public void atualizarAgencia(int tamanho) {

        Optional<Agencia> agenciaOptional = agenciaRepository.findById(11L);
        if (agenciaOptional.isEmpty()) {
            throw new IllegalStateException("Entidade não encontrada para o ID: " + 11);
        }

        Agencia agencia = agenciaOptional.get();
        agencia.setTamanhoMaximoDaFrota(tamanho);
        agenciaRepository.save(agencia);
    }

    @Then("a agencia com capacidade atualizadad com {tamanho} deve estar salvo no banco de dados")
    public void deveAtualizadaAgencia(int tamanho) {

        Optional<Agencia> agenciaOptional = agenciaRepository.findById(11L);
        if (agenciaOptional.isEmpty()) {
            throw new IllegalStateException("Entidade não encontrada para o ID: " + 11);
        }

        Agencia agencia = agenciaOptional.get();
        assertAll(
                () -> assertEquals(tamanho, agencia.getTamanhoMaximoDaFrota())
        );
    }

    @When("eu deleto a agencia do BD")
    public void deleteAgencia(){
        Optional<Agencia> agenciaOptional = agenciaRepository.findById(11L);
        agenciaOptional.ifPresent(agencia -> agenciaRepository.delete(agencia));
    }

    @Then("o BD não deve conter mais a agencia")
    public void verificarAgenciaDeletado(){
        Optional<Agencia> agenciaOptional = agenciaRepository.findById(11L);
        assertTrue(agenciaOptional.isEmpty());

    }
}
