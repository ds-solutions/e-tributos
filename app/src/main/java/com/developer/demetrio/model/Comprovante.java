package com.developer.demetrio.model;

import java.io.Serializable;
import java.util.Objects;

public class Comprovante implements Serializable {

    private long id;
    private String fotoRgFrente;
    private String fotoRgVerso;
    private String fotoCPF;
    private String fotoEscritura;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFotoRgFrente() {
        return fotoRgFrente;
    }

    public void setFotoRgFrente(String fotoRgFrente) {
        this.fotoRgFrente = fotoRgFrente;
    }

    public String getFotoRgVerso() {
        return fotoRgVerso;
    }

    public void setFotoRgVerso(String fotoRgVerso) {
        this.fotoRgVerso = fotoRgVerso;
    }

    public String getFotoCPF() {
        return fotoCPF;
    }

    public void setFotoCPF(String fotoCPF) {
        this.fotoCPF = fotoCPF;
    }

    public String getFotoEscritura() {
        return fotoEscritura;
    }

    public void setFotoEscritura(String fotoEscritura) {
        this.fotoEscritura = fotoEscritura;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comprovante)) return false;
        Comprovante that = (Comprovante) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
