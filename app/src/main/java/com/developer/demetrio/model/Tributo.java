package com.developer.demetrio.model;

import android.util.Log;

import com.developer.demetrio.iptu.IPTU;

import java.io.Serializable;

public class Tributo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private IPTU iptu = new IPTU();

    public Tributo() {
    }

    public Tributo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IPTU getIptu() {
        return iptu;
    }

    public void setIptu(IPTU iptu) {
        this.iptu = iptu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tributo tributo = (Tributo) o;

        return id != null ? id.equals(tributo.id) : tributo.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
