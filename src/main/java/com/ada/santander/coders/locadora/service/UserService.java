package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.RegisterDTO;
import com.ada.santander.coders.locadora.entity.User;
import com.ada.santander.coders.locadora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDetails findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public void saveUser(RegisterDTO registerDTO) {
        String passwordEncrypted = new BCryptPasswordEncoder().encode(registerDTO.password());
        User user = new User(registerDTO.login(), passwordEncrypted, registerDTO.userRole());
        userRepository.save(user);
    }
}
