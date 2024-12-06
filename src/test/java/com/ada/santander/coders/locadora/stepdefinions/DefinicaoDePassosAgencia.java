package com.ada.santander.coders.locadora.stepdefinions;

import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import io.cucumber.spring.CucumberContextConfiguration;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


@CucumberContextConfiguration
@DataJpaTest
public class DefinicaoDePassosAgencia {

    @Autowired
    AgenciaRepository agenciaRepository;

    @Given("O BD deve estar limpo")
    public void oBancoDeDadosEstaLimpo() {
        agenciaRepository.deleteAll();
    }

    public Agencia criarAgenciaFake(Long id, int tamanho, Endereco endereco) {
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setId(id);
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

    @When("eu cadastro uma agencia com a capacidade de {tamanho}, cep {cep}, Uf {uf}, Bairro {bairro}, Lougradouro {logradouro}, Região {regiao}, Cidade {cidade}")
    public void euCadastroUmVotoComNomeENumeroDoCandidato(
            int tamanho,
            String cep,
            String uf,
            String bairro,
            String logradouro,
            String regiao,
            String cidade) {
        Endereco endereco = criarEndereco(cep, uf, bairro, logradouro, regiao, cidade);
        Agencia agencia = criarAgenciaFake(1L, tamanho, endereco);
        agenciaRepository.save(agencia);
    }

    @Then("a agencia com a capacidade de {tamanho}, cep {cep}, Uf {uf}, Bairro {bairro}, Lougradouro {logradouro}, Região {regiao}, Cidade {cidade} deve estar salvo no banco de dados")
    public void oVotoComNomeDeveEstarSalvoNoBancoDeDados(
        int tamanho,
        String cep,
        String uf,
        String bairro,
        String logradouro,
        String regiao,
        String cidade) {
        Optional<Agencia> agenciaOptional =
                agenciaRepository.findById(1l);

        Agencia agencia = agenciaOptional.get();

        assertAll(
                ()->assertEquals(tamanho, agencia.getTamanhoMaximoDaFrota()),
                ()->assertEquals(cep, agencia.getEndereco().getCep()),
                ()->assertEquals(uf, agencia.getEndereco().getUf()),
                ()->assertEquals(bairro, agencia.getEndereco().getBairro()),
                ()->assertEquals(logradouro, agencia.getEndereco().getLogradouro()),
                ()->assertEquals(regiao, agencia.getEndereco().getRegiao()),
                ()->assertEquals(cidade, agencia.getEndereco().getCidade())
        );

    }
}
