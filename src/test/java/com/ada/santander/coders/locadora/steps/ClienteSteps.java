package com.ada.santander.coders.locadora.steps;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import com.ada.santander.coders.locadora.dto.ClienteDTO;
import com.ada.santander.coders.locadora.entity.Cliente;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;


import io.cucumber.java.pt.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String clienteId;

    @Dado("que o usuário deseja cadastrar um novo cliente")
    public void queOUsuarioDesejaCadastrarUmNovoCliente() {
    }


    @Quando("ele envia os dados do cliente com nome {string}, CPF {string}, e e-mail {string}")
    public void eleEnviaOsDadosDoClienteComNomeCPFEDEEmail(String nome, String cpf, String email, String telefone) {
        ClienteDTO clienteDTO = new ClienteDTO(nome, cpf, email, telefone);
        response = restTemplate.postForEntity("http://localhost:8080/clientes", clienteDTO, String.class);
    }

    @Então("o sistema deve criar o cliente e retornar status 200")
    public void oSistemaDeveCriarOClienteERetornarStatus() throws JSONException {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        clienteId = jsonResponse.getString("id");
        assertNotNull(clienteId);
    }

    @Dado("que existem clientes cadastrados")
    public void queExistemClientesCadastrados() {

    }

    @Quando("o usuário acessa a rota para listar todos os clientes")
    public void oUsuarioAcessaARotaParaListarTodosOsClientes() {
        response = restTemplate.exchange("http://localhost:8080/clientes", HttpMethod.GET, null, String.class);
    }

    @Então("o sistema deve retornar uma lista com os clientes cadastrados")
    public void oSistemaDeveRetornarUmaListaComOsClientesCadastrados() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("João Silva"));
    }

    @Dado("que existe um cliente com ID {string}")
    public void queExisteUmClienteComID(String id) {

    }

    @Quando("o usuário atualiza as informações do cliente com nome {string}")
    public void oUsuarioAtualizaAsInformacoesDoClienteComNome(String nomeAtualizado) {
        ClienteDTO clienteDTO = new ClienteDTO("nomeAtualizado", "12345678901", "joao@gmail.com", "229482716234");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer token-exemplo");
        HttpEntity<ClienteDTO> entity = new HttpEntity<>(clienteDTO, headers);
        response = restTemplate.exchange("http://localhost:8080/clientes/1", HttpMethod.PUT, entity, String.class);
    }

    @Então("o sistema deve retornar as informações do cliente atualizadas")
    public void oSistemaDeveRetornarAsInformacoesDoClienteAtualizadas() throws JSONException {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        assertEquals("João Silva Atualizado", jsonResponse.getString("nome"));
    }

    @Quando("o usuário deleta o cliente com ID {string}")
    public void oUsuarioDeletaOClienteComID(String id) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer token-exemplo");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        response = restTemplate.exchange("http://localhost:8080/clientes/" + id, HttpMethod.DELETE, entity, String.class);
    }

    @Então("o sistema deve retornar status 200")
    public void oSistemaDeveRetornarStatus200() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
