package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.dto.UserDTO;
import com.ada.santander.coders.locadora.entity.User;
import com.ada.santander.coders.locadora.entity.enums.UserRole;
import com.ada.santander.coders.locadora.response.LoginResponseDTO;
import com.ada.santander.coders.locadora.service.TokenService;
import com.ada.santander.coders.locadora.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testLoginSuccess() throws Exception {
        UserDTO userDTO = new UserDTO("testUser", "testPassword");
        User user = new User("testUser", "testPassword", null, "Test User", "12345678901", "testuser@example.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(user, null));
        when(tokenService.generateToken(user)).thenReturn("mockToken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"testUser\", \"password\": \"testPassword\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mockToken"));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);
    }

    @Test
    void testRegisterSuccess() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("newUser", "newPassword", UserRole.USER, "New User", "98765432100", "newuser@example.com");

        when(userService.findByLogin(registerDTO.login())).thenReturn(null);
        doNothing().when(userService).saveUser(registerDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"newUser\", \"password\": \"newPassword\", \"userRole\": \"USER\", \"nome\": \"New User\", \"cpf\": \"98765432100\", \"email\": \"newuser@example.com\"}"))
                .andExpect(status().isOk());

        verify(userService).saveUser(registerDTO);
    }

    @Test
    void testRegisterConflict() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("newUser", "newPassword", UserRole.USER, "New User", "98765432100", "newuser@example.com");

        when(userService.findByLogin(registerDTO.login())).thenReturn(null);
        doNothing().when(userService).saveUser(registerDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"login\": \"newUser\", \"password\": \"newPassword\", \"userRole\": \"USER\", \"nome\": \"New User\", \"cpf\": \"98765432100\", \"email\": \"newuser@example.com\"}"))
                .andExpect(status().isOk());

        verify(userService).saveUser(registerDTO);
    }
}
