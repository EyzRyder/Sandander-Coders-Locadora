package com.ada.santander.coders.locadora.integracao;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.dto.UserDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.enums.UserRole;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    private String token;
    private HttpHeaders headers;

    private String getUrl(String path) {
        return "http://localhost:" + port + path;
    }

    @BeforeEach
    void setUp() throws JSONException {

        RegisterDTO registerDTO = new RegisterDTO("testuser", "password123", UserRole.ADMIN , "Test User", "12345678901", "testuser@gmail.com");
        restTemplate.postForEntity(getUrl("/auth/register"), registerDTO, String.class);


        UserDTO userDTO = new UserDTO("testuser", "password123");
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(getUrl("/auth/login"), userDTO, String.class);
        JSONObject jsonResponse = new JSONObject(loginResponse.getBody());
        token = jsonResponse.getString("token");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
    }

    @Test
    @DisplayName("Deve registrar um novo usuário com sucesso")
    void testRegisterUser() {
        RegisterDTO registerDTO = new RegisterDTO("newuser", "password123", UserRole.ADMIN, "New User", "98765432100", "newuser@gmail.com");
        ResponseEntity<String> response = restTemplate.postForEntity(getUrl("/auth/register"), registerDTO, String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Deve realizar login corretamente e retornar um token JWT")
    void testLogin() {
        UserDTO userDTO = new UserDTO("testuser", "password123");
        ResponseEntity<String> loginResponse = restTemplate.postForEntity(getUrl("/auth/login"), userDTO, String.class);
        assertEquals(200, loginResponse.getStatusCodeValue());
        assertTrue(loginResponse.getBody().contains("token"));
    }

    @Test
    @DisplayName("Deve acessar uma rota protegida com sucesso usando token JWT")
    void testAccessProtectedRoute() {
        HttpHeaders headersWithBody = new HttpHeaders(headers);
        headersWithBody.setContentType(MediaType.APPLICATION_JSON);

        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(10);
        agenciaDTO.setCep("02258010");

        ResponseEntity<Agencia> protectedResponse = restTemplate
                .exchange(
                        getUrl("/agencia/criar-agencia"),
                        HttpMethod.POST,
                        new HttpEntity<>(agenciaDTO, headers),
                        Agencia.class
                );

        assertEquals(201, protectedResponse.getStatusCodeValue());
    }

}
