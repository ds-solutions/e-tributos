package com.developer.demetrio.model;

import java.io.Serializable;

public class ValoresVenais implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String terreno;
    private String edificada;
    private String excedente;
    private String total;

    public ValoresVenais() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public String getEdificada() {
        return edificada;
    }

    public void setEdificada(String edificada) {
        this.edificada = edificada;
    }

    public String getExcedente() {
        return excedente;
    }

    public void setExcedente(String excedente) {
        this.excedente = excedente;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValoresVenais that = (ValoresVenais) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
