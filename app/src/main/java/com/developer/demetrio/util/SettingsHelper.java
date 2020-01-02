package com.developer.demetrio.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SettingsHelper {
    private static final String PREFS_NAME = "OurSavedAddress";
    private static final String bluetoothAddressKey = "ZEBRA_DEMO_BLUETOOTH_ADDRESS";
    private static final String printerNameKey = "ZEBRA_PRINTER_NAME";
    private static final String tcpAddressKey = "ZEBRA_DEMO_TCP_ADDRESS";
    private static final String tcpPortKey = "ZEBRA_DEMO_TCP_PORT";

    public static String getIp(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(tcpAddressKey, "");
    }

    public static String getPort(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(tcpPortKey, "");
    }

    public static String getBluetoothAddress(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(bluetoothAddressKey, "");
    }

    public static String getPrinterName(Context context) {
        return context.getSharedPreferences(PREFS_NAME, 0).getString(printerNameKey, "");
    }

    public static void saveIp(Context context, String ip) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(tcpAddressKey, ip);
        editor.commit();
    }

    public static void savePort(Context context, String port) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(tcpPortKey, port);
        editor.commit();
    }

    public static void saveBluetoothAddress(Context context, String address) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(bluetoothAddressKey, address);
        editor.commit();
    }

    public static void savePrinterName(Context context, String name) {
        Editor editor = context.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putString(printerNameKey, name);
        editor.commit();
    }
}
