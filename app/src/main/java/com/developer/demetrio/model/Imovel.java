package com.developer.demetrio.model;

import java.io.Serializable;

public class Imovel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Integer indcEmissaoConta;
    private Integer indcEnvioZap;
    private Integer indcEnvioEmail;
    private Cadastro cadastro;
    private Endereco endereco;
    private Contribuinte contribuinte;
    private Tributo tributo = new Tributo();
    private LatLng latLng = new LatLng();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cadastro getCadastro() {
        return cadastro;
    }

    public void setCadastro(Cadastro cadastro) {
        this.cadastro = cadastro;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Contribuinte getContribuinte() {
        return contribuinte;
    }

    public void setContribuinte(Contribuinte contribuinte) {
        this.contribuinte = contribuinte;
    }



    public Tributo getTributo() {
        return tributo;
    }

    public void setTributo(Tributo tributo) {
        this.tributo = tributo;
    }

    public Integer getIndcEmissaoConta() {
        if (this.indcEmissaoConta == null) {
            this.indcEmissaoConta = 0;
        }
        return this.indcEmissaoConta;
    }

    public void setIndcEmissaoConta(Integer indcEmissaoConta) {
        this.indcEmissaoConta = indcEmissaoConta;
    }

    public Integer getIndcEnvioZap() {
        if (indcEnvioZap == null) {
            indcEnvioZap = 0;
        }
        return indcEnvioZap;
    }

    public void setIndcEnvioZap(Integer indcEnvioZap) {
        this.indcEnvioZap = indcEnvioZap;
    }

    public Integer getIndcEnvioEmail() {
        if (indcEnvioEmail == null) {
            indcEnvioEmail = 0;
        }
        return indcEnvioEmail;
    }

    public void setIndcEnvioEmail(Integer indcEnvioEmail) {
        this.indcEnvioEmail = indcEnvioEmail;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Imovel)) return false;
        Imovel imovel = (Imovel) o;
        return id != null ? id.equals(imovel.id): imovel.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
