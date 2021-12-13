package com.example.academicv2_0.model;

import javax.persistence.*;

@Entity
@Table( schema = "graduacao", name = "aluno")
public class Aluno {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "aluno_seq")
    @SequenceGenerator(name = "aluno_seq", schema = "graduacao", sequenceName = "aluno_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @Basic
    @Column(name = "matricula")
    private String matricula;

    @Basic
    @Column(name = "ano_entrada")
    private int anoEntrada;

    public Aluno() {
    }

    public Aluno(Pessoa pessoa, String matricula, int anoEntrada) {
        this.pessoa = pessoa;
        this.matricula = matricula;
        this.anoEntrada = anoEntrada;
    }

    @Override
    public String toString() {
        return "Pessoa vinculada (Nome, CPF): " + pessoa.getNome() + ", " + pessoa.getCPF() + "\n" +
                "Matrícula: " + matricula + "\n" +
                "Ano de entrada: " + anoEntrada + "\n" +
                "---------------------------------------------";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnoEntrada() {
        return anoEntrada;
    }

    public void setAnoEntrada(int anoEntrada) {
        this.anoEntrada = anoEntrada;
    }
}