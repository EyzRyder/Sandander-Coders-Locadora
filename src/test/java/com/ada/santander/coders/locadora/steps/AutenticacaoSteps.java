package com.ada.santander.coders.locadora.steps;

import com.ada.santander.coders.locadora.dto.UserDTO;
import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.entity.enums.UserRole;
import org.json.JSONException;
import org.springframework.boot.test.web.client.TestRestTemplate;
import com.ada.santander.coders.locadora.response.LoginResponseDTO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import io.cucumber.java.pt.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AutenticacaoSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String token;

    @Dado("que o usuário deseja realizar login")
    public void queOUsuarioDesejaRealizarLogin() {

    }

    @Quando("ele envia um pedido de login com as credenciais {string} e {string}")
    public void eleEnviaUmPedidoDeLoginComAsCredenciaisE(String login, String senha) {
        UserDTO userDTO = new UserDTO(login, senha);
        response = restTemplate.postForEntity("http://localhost:8080/auth/login", userDTO, String.class);
    }

    @Então("o sistema retorna um token JWT válido")
    public void oSistemaRetornaUmTokenJWTValido() throws JSONException {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONObject jsonResponse = new JSONObject(response.getBody());
        token = jsonResponse.getString("token");
        assertTrue(token != null && !token.isEmpty());
    }

    @Dado("que o usuário deseja se registrar")
    public void queOUsuarioDesejaSeRegistrar() {

    }

    @Quando("ele envia os dados de registro com login {string}, senha {string}, e outras informações válidas")
    public void eleEnviaOsDadosDeRegistroComLoginESenhaEOutrasInformacoesVálidas(String login, String senha) {
        RegisterDTO registerDTO = new RegisterDTO(login, senha, UserRole.USER, "Nome do Novo Usuário", "12345678901", "newuser@example.com");
        response = restTemplate.postForEntity("http://localhost:8080/auth/register", registerDTO, String.class);
    }

    @Então("o sistema cria o usuário e retorna status 200")
    public void oSistemaCriaOUsuarioERetornaStatus() {
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
