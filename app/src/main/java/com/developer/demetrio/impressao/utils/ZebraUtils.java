package com.developer.demetrio.impressao.utils;

import android.content.Context;
import android.util.Log;

import com.developer.demetrio.execoes.ConexaoImpressoraException;
import com.developer.demetrio.execoes.ImpressaoException;
import com.developer.demetrio.execoes.StatusImpressoraException;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.etributos.SelecionarImpressora;
import com.developer.demetrio.util.Bluetooth;
import com.developer.demetrio.util.ConstantesSistemas;
import com.developer.demetrio.util.SettingsHelper;
/*
import com.developer.demetrio.zebra.android.comm.BluetoothPrinterConnection;
import com.developer.demetrio.zebra.android.comm.ZebraPrinterConnection;
import com.developer.demetrio.zebra.android.comm.ZebraPrinterConnectionException;
//import com.developer.demetrio.zebra.android.printer.ZebraPrinter;
*/
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.io.UnsupportedEncodingException;

public class ZebraUtils {
    private static ZebraUtils instance;
    private Context ctx;
    //private ZebraPrinterConnection zebraPrinterConnection;
    private Connection zebraPrinterConnection;

    public ZebraUtils(Context context) {
        Bluetooth.ativarBluetooth();
        this.ctx = context;
    }

    public static ZebraUtils getInstance(Context context) {
        if (instance == null) {
            instance = new ZebraUtils(context);
        }
        return instance;
    }

    public ZebraPrinter connect() throws ImpressaoException, ConexaoImpressoraException {
        if (this.zebraPrinterConnection == null || !this.zebraPrinterConnection.isConnected()) {
            initConnection();
        }
        if (this.zebraPrinterConnection == null || !this.zebraPrinterConnection.isConnected()) {
            return null;
        }
        try {
            //ZebraPrinter printer = ZebraPrinterFactory.getInstance(this.zebraPrinterConnection);
            ZebraPrinter printer = ZebraPrinterFactory.getInstance(this.zebraPrinterConnection);
            printer.getPrinterControlLanguage();
            return printer;
        } /*catch (ZebraPrinterConnectionException e) {
            disconnect();
            Bluetooth.resetarBluetooth();
            throw new ConexaoImpressoraException();
        } catch (ZebraPrinterLanguageUnknownException e2) {
            disconnect();
            Log.v(ConstantesSistemas.CATEGORIA, e2.getMessage());
            Bluetooth.resetarBluetooth();
            throw new ImpressaoException("Erro de conexão.");
        } */ catch (ConnectionException e) {
            disconnect();
            Bluetooth.resetarBluetooth();
            e.printStackTrace();
            throw new ConexaoImpressoraException();
        } catch (ZebraPrinterLanguageUnknownException e2) {
            disconnect();
            Log.v(ConstantesSistemas.CATEGORIA, e2.getMessage());
            Bluetooth.resetarBluetooth();
            throw new ImpressaoException("Erro de conexão.");
         //   e2.printStackTrace();
        }

    }

    public void disconnect() throws ImpressaoException {
        try {
            if (this.zebraPrinterConnection != null) {
               // this.zebraPrinterConnection.close();
               this.zebraPrinterConnection.close();
            }
        } catch (ConnectionException e) {
            e.printStackTrace();
            throw new ConexaoImpressoraException();
        }

        /*catch (ZebraPrinterConnectionException e) {
            throw new ConexaoImpressoraException();
        }*/
    }

  /*  public ZebraPrinterConnection getPrinterConnection() {
        return this.zebraPrinterConnection;
    } */

    public Connection getPrinterConnection() {
        return this.zebraPrinterConnection;
    }

    public boolean imprimir(StringBuilder conta) throws ImpressaoException {
       if (connect() != null) {
            return sendLabel(codificarConta(conta));
        }
        return false;
    }

    private void initConnection() throws ImpressaoException {
        this.zebraPrinterConnection = new BluetoothConnection(SettingsHelper.getBluetoothAddress(this.ctx.getApplicationContext()));
    //new BluetoothPrinterConnection(SettingsHelper.getBluetoothAddress(this.ctx.getApplicationContext()));
        try {
            if (this.zebraPrinterConnection != null) {
                this.zebraPrinterConnection.open();
            }
        } //catch (ZebraPrinterConnectionException ups) {

           catch (ConnectionException ups) {
            ups.printStackTrace();
            disconnect();
            throw new ConexaoImpressoraException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendLabel(byte[] label) throws ImpressaoException {
         try {
           // PrinterStatus printerStatus = ZebraPrinterFactory.getInstance(this.zebraPrinterConnection).getCurrentStatus();
             PrinterStatus printerStatus = ZebraPrinterFactory.getInstance(this.zebraPrinterConnection).getCurrentStatus();
            if (printerStatus.isReadyToPrint) {
                this.zebraPrinterConnection.write(label);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            } else if (printerStatus.isPaused) {
                System.out.println("Impressora está em pausa! "+ printerStatus.isPaused);
                throw new StatusImpressoraException("Impressora em pausa.");
            } else if (printerStatus.isHeadOpen) {
                System.out.println("Impressora está com a tampa aberta! "+ printerStatus.isHeadOpen);
                throw new StatusImpressoraException("A impressora está com a tampa aberta.");
            } else if (printerStatus.isPaperOut) {
                System.out.println("Impressora está sem papel! "+ printerStatus.isPaperOut);
                throw new StatusImpressoraException("A Impressora está sem papel.");
            } else {
                System.out.println("Excessão na conexão com a impressora! "+ ConexaoImpressoraException.class.toString());
                throw new ConexaoImpressoraException();
            }
        } catch (ConnectionException e) {

             e.printStackTrace();
             throw new ConexaoImpressoraException();
         } catch (ZebraPrinterLanguageUnknownException e)
        {
            e.printStackTrace();
            throw new ImpressaoException("Erro de conexão.");

         }

         /* catch (ZebraPrinterConnectionException e2) {
            System.out.println("Excessão na conexão com a impressora! "+ e2.getMessage());
            throw new ConexaoImpressoraException();
        } catch (ZebraPrinterLanguageUnknownException e3) {
            System.out.println("Excessão na conexão com a impressora! "+ e3.getMessage());
            throw new ImpressaoException("Erro de conexão.");
        }*/
    }

    private byte[] codificarConta(StringBuilder imovel) {
        byte[] configLabel = null;
        try {
            configLabel = imovel.toString().getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Log.v(ConstantesSistemas.CATEGORIA, e.getMessage());
            e.printStackTrace();
        }
        return configLabel;
    }


    public boolean verificaExistenciaImpressoraConfigigurada(Context ctx, Imovel imovel) {
        String bluetoothAddress = SettingsHelper.getBluetoothAddress(ctx);
        String bluetoothName = SettingsHelper.getPrinterName(ctx);
        if (bluetoothAddress != null && !bluetoothAddress.equals("") && bluetoothName != null && !bluetoothName.equals("")) {
            return true;
        }

        return false;
    }
}
