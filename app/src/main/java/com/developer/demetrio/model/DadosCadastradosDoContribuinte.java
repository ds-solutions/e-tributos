package com.developer.demetrio.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class DadosCadastradosDoContribuinte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nome;
    private String cpf;
    private String rg;
    private String orgEmissor;
    private Date dataNasc;
    private String estadoCivil;
    private String nacionalidade;
    private String naturalidade;
    private String raca;
    private String sexo;
    private String email;
    private String numeroCelular;

    public DadosCadastradosDoContribuinte() {
    }

    public DadosCadastradosDoContribuinte(Long id, String nome, String cpf, String rg, String orgEmissor, Date dataNasc, String estadoCivil, String nacionalidade, String naturalidade, String raca, String sexo, String email, String numeroCelular) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.orgEmissor = orgEmissor;
        this.dataNasc = dataNasc;
        this.estadoCivil = estadoCivil;
        this.nacionalidade = nacionalidade;
        this.naturalidade = naturalidade;
        this.raca = raca;
        this.sexo = sexo;
        this.email = email;
        this.numeroCelular = numeroCelular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getOrgEmissor() {
        return orgEmissor;
    }

    public void setOrgEmissor(String orgEmissor) {
        this.orgEmissor = orgEmissor;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Date dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DadosCadastradosDoContribuinte)) return false;
        DadosCadastradosDoContribuinte that = (DadosCadastradosDoContribuinte) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
