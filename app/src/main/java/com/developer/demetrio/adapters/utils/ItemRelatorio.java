package com.developer.demetrio.adapters.utils;


public class ItemRelatorio {
    private int img;
    private String titulo;

    public ItemRelatorio(int img, String titulo) {
        this.img = img;
        this.titulo = titulo;
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
}
