package com.developer.demetrio.adapters.utils;

public class ItemRelatorio {
    private int img;
    private String titulo;
    private long qtd;
    private String dado;
    private String comparativo;

    public ItemRelatorio() {
    }

    public ItemRelatorio(int img, String titulo, long qtd, String dado, String comparativo) {
        this.img = img;
        this.titulo = titulo;
        this.qtd = qtd;
        this.dado = dado;
        this.comparativo = comparativo;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getQtd() {
        return qtd;
    }

    public void setQtd(long qtd) {
        this.qtd = qtd;
    }

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }

    public String getComparativo() {
        return comparativo;
    }

    public void setComparativo(String comparativo) {
        this.comparativo = comparativo;
    }
}
