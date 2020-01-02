package com.developer.demetrio.model;

import java.io.Serializable;

public class Cadastro implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String inscricao;
    private String numCadastro;
    private String distrito;
    private String setor;
    private String quadra;
    private String lote;
    private String unidade;

    private AreasDoImovel areasDoImovel;
    private ValoresVenais valoresVenais;
    private Aliquota aliquota;

    public Cadastro() {
    }

    public Cadastro(Long id, String inscricao,
                    String numCadastro, String distrito,
                    String setor, String quadra, String lote,
                    String unidade, AreasDoImovel areasDoImovel,
                    ValoresVenais valoresVenais, Aliquota aliquota) {
        this.id = id;
        this.inscricao = inscricao;
        this.numCadastro = numCadastro;
        this.distrito = distrito;
        this.setor = setor;
        this.quadra = quadra;
        this.lote = lote;
        this.unidade = unidade;
        this.areasDoImovel = areasDoImovel;
        this.valoresVenais = valoresVenais;
        this.aliquota = aliquota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInscricao() {
        return inscricao;
    }

    public void setInscricao(String inscricao) {
        this.inscricao = inscricao;
    }

    public String getNumCadastro() {
        return numCadastro;
    }

    public void setNumCadastro(String numCadastro) {
        this.numCadastro = numCadastro;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getQuadra() {
        return quadra;
    }

    public void setQuadra(String quadra) {
        this.quadra = quadra;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public AreasDoImovel getAreasDoImovel() {
        return areasDoImovel;
    }

    public void setAreasDoImovel(AreasDoImovel areasDoImovel) {
        this.areasDoImovel = areasDoImovel;
    }

    public ValoresVenais getValoresVenais() {
        return valoresVenais;
    }

    public void setValoresVenais(ValoresVenais valoresVenais) {
        this.valoresVenais = valoresVenais;
    }

    public Aliquota getAliquota() {
        return aliquota;
    }

    public void setAliquota(Aliquota aliquota) {
        this.aliquota = aliquota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cadastro cadastro = (Cadastro) o;

        return id != null ? id.equals(cadastro.id) : cadastro.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
