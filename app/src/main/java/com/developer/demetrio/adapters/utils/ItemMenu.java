package com.developer.demetrio.adapters.utils;

public class ItemMenu {
    private int img;
    private String descricao;

    public ItemMenu(int img, String descricao){
        this.img = img;
        this.descricao = descricao;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
