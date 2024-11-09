package com.ada.santander.coders.locadora.service;

import com.ada.santander.coders.locadora.dto.AgenciaDTO;
import com.ada.santander.coders.locadora.entity.Agencia;
import com.ada.santander.coders.locadora.entity.Endereco;
import com.ada.santander.coders.locadora.mappers.AgenciaMapper;
import com.ada.santander.coders.locadora.repository.AgenciaRepository;
import com.ada.santander.coders.locadora.repository.EnderecoRepository;
import com.ada.santander.coders.locadora.response.CepResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AgenciaService {

    private final AgenciaRepository agenciaRepository;
    private final EnderecoRepository enderecoRepository;
    private final AgenciaMapper agenciaMapper;
    private final WebClient webClient;

    private static final String CEP_REGEX = "^[0-9]{5}-?[0-9]{3}$";


    @Autowired
    public AgenciaService(AgenciaRepository agenciaRepository, EnderecoRepository enderecoRepository, AgenciaMapper agenciaMapper, WebClient webClient) {
        this.agenciaRepository = agenciaRepository;
        this.enderecoRepository = enderecoRepository;
        this.agenciaMapper = agenciaMapper;
        this.webClient = webClient;
    }

    public Agencia criarAgencia(AgenciaDTO agenciaDTO) {
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setTamanhoMaximoDaFrota(agenciaDTO.getTamanhoMaximoDaFrota());
        agenciaNovo.setVeiculos(new ArrayList<>());
        Endereco endereco = getEnderecoByCep(agenciaDTO.getCep());
        agenciaNovo.setEndereco(endereco);

        return agenciaRepository.save(agenciaNovo);
    }

    public Page<Agencia> buscarAgenciaPaginados(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho);
        return agenciaRepository.findAll(pageable);
    }

    public Agencia atualizarAgencia(Long id, AgenciaDTO agenciaAtualizado) {
        Agencia agenciaExistente = this.buscarAgenciaPorId(id);
        Agencia agenciaNovo = new Agencia();
        agenciaNovo.setTamanhoMaximoDaFrota(agenciaAtualizado.getTamanhoMaximoDaFrota());

        if (!agenciaExistente.getEndereco().getCep().contains(agenciaAtualizado.getCep())) {
            Endereco endereco = getEnderecoByCep(agenciaAtualizado.getCep());
            agenciaNovo.setEndereco(endereco);
        }
        agenciaMapper.atualizarAgencia(agenciaNovo, agenciaExistente);
        return agenciaRepository.save(agenciaExistente);
    }

    public Agencia deletarAgencia(Long id) {
        Agencia agenciaExistente = this.buscarAgenciaPorId(id);
        agenciaRepository.delete(agenciaExistente);
        return agenciaExistente;
    }

    private Agencia buscarAgenciaPorId(Long id) {
        return agenciaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Agencia com Id " + id
                ));
    }

    private boolean verificarAgenciaExistente(Long id) {
        Optional<Agencia> agenciaExistente = agenciaRepository.findById(id);
        return agenciaExistente.isPresent();
    }

    private Mono<CepResponse> consultaCep(String cep) {

        if (!validarCep(cep)) {
            return Mono.error(new IllegalArgumentException("Cep Inválido"));
        }

        return webClient
                .get()
                .uri("/{cep}/json", cep)
                .retrieve()
                .bodyToMono(CepResponse.class);
    }

    private boolean validarCep(String cep) {
        return cep != null && cep.matches(CEP_REGEX);
    }

  private Endereco getEnderecoByCep (String cep){

      Optional<Endereco> cepExitente = enderecoRepository.findById(cep);
      if(cepExitente.isPresent()){
          return cepExitente.get();
      }else{
          CepResponse cepResponse = consultaCep(cep).block();
          Endereco cepNovo= new Endereco();
          cepNovo.setCep(cepResponse.getCep());
          cepNovo.setLogradouro(cepResponse.getLogradouro());
          cepNovo.setBairro(cepResponse.getBairro());
          cepNovo.setUf(cepResponse.getUf());
          cepNovo.setCidade(cepResponse.getLocalidade());
          cepNovo.setRegiao(cepResponse.getRegiao());
          return enderecoRepository.save(cepNovo);
      }
  }
}
