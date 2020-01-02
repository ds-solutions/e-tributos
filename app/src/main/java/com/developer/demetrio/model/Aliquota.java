package com.developer.demetrio.model;

import java.io.Serializable;

public class Aliquota implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String terreno;
    private String edificado;
    private String zoneamento;
    private String tipoConstrucao;
    private CodigoDeCobranca codigoDeCobranca;

    public Aliquota() {
    }

    public Aliquota(Long id, String terreno, String edificado,
                    String zoneamento, String tipoConstrucao,
                    CodigoDeCobranca codigoDeCobranca) {
        this.id = id;
        this.terreno = terreno;
        this.edificado = edificado;
        this.zoneamento = zoneamento;
        this.tipoConstrucao = tipoConstrucao;
        this.codigoDeCobranca = codigoDeCobranca;
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

    public String getEdificado() {
        return edificado;
    }

    public void setEdificado(String edificado) {
        this.edificado = edificado;
    }

    public String getZoneamento() {
        return zoneamento;
    }

    public void setZoneamento(String zoneamento) {
        this.zoneamento = zoneamento;
    }

    public String getTipoConstrucao() {
        return tipoConstrucao;
    }

    public void setTipoConstrucao(String tipoConstrucao) {
        this.tipoConstrucao = tipoConstrucao;
    }

    public CodigoDeCobranca getCodigoDeCobranca() {
        return codigoDeCobranca;
    }

    public void setCodigoDeCobranca(CodigoDeCobranca codigoDeCobranca) {
        this.codigoDeCobranca = codigoDeCobranca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Aliquota aliquota = (Aliquota) o;

        return id.equals(aliquota.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
