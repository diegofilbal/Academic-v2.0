package com.example.academicv2_0.service;

import com.example.academicv2_0.model.Pessoa;
import com.example.academicv2_0.model.dto.PessoaDTO;
import com.example.academicv2_0.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    // Objeto para invocar métodos de manipulação no banco de dados na tabela de pessoas
    private PessoaRepository repository;

    @Autowired
    private void setRepository(PessoaRepository repository){
        this.repository = repository;
    }

    // Método que consulta o banco e retorna todos os registros da tabela de pessoa
    public List<PessoaDTO> getPessoas(){
        List<Pessoa> listaPessoa = repository.findAll();
        List<PessoaDTO> listaPessoaDTO = new ArrayList<>();

        for (Pessoa p : listaPessoa) {
            listaPessoaDTO.add(new PessoaDTO(p));
        }

        return listaPessoaDTO;
    }

    // Métodos que buscam um registro específico da tabela de pessoas conforme o ID recebido
    public Optional<Pessoa> getPessoaPorID(Long id){
        return repository.findById(id);
    }

    public Optional<PessoaDTO> getPessoaDTOPorID(Long id){
        Optional<Pessoa> optPessoa = getPessoaPorID(id);
        Optional<PessoaDTO> optPessoaDTO = Optional.empty();

        if(optPessoa.isPresent()){
            PessoaDTO pessoaDTO = new PessoaDTO(optPessoa.get());
            optPessoaDTO = Optional.of(pessoaDTO);
        }

        return optPessoaDTO;
    }

    // Método que busca um registro específico da tabela de pessoas conforme o CPF recebido
    public Pessoa getPessoaPorCPF(String cpf){
        return repository.findByCpf(cpf).orElse(null);
    }

    // Método que insere um registro na tabela de pessoas do banco de dados
    public Pessoa inserir(Pessoa pessoa){
        return repository.save(pessoa);
    }

    // Método que altera um registro na tabela de pessoas do banco de dados
    public Pessoa alterar(Pessoa pessoa){
        return repository.save(pessoa);
    }

    // Método que remove um registro da tabela de pessoas do banco de dados
    public void remover(Pessoa pessoa){
        repository.delete(pessoa);
    }

}
