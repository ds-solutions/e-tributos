package com.developer.demetrio.util;

import android.bluetooth.BluetoothAdapter;

public class Bluetooth {
    public static boolean ativarBluetooth() {
        if (!ConstantesSistemas.SIMULADOR) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!bluetoothAdapter.isEnabled()) {
                return bluetoothAdapter.enable();
            }
        }
        return false;
    }

    public static boolean desativarBluetooth() {
        if (!ConstantesSistemas.SIMULADOR){
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
                return true;
            }
        }
        return false;
    }

    public static void resetarBluetooth() {
        if (!ConstantesSistemas.SIMULADOR) {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (desativarBluetooth()) {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ativarBluetooth();
        }
    }
}
