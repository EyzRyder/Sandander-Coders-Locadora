package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.entity.User;
import com.ada.santander.coders.locadora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.logging.Logger;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(ClienteService.class.getName());
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void saveUser(RegisterDTO registerDTO) {
        LOGGER.info("Validando cliente antes de salvar...");
        validarUser(registerDTO);
        String passwordEncrypted = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.login(), passwordEncrypted, registerDTO.userRole(), registerDTO.nome(), registerDTO.cpf(), registerDTO.email());
        userRepository.save(user);
    }


    private void validarUser(RegisterDTO registerDTO) {
        if (!StringUtils.hasLength(registerDTO.nome()) || registerDTO.nome().length() <= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O nome deve ter mais de 3 caracteres.");
        }

        if (!registerDTO.cpf().matches("\\d{11}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O CPF deve ter 11 dígitos numéricos.");
        }

        if (!EMAIL_PATTERN.matcher(registerDTO.email()).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O e-mail está em um formato inválido.");
        }

        LOGGER.info("Cliente validado com sucesso.");
    }
}
