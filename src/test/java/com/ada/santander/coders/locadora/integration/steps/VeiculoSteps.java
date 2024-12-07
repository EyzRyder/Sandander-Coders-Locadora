package com.ada.santander.coders.locadora.integration.steps;

import com.ada.santander.coders.locadora.dto.VeiculoDTO;
import com.ada.santander.coders.locadora.entity.Veiculo;
import com.ada.santander.coders.locadora.entity.enums.TipoVeiculo;
import com.ada.santander.coders.locadora.service.VeiculoService;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class VeiculoSteps {

    @Autowired
    private VeiculoService veiculoService;

    private VeiculoDTO veiculoDTO;
    private Veiculo veiculoCriado;
    private ResponseEntity<?> response;

    @Given("uma agência com capacidade disponível")
    public void umaAgenciaComCapacidadeDisponivel() {
        veiculoDTO = new VeiculoDTO();
        veiculoDTO.setModelo("Civic");
        veiculoDTO.setPlaca("ABC1234");
        veiculoDTO.setAno(2020);
        veiculoDTO.setCor("Preto");
        veiculoDTO.setTipoVeiculo(TipoVeiculo.CARRO);
        veiculoDTO.setAgenciaId(1L); // Certifique-se de que esta agência existe no banco de testes.
    }

    @When("eu criar um veículo com os dados válidos")
    public void euCriarUmVeiculoComOsDadosValidos() {
        veiculoCriado = veiculoService.createVeiculo(veiculoDTO);
    }

    @Then("o veículo deve ser salvo com sucesso")
    public void oVeiculoDeveSerSalvoComSucesso() {
        assertNotNull(veiculoCriado);
        assertEquals("Civic", veiculoCriado.getModelo());
        assertEquals("ABC1234", veiculoCriado.getPlaca());
    }

    @Given("um veículo existente no sistema")
    public void umVeiculoExistenteNoSistema() {
        veiculoCriado = veiculoService.createVeiculo(veiculoDTO); // Certifica-se de criar um veículo previamente.
    }

    @When("eu buscar o veículo pelo ID")
    public void euBuscarOVeiculoPeloID() {
        response = ResponseEntity.of(veiculoService.getVeiculoById(veiculoCriado.getId()));
    }

    @Then("o sistema deve retornar os detalhes do veículo")
    public void oSistemaDeveRetornarOsDetalhesDoVeiculo() {
        assertNotNull(response.getBody());
        assertEquals(veiculoCriado.getModelo(), ((Veiculo) response.getBody()).getModelo());
    }

    @When("eu atualizar o veículo com novos dados")
    public void euAtualizarOVeiculoComNovosDados() {
        veiculoDTO.setModelo("Corolla");
        veiculoCriado = veiculoService.updateVeiculo(veiculoCriado.getId(), veiculoDTO);
    }

    @Then("o sistema deve refletir as mudanças no veículo")
    public void oSistemaDeveRefletirAsMudancasNoVeiculo() {
        assertEquals("Corolla", veiculoCriado.getModelo());
    }

    @When("eu deletar o veículo pelo ID")
    public void euDeletarOVeiculoPeloID() {
        veiculoService.deleteVeiculo(veiculoCriado.getId());
    }

    @Then("o veículo deve ser removido do sistema")
    public void oVeiculoDeveSerRemovidoDoSistema() {
        assertFalse(veiculoService.getVeiculoById(veiculoCriado.getId()).isPresent());
    }
}
