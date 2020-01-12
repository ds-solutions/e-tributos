package com.developer.demetrio.etributos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.developer.demetrio.adapters.PageViewAdapter;
import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.fragments.DadosDeAtualizacaoProprietario;
import com.developer.demetrio.fragments.DadosDoImovel;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.model.Aliquota;
import com.developer.demetrio.model.AreasDoImovel;
import com.developer.demetrio.model.Cadastro;
import com.developer.demetrio.model.CodigoDeCobranca;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Endereco;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.model.LatLng;
import com.developer.demetrio.model.ValoresVenais;
import com.developer.demetrio.service.GPS_Service;
import com.developer.demetrio.service.Mail;
import com.developer.demetrio.service.Zap;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;


public class ListaImoveis extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    private Fachada fachada = Fachada.getInstance();
    //  private OnClickListener imprimir = new C_Imprimir();
    private Location location;
    private static final int PLAY_SERVICE_RESOLUTION_REQUEST = 9000;
    private long UPDATE_INTERVAL = 10, FASTEST_INTRVAL = 10;

    private static final int ALL_PERMISSIONS_RESULT = 1011;
    public static final Integer FRAGMENTO_DADOS_DO_IMOVEL = 1;
    private long idImovel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_imoveis);
        List<Fragment> fragments = new ArrayList<>();
        Intent intent = getIntent();

        if (intent != null) {
            Bundle parametros = intent.getExtras();
            if (parametros != null) {
               this.idImovel = parametros.getLong("id");
            }
        }

        fragments.add(new DadosDoImovel(this, this, this.idImovel));
        fragments.add(new DadosDeAtualizacaoProprietario(this, this.idImovel));

        this.viewPager = (ViewPager) findViewById(R.id.view_page);
        this.pagerAdapter = new PageViewAdapter(getSupportFragmentManager(), fragments);
        this.viewPager.setAdapter(this.pagerAdapter);

        Fachada.setContext(this);
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ConsultarImoveis.class));
        finishAffinity();
        return;
    }


}
