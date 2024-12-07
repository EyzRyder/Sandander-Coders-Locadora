package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.ClienteDTO;
import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.logging.Logger;

@Service
public class ClienteService {

    private static final Logger LOGGER = Logger.getLogger(ClienteService.class.getName());

    @Autowired
    private ClienteRepository clienteRepository;

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");

    @Transactional
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Transactional
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        LOGGER.info("Validando cliente antes de salvar...");
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }


    @Transactional
    public Cliente atualizar(Long id, ClienteDTO clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    LOGGER.info("Validando cliente antes de atualizar...");
                    validarCliente(clienteAtualizado);

                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());

                    Cliente clienteSalvo = clienteRepository.save(cliente);
                    LOGGER.info("Cliente atualizado com sucesso: " + clienteSalvo);
                    return clienteSalvo;
                })
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }


    @Transactional
    public void deletar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new IllegalArgumentException("Cliente com o ID fornecido não foi encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    private void validarCliente(Cliente cliente) {
        if (!StringUtils.hasLength(cliente.getNome()) || cliente.getNome().length() <= 3) {
            throw new IllegalArgumentException("O nome deve ter mais de 3 caracteres.");
        }

        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos numéricos.");
        }

        if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new IllegalArgumentException("O e-mail está em um formato inválido.");
        }

        if (!cliente.getTelefone().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("O telefone deve ter entre 8 e 15 dígitos numéricos.");
        }

        LOGGER.info("Cliente validado com sucesso.");
    }

    private void validarCliente(ClienteDTO cliente) {
        if (!StringUtils.hasLength(cliente.getNome()) || cliente.getNome().length() <= 3) {
            throw new IllegalArgumentException("O nome deve ter mais de 3 caracteres.");
        }

        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos numéricos.");
        }

        if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new IllegalArgumentException("O e-mail está em um formato inválido.");
        }

        if (!cliente.getTelefone().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("O telefone deve ter entre 8 e 15 dígitos numéricos.");
        }

        LOGGER.info("Cliente validado com sucesso.");

    }
}
