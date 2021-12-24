package com.example.academicv2_0.controller;

import com.example.academicv2_0.model.Aluno;
import com.example.academicv2_0.model.dto.AlunoDTO;
import com.example.academicv2_0.service.AlunoService;
import com.example.academicv2_0.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/alunos")
@CrossOrigin(origins = "http://localhost:8080")
public class AlunoRestController {

    AlunoService alunoServico;
    PessoaService pessoaServico;

    @Autowired
    public AlunoRestController(AlunoService alunoServico, PessoaService pessoaServico){
        this.alunoServico = alunoServico;
        this.pessoaServico = pessoaServico;
    }

    // Método que retorna a página inicial com a listagem dos alunos
    @GetMapping(path = "/homepage")
    public String homepage(Model model){
        model.addAttribute("listaAlunos", alunoServico.getAlunos());
        return "alunos/index";
    }

    // Método que retorna a página de cadastro de aluno
    @GetMapping(path = "/cadastro")
    public String cadastro(@ModelAttribute("aluno") AlunoDTO aluno){
        return "alunos/form-page";
    }

    // Método de resposta à ação do botão de confirmar o cadastro de aluno
    @PostMapping(path = "/salvar")
    public String salvar(@ModelAttribute("aluno") AlunoDTO aluno){
        aluno.setIdPessoa(15L);
        Aluno novoAluno = new Aluno(aluno);
        alunoServico.inserir(novoAluno);
        return "redirect:/alunos/homepage";
    }

    // TODO Implementar método que responde à alteração de aluno
/*
    @GetMapping(path = "/alterar/{id}")
    public String alterar(@PathVariable Long id){}
*/

    // Método que responde à requisição GET para retornar todos os alunos cadastrados
    @GetMapping
    public List<AlunoDTO> listAll(){
        return alunoServico.getAlunos();
    }

    // Método que responde à requisição GET para retornar todos os alunos cadastrados
    @GetMapping(path = "/{id}")
    public ResponseEntity<AlunoDTO> getPorID (@PathVariable Long id){
        Optional<AlunoDTO> aluno = alunoServico.getAlunoDTOPorID(id);
        if(aluno.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(aluno.get());
        }
    }

    // Método que responde à requisição POST para inserir um aluno no banco de dados
    @PostMapping
    public ResponseEntity<Aluno> inserir(@RequestBody Aluno aluno){
        if (alunoServico.getAlunoPorMatricula(aluno.getMatricula()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if(aluno.getPessoa() == null || pessoaServico.getPessoaPorID(aluno.getPessoa().getID()).isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        Aluno novoAluno = alunoServico.inserir(aluno);
        return ResponseEntity.created(URI.create("/alunos/" + novoAluno.getID())).body(aluno);
    }

    // Método que responde à requisição PUT para alterar os dados de um aluno no banco de dados
    @PutMapping(value = "/{id}")
    public ResponseEntity<AlunoDTO> alterar(@PathVariable Long id, @RequestBody Aluno aluno){
        Optional<Aluno> alunoBuscadoPorID = alunoServico.getAlunoPorID(id);
        if(alunoBuscadoPorID.isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            Aluno alunoBuscadoPorMatricula = alunoServico.getAlunoPorMatricula(aluno.getMatricula());
            if(alunoBuscadoPorMatricula != null && !Objects.equals(id, alunoBuscadoPorMatricula.getID())){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }else{
                aluno.setID(id);
                if(aluno.getPessoa() == null){
                    aluno.setPessoa(alunoBuscadoPorID.get().getPessoa());
                }else{
                    if(pessoaServico.getPessoaPorID(aluno.getPessoa().getID()).isEmpty()){
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
                    }
                }
                Aluno alterado = alunoServico.alterar(aluno);
                AlunoDTO alteradoDTO = new AlunoDTO(alterado);
                return ResponseEntity.ok().body(alteradoDTO);
            }
        }
    }

    // Método que responde à requisição DELETE para remover o registro de um aluno no banco de dados
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        return alunoServico.getAlunoPorID(id).map(aluno -> {
            alunoServico.remover(aluno);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
