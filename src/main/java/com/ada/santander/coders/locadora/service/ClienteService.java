package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.entity.Cliente;
import com.ada.santander.coders.locadora.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;


    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");


    public List<Cliente> listarTodos(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id){
        return clienteRepository.findById(id);
    }

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Long id, Cliente clienteAtualizado){
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());

                    return clienteRepository.save(cliente);
                })

                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public void deletar(Long id){
        clienteRepository.deleteById(id);
    }

    private void validarCliente(Cliente cliente){
        if (!StringUtils.hasLength(cliente.getNome()) || cliente.getNome().length() <= 3) {
            throw new IllegalArgumentException("O nome deve ter mais de 3 caracteres!");
        }


        if (!cliente.getCpf().matches("\\d{11}")) {
            throw new IllegalArgumentException("O CPF deve ter 11 dígitos numéricos!");
        }



        if (!EMAIL_PATTERN.matcher(cliente.getEmail()).matches()) {
            throw new IllegalArgumentException("O e-mail está em um formato inválido!");
        }


        if (!cliente.getTelefone().matches("\\d{8,15}")) {
            throw new IllegalArgumentException("O telefone deve ter entre 8 e 15 dígitos numéricos!");
        }
    }

}
