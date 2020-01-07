package com.developer.demetrio.iptu;

import java.io.Serializable;

public class DescricaoDaDivida implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String codigo;
    private String descricao;
    private String valor;
    private String pontualidade;
    private String isencao;
    private long id_IPTU;

    public DescricaoDaDivida() {
    }

    public DescricaoDaDivida(long id, String codigo, String descricao,
                             String valor, String pontualidade,
                             String isencao, long id_IPTU) {
        this.id = id;
        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
        this.pontualidade = pontualidade;
        this.isencao = isencao;
        this.id_IPTU = id_IPTU;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPontualidade() {
        return pontualidade;
    }

    public void setPontualidade(String pontualidade) {
        this.pontualidade = pontualidade;
    }

    public String getIsencao() {
        return isencao;
    }

    public void setIsencao(String isencao) {
        this.isencao = isencao;
    }

    public long getId_IPTU() {
        return id_IPTU;
    }

    public void setId_IPTU(long id_IPTU) {
        this.id_IPTU = id_IPTU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescricaoDaDivida that = (DescricaoDaDivida) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
