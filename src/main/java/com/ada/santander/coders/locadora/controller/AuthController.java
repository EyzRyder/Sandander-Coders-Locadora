package com.ada.santander.coders.locadora.controller;

import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.dto.UserDTO;
import com.ada.santander.coders.locadora.entity.User;
import com.ada.santander.coders.locadora.response.LoginResponseDTO;
import com.ada.santander.coders.locadora.service.TokenService;
import com.ada.santander.coders.locadora.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "API para Auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity login(@RequestBody UserDTO userDTO) {

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDTO.login(), userDTO.password());

        var authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        String token = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

    @PostMapping("/register")
    @Operation(summary = "Registrar")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {

        if (Objects.nonNull(userService.findByLogin(registerDTO.login()))) {
            return ResponseEntity.badRequest().build();
        }
        userService.saveUser(registerDTO);
        return ResponseEntity.ok("Usuário Registrado");
    }
}
