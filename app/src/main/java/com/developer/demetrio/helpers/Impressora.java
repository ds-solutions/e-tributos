package com.developer.demetrio.helpers;

import java.io.Serializable;
import java.util.Objects;

public class Impressora implements Serializable {
    private static final long serialVersionUID = 1L;

    private String bluetoothEndereco;
    private String getBluetoothNome;

    public String getBluetoothEndereco() {
        return bluetoothEndereco;
    }

    public void setBluetoothEndereco(String bluetoothEndereco) {
        this.bluetoothEndereco = bluetoothEndereco;
    }

    public String getBluetoothNome() {
        return getBluetoothNome;
    }

    public void setBluetoothNome(String getBluetoothNome) {
        this.getBluetoothNome = getBluetoothNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Impressora that = (Impressora) o;
        if (this.bluetoothEndereco == null) {
            if (that.bluetoothEndereco != null) {
                return false;
            }
            return true;
        } else if (this.bluetoothEndereco.equals(that.bluetoothEndereco)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int i;
        if (this.bluetoothEndereco == null) {
            i = 0;
        } else {
            i = this.bluetoothEndereco.hashCode();
        }
        return i + 31;
    }
}
