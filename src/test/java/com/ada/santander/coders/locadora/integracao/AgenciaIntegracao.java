package com.ada.santander.coders.locadora.integracao;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.dto.UserDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.entity.enums.UserRole;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AgenciaIntegracao {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    public String token;
    HttpHeaders headers;

    private String getUrl(String path) {
        return "http://localhost:" + port + "/agencia/" + path;
    }

    private AgenciaDTO criarAgenciaDTO(int tamanho) {
        AgenciaDTO agenciaDTO = new AgenciaDTO();
        agenciaDTO.setTamanhoMaximoDaFrota(tamanho);
        agenciaDTO.setCep("02258010");
        return agenciaDTO;
    }


    public Agencia criarAgenciaFake(Long id, int tamanho) {
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setId(id);
        agenciaNovo.setTamanhoMaximoDaFrota(tamanho);
        agenciaNovo.setVeiculos(new ArrayList<>());

        Endereco endereco = new Endereco();
        endereco.setCep("02258010");
        endereco.setUf("SP");
        endereco.setBairro("Vila Constança");
        endereco.setLogradouro("Rua Bassi");
        endereco.setRegiao("Sudeste");
        endereco.setCidade("São Paulo");
        agenciaNovo.setEndereco(endereco);
        return agenciaNovo;
    }

    @BeforeEach
    void setUp() throws JSONException {
        UserDTO user = new UserDTO("test", "123456");
        RegisterDTO registerDTO = new RegisterDTO("test", "123456", UserRole.ADMIN, "Test - user", "12345678901", "gmail@gmail.com");

        ResponseEntity<String> responseEntityRegistrar = restTemplate
                .postForEntity(
                        "http://localhost:" + port + "/auth/register", registerDTO, String.class
                );

        ResponseEntity<String> responseEntityLogin = restTemplate
                .postForEntity(
                        "http://localhost:" + port + "/auth/login", user, String.class
                );
        JSONObject jsonResponse = new JSONObject(responseEntityLogin.getBody());
        token = jsonResponse.getString("token");

        headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @DisplayName("Deve cadastrar agencia  corretamente quando os dados forem enviados")
    void test01() {
        ResponseEntity<Agencia> responseEntity = restTemplate
                .postForEntity(
                        getUrl("criar-agencia"),
                        new HttpEntity<>(criarAgenciaDTO(10), headers),
                        Agencia.class
                );

        Agencia agenciaResponse = responseEntity.getBody();

        assertAll(
                () -> assertNotNull(agenciaResponse),
                () -> assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode())
        );
    }

    @Test
    @DisplayName("Deve atualizar agencia corretamente quando os dados forem enviados")
    void test02() {
        ResponseEntity<Agencia> responseEntityCriaAgencia = restTemplate
                .postForEntity(
                        getUrl("criar-agencia"),
                        new HttpEntity<>(criarAgenciaDTO(10), headers),
                        Agencia.class
                );


        Agencia agenciaCriadaResponse = responseEntityCriaAgencia.getBody();

        ResponseEntity<Agencia> responseEntityAtualizarAgencia = restTemplate
                .exchange(
                        getUrl("atualiza-agencia/" + agenciaCriadaResponse.getId()),
                        HttpMethod.PUT,
                        new HttpEntity<>(criarAgenciaDTO(1), headers),
                        Agencia.class
                );

        Agencia agenciaResponse = responseEntityAtualizarAgencia.getBody();


        assertAll(
                () -> assertNotNull(agenciaResponse),
                () -> assertEquals(HttpStatus.OK, responseEntityAtualizarAgencia.getStatusCode())
        );
    }


    @Test
    @DisplayName("Deve retornar uma pagina de agencia  corretamente quando os dados forem enviados")
    void test03() {
        ResponseEntity<Agencia> responseEntityCriaAgencia = restTemplate
                .postForEntity(
                        getUrl("criar-agencia"),
                        new HttpEntity<>(criarAgenciaDTO(10), headers),
                        Agencia.class
                );


        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(getUrl("buscar"))
                .queryParam("pagina", 0)
                .queryParam("tamanho", 1);
        ResponseEntity<PageImpl<Agencia>> responseEntity = restTemplate
                .exchange(
                        builder.toUriString(),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        new ParameterizedTypeReference<PageImpl<Agencia>>() {}                );


        Page<Agencia> agenciaPageResponse = responseEntity.getBody();

        assertAll(
                () -> assertNotNull(agenciaPageResponse),
                () -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode())
        );
    }
}
