package com.example.academicv2_0.model.dto;

import com.example.academicv2_0.model.Aluno;

public class AlunoDTO {

    // TODO Adicionar os outros dados de Pessoa como atributos de AlunoDTO
    private Long id;
    private Long idPessoa;
    private String nomePessoa;
    private String matricula;
    private Integer anoEntrada;

    public AlunoDTO() {
    }

    public AlunoDTO(Aluno aluno) {
        id = aluno.getID();
        idPessoa = aluno.getPessoa().getID();
        nomePessoa = aluno.getPessoa().getNome();
        matricula = aluno.getMatricula();
        anoEntrada = aluno.getAnoEntrada();
    }

    public AlunoDTO(Long idPessoa, String matricula, int anoEntrada) {
        this.idPessoa = idPessoa;
        this.matricula = matricula;
        this.anoEntrada = anoEntrada;
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Integer getAnoEntrada() {
        return anoEntrada;
    }

    public void setAnoEntrada(Integer anoEntrada) {
        this.anoEntrada = anoEntrada;
    }
}
