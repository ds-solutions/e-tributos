package com.developer.demetrio.model;

import java.io.Serializable;

public class AreasDoImovel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String testada;
    private String areaDoTerreno;
    private String areaTotalDoTerreno;
    private String edificado;
    private String areaTotalEdificado;
    private String excedente;
    private String fracao;
    private Aliquota aliquota;
    private ValoresVenais valoresVenais;

    public AreasDoImovel() {
   }

    public AreasDoImovel(Long id, String testada,
                         String areaDoTerreno, String areaTotalDoTerreno,
                         String edificado, String areaTotalEdificado,
                         String excedente, String fracao, Aliquota aliquota,
                         ValoresVenais valoresVenais) {
        this.id = id;
        this.testada = testada;
        this.areaDoTerreno = areaDoTerreno;
        this.areaTotalDoTerreno = areaTotalDoTerreno;
        this.edificado = edificado;
        this.areaTotalEdificado = areaTotalEdificado;
        this.excedente = excedente;
        this.fracao = fracao;
        this.aliquota = aliquota;
        this.valoresVenais = valoresVenais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestada() {
        return testada;
    }

    public void setTestada(String testada) {
        this.testada = testada;
    }

    public String getAreaDoTerreno() {
        return areaDoTerreno;
    }

    public void setAreaDoTerreno(String areaDoTerreno) {
        this.areaDoTerreno = areaDoTerreno;
    }

    public String getAreaTotalDoTerreno() {
        return areaTotalDoTerreno;
    }

    public void setAreaTotalDoTerreno(String areaTotalDoTerreno) {
        this.areaTotalDoTerreno = areaTotalDoTerreno;
    }

    public String getEdificado() {
        return edificado;
    }

    public void setEdificado(String edificado) {
        this.edificado = edificado;
    }

    public String getAreaTotalEdificado() {
        return areaTotalEdificado;
    }

    public void setAreaTotalEdificado(String areaTotalEdificado) {
        this.areaTotalEdificado = areaTotalEdificado;
    }

    public String getExcedente() {
        return excedente;
    }

    public void setExcedente(String excedente) {
        this.excedente = excedente;
    }

    public String getFracao() {
        return fracao;
    }

    public void setFracao(String fracao) {
        this.fracao = fracao;
    }

    public Aliquota getAliquota() {
        return aliquota;
    }

    public void setAliquota(Aliquota aliquota) {
        this.aliquota = aliquota;
    }

    public ValoresVenais getValoresVenais() {
        return valoresVenais;
    }

    public void setValoresVenais(ValoresVenais valoresVenais) {
        this.valoresVenais = valoresVenais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreasDoImovel that = (AreasDoImovel) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
