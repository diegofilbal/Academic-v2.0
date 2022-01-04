package com.example.academicv2_0.model.dto;

import com.example.academicv2_0.model.Aluno;
import com.example.academicv2_0.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDTO {

    private Long id;
    private String nome;
    private String cpf;
    private List<Long> idAlunos = new ArrayList<>();

    public PessoaDTO() {
    }

    public PessoaDTO(Pessoa pessoa) {
        id = pessoa.getID();
        nome = pessoa.getNome();
        cpf = pessoa.getCPF();
        List<Aluno> listaAluno = pessoa.getAlunos();
        for (Aluno a : listaAluno) {
            idAlunos.add(a.getID());
        }
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String cpf) {
        this.cpf = cpf;
    }

    public List<Long> getIdAlunos() {
        return idAlunos;
    }

    public void setIdAlunos(List<Long> idAlunos) {
        this.idAlunos = idAlunos;
    }
}
