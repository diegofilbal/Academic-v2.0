package com.example.academicv2_0.service;

import com.example.academicv2_0.model.Aluno;
import com.example.academicv2_0.model.dto.AlunoDTO;
import com.example.academicv2_0.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    // Objeto para invocar métodos de manipulação no banco de dados na tabela de alunos
    private AlunoRepository repository;

    @Autowired
    private void setRepository(AlunoRepository repository) {
        this.repository = repository;
    }

    // Método que consulta o banco e retorna todos os registros da tabela de alunos
    public List<AlunoDTO> getAlunos() {
        List<Aluno> listaAluno = repository.findAll();
        List<AlunoDTO> listaAlunoDTO = new ArrayList<>();

        for (Aluno a : listaAluno) {
            listaAlunoDTO.add(new AlunoDTO(a));
        }

        return listaAlunoDTO;
    }

    // Métodos que buscam um registro específico da tabela de alunos conforme o ID recebido
    public Optional<Aluno> getAlunoPorID(Long id) {
        return repository.findById(id);
    }

    public Optional<AlunoDTO> getAlunoDTOPorID(Long id) {
        Optional<Aluno> optAluno = getAlunoPorID(id);
        Optional<AlunoDTO> optAlunoDTO = Optional.empty();

        if(optAluno.isPresent()){
            AlunoDTO alunoDTO = new AlunoDTO(optAluno.get());
            optAlunoDTO = Optional.of(alunoDTO);
        }

        return optAlunoDTO;
    }

    // Método que busca um registro específico da tabela de alunos conforme a matrícula recebida
    public Aluno getAlunoPorMatricula(String matricula) {
        return repository.findByMatricula(matricula).orElse(null);
    }

    // Método que insere um registro na tabela de alunos do banco de dados
    public Aluno inserir(Aluno aluno) {
        return repository.save(aluno);
    }

    // Método que altera um registro na tabela de alunos do banco de dados
    public Aluno alterar(Aluno aluno) {
        return repository.save(aluno);
    }

    // Método que remove um registro da tabela de alunos do banco de dados
    public void remover(Aluno aluno) {
        repository.delete(aluno);
    }

}
