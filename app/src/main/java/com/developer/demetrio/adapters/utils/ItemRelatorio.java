package com.developer.demetrio.adapters.utils;


import org.apache.commons.lang3.StringUtils;

public class ItemRelatorio {
    private int img;
    private String titulo;
    private String dado;

    public ItemRelatorio(int img, String titulo, String dado) {
        this.img = img;
        this.titulo = titulo;
        this.dado = dado;
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

    public String getDado() {
        return dado;
    }

    public void setDado(String dado) {
        this.dado = dado;
    }
}
