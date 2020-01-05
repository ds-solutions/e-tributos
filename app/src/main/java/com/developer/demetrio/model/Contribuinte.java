package com.developer.demetrio.model;

import java.io.Serializable;

public class Contribuinte implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private DadosCadastradosDoContribuinte dadosCadastradosDoContribuinte;
    private AtualizacaoDoContribuinte atualizacaoDoContribuinte;


    public Contribuinte() {
    }

    public Contribuinte(Long id, DadosCadastradosDoContribuinte dadosCadastradosDoContribuinte, AtualizacaoDoContribuinte atualizacaoDoContribuinte) {
        this.id = id;
        this.dadosCadastradosDoContribuinte = dadosCadastradosDoContribuinte;
        this.atualizacaoDoContribuinte = atualizacaoDoContribuinte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosCadastradosDoContribuinte getDadosCadastradosDoContribuinte() {
        return dadosCadastradosDoContribuinte;
    }

    public void setDadosCadastradosDoContribuinte(DadosCadastradosDoContribuinte dadosCadastradosDoContribuinte) {
        this.dadosCadastradosDoContribuinte = dadosCadastradosDoContribuinte;
    }

    public AtualizacaoDoContribuinte getAtualizacaoDoContribuinte() {
        return atualizacaoDoContribuinte;
    }

    public void setAtualizacaoDoContribuinte(AtualizacaoDoContribuinte atualizacaoDoContribuinte) {
        this.atualizacaoDoContribuinte = atualizacaoDoContribuinte;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contribuinte other = (Contribuinte) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
