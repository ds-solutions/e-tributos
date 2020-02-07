package com.developer.demetrio.etributos;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;


import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.view.View.OnClickListener;

import com.developer.demetrio.adapters.ListaImpressoraAdapter;
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.helpers.Impressora;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.util.Bluetooth;
import com.developer.demetrio.util.SettingsHelper;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterBluetooth;
//import com.developer.demetrio.zebra.android.discovery.DiscoveredPrinterBluetooth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelecionarImpressora extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVER_BT = 1;
    private ListaImpressoraAdapter adapter;
    private Button btPesquisa;
    private Impressora impressora;
    private Map<String, DiscoveredPrinterBluetooth> impressoras = new HashMap<>();
    private ArrayList<Impressora> impressorasEncontradas = null;
    private ListView listView;
    private OnItemClickListener deviceClickListener = new C_Extra_01();
    private TextView status, status_conexao_impressora;
    private BluetoothAdapter myBluetoothAdapter;
    private ImageView imgBlue;
    private LayoutInflater inflater;

    private IPTU iptu;


    /****
     *  ESTA CLASSE VAI CHAMAR O MÉTODO ONCLICKLISTENER
     *  QUE FAZ UMA NOVA BUSCA POR DISPOSITIVOS NA TELA DE ATIVAÇÃO DO BLUETOOTH NATIVO
     */
    class C_Extra implements OnClickListener {
        C_Extra() {
        }

        @Override
        public void onClick(View view) {
            SelecionarImpressora.this.impressorasEncontradas.clear();
            SelecionarImpressora.this.impressoras.clear();
            SelecionarImpressora.this.adapter = null;
            SelecionarImpressora.this.listView.setAdapter(SelecionarImpressora.this.adapter);
            SelecionarImpressora.this.startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
        }
    }

        class C_Extra_01 implements OnItemClickListener {

        C_Extra_01() {
        }

        @SuppressLint("ResourceType")
        @Override
        public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {

                class C_Extra02 implements DialogInterface.OnClickListener {
                    C_Extra02() {
                    }
                    public void onClick(DialogInterface dialog, int i) {
                        SelecionarImpressora.this.finish();
                    }


                }

                    SelecionarImpressora.this.impressora = (Impressora) view.getTag();
                    DiscoveredPrinterBluetooth p = (DiscoveredPrinterBluetooth)
                            SelecionarImpressora.this.impressoras.get(SelecionarImpressora.this.impressora.getBluetoothNome() +
                                    " ("+ SelecionarImpressora.this.impressora.getBluetoothEndereco() + ")");

                    SettingsHelper.saveBluetoothAddress(SelecionarImpressora.this.getApplicationContext(), p.address);
                    SettingsHelper.savePrinterName(SelecionarImpressora.this.getApplicationContext(), /*"@" + "zebra" + "@" +*/p.friendlyName);
                    SelecionarImpressora.this.iptu = (IPTU) SelecionarImpressora.this.getIntent().getSerializableExtra("iptu");
                    if (SelecionarImpressora.this.iptu == null) {
                        Builder alerta =
                                new Builder(SelecionarImpressora.this);
                        alerta.setTitle(R.string.sucesso_conexao);
                        alerta.setNeutralButton(SelecionarImpressora.this.getString(17039370)
                                , new C_Extra02());
                        alerta.show();
                    }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bluetooth.ativarBluetooth();
        setContentView(R.layout.activity_selecionar_impressora);
        this.imgBlue = findViewById(R.id.img_blue);
        this.status = (TextView) findViewById(R.id.status);
        this.btPesquisa = (Button) findViewById(R.id.bt_pesquisar);
        this.listView = (ListView) findViewById(R.id.list_impressoras);
        this.status_conexao_impressora = (TextView) findViewById(R.id.status_conexao_impressora);
        Fachada.setContext(this);
        this.listView.setOnItemClickListener(this.deviceClickListener);
        this.impressorasEncontradas= new ArrayList<>();
        this.btPesquisa.setOnClickListener(new C_Extra());

        onResume();
    }

    protected void onResume() {
        super.onResume();
        setarImagem();
        popularListView();
    }

    private void setarImagem() {
        this.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.myBluetoothAdapter == null || !this.myBluetoothAdapter.isEnabled()) {
            this.imgBlue.setImageResource(R.drawable.bluetooth_off);
            this.status.setText("Desabilitado");
        }
        if (this.myBluetoothAdapter != null && this.myBluetoothAdapter.isEnabled()) {
            this.imgBlue.setImageResource(R.drawable.bluetooth_on);
            this.status.setText("Habilitado");
        }
    }

    private void popularListView() {
        this.inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        BluetoothAdapter bA = BluetoothAdapter.getDefaultAdapter();
        for (BluetoothDevice dispositivo: bA.getBondedDevices()){
            BluetoothDevice device = bA.getRemoteDevice(dispositivo.toString());
          boolean x = !(device.getBluetoothClass() == null
                    || device.getBluetoothClass().getMajorDeviceClass() != 1536
                    || (device.getBluetoothClass().getDeviceClass() & 128) == 0);
            if (!(device.getBluetoothClass() == null)){
                DiscoveredPrinterBluetooth p = new DiscoveredPrinterBluetooth(device.getAddress(), device.getName());
    String key = p.friendlyName + " (" + p.address +")";
                Impressora impressora = new Impressora();
                impressora.setBluetoothEndereco(device.getAddress());
                impressora.setBluetoothNome(device.getName());
                if (!this.impressorasEncontradas.contains(impressora)){
                    this.impressorasEncontradas.add(impressora);
                    this.impressoras.put(key, p);
                    this.adapter = new ListaImpressoraAdapter(this, this.impressorasEncontradas, this.inflater);
                    this.listView.setAdapter(this.adapter);
                }
            }
        }

        if (this.impressorasEncontradas.size() == 0) {
            this.status_conexao_impressora.setText(R.string.dispositivo_nao_pareado);
        } else {
            this.status_conexao_impressora.setText(R.string.selecione_dispositivo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK) {
                    this.imgBlue.setImageResource(R.drawable.bluetooth_on);
                    this.status.setText("Habilitado!");

                } else {
                    this.imgBlue.setImageResource(R.drawable.bluetooth_off);
                    this.status.setText("Desabilitado!");
                }
                break;
        }
        popularListView();
        super.onActivityResult(requestCode, resultCode, data);

    }

    public void pesquisarImpressoras(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SelecionarImpressora.this.startActivity(new Intent("android.settings.BLUETOOTH_SETTINGS"));
                onResume();
            }
        });
    }
}
