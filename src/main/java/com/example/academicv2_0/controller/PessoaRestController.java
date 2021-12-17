package com.example.academicv2_0.controller;

import com.example.academicv2_0.model.Pessoa;
import com.example.academicv2_0.model.dto.PessoaDTO;
import com.example.academicv2_0.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin(origins = "http://localhost:8080")
public class PessoaRestController {

    PessoaService pessoaService;

    @Autowired
    public PessoaRestController(PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    // Método que responde à requisição GET para retornar todas as pessoas cadastradas
    @GetMapping
    public List<PessoaDTO> listAll(){
        return pessoaService.getPessoas();
    }

    // Método que responde à requisição GET para retornar uma pessoa específica com o ID recebido
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<PessoaDTO> getPorID(@PathVariable Long id){
        Optional<PessoaDTO> pessoa = pessoaService.getPessoaDTOPorID(id);
        if(pessoa.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(pessoa.get());
        }
    }

    // Método que responde à requisição POST para inserir uma pessoa no banco de dados
    @PostMapping
    public ResponseEntity<Pessoa> inserir(@RequestBody Pessoa pessoa){
        if (pessoaService.getPessoaPorCPF(pessoa.getCPF()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Pessoa novaPessoa = pessoaService.inserir(pessoa);
        return ResponseEntity.created(URI.create("/pessoas/" + novaPessoa.getID())).body(pessoa);
    }

    // Método que responde à requisição PUT para alterar os dados de uma pessoa no banco de dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<PessoaDTO> alterar(@PathVariable Long id, @RequestBody Pessoa pessoa){
        Optional<Pessoa> pessoaBuscadaPorID = pessoaService.getPessoaPorID(id);
        if(pessoaBuscadaPorID.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            Pessoa pessoaBuscadaPorCPF = pessoaService.getPessoaPorCPF(pessoa.getCPF());
            if(pessoaBuscadaPorCPF != null && !Objects.equals(id, pessoaBuscadaPorCPF.getID())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else{
                pessoa.setID(id);
                pessoa.setAlunos(pessoaBuscadaPorID.get().getAlunos());
                Pessoa alterada = pessoaService.alterar(pessoa);
                PessoaDTO alteradaDTO = new PessoaDTO(alterada);
                return ResponseEntity.ok().body(alteradaDTO);
            }
        }
    }

    // Método que responde à requisição DELETE para remover o registro de uma pessoa no banco de dados
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        // TODO Verificar se a pessoa não tem alunos vinculados antes de removê-la
        Optional<Pessoa> pessoaBuscadaPorID = pessoaService.getPessoaPorID(id);
        if(pessoaBuscadaPorID.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        if(!pessoaBuscadaPorID.get().getAlunos().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        return pessoaService.getPessoaPorID(id).map(pessoa -> {
            pessoaService.remover(pessoa);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
