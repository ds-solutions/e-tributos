package com.developer.demetrio.model;

import java.io.Serializable;

public class CodigoDeCobranca implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String tipo;
    private String taxaTestada;

    public CodigoDeCobranca() {
    }

    public CodigoDeCobranca(Long id, String tipo, String taxaTestada) {
        this.id = id;
        this.tipo = tipo;
        this.taxaTestada = taxaTestada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTaxaTestada() {
        return taxaTestada;
    }

    public void setTaxaTestada(String taxaTestada) {
        this.taxaTestada = taxaTestada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CodigoDeCobranca that = (CodigoDeCobranca) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
