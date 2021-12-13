package com.example.academicv2_0.controller;

import com.example.academicv2_0.model.Pessoa;
import com.example.academicv2_0.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "http://localhost:8080")
public class PessoaRestController {

    PessoaService servico;

    @Autowired
    public PessoaRestController(PessoaService servico){
        this.servico = servico;
    }

    // Método que responde à requisição GET para retornar todas as pessoas cadastradas
    @GetMapping
    public List<Pessoa> listAll(){
        return servico.getPessoas();
    }

    // Método que responde à requisição GET para retornar uma pessoa específica com o ID recebido
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Pessoa> getId(@PathVariable Long id){
        Optional<Pessoa> pessoa = servico.getPessoaPorID(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(pessoa.get());
        }
    }

    // Método que responde à requisição POST para inserir uma pessoa no banco de dados
    @PostMapping
    public ResponseEntity<Pessoa> inserir(@RequestBody Pessoa pessoa){
        if (servico.getPessoaPorID(pessoa.getId()).isPresent() || servico.getPessoaPorCPF(pessoa.getCPF()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Pessoa novaPessoa = servico.inserir(pessoa);
        return ResponseEntity.created(URI.create("/pessoas/" + novaPessoa.getId())).body(pessoa);
    }

    // Método que responde à requisição PUT para alterar os dados de uma pessoa no banco de dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<Pessoa> alterar(@PathVariable Long id, @RequestBody Pessoa pessoa){
        if(servico.getPessoaPorID(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }else {
            pessoa.setId(id);
            servico.alterar(pessoa);
            Pessoa alterada = servico.getPessoaPorID(id).get();
            return ResponseEntity.ok().body(alterada);
        }
    }

    // Método que responde à requisição DELETE para remover o registro de uma pessoa no banco de dados
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        return servico.getPessoaPorID(id).map(pessoa -> {
            servico.remover(pessoa);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
