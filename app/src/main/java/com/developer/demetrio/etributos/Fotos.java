package com.developer.demetrio.etributos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.model.Comprovante;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioContribuinte;
import com.developer.demetrio.repositorio.RepositorioDadosAtualizadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.util.ConstantesSistemas;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fotos extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private RepositorioImovel imoveis;
    private RepositorioDadosAtualizadosDoContribuinte atualizados;

    private Imovel imovel;
    private AtualizacaoDoContribuinte atualizado;
    private Comprovante comprovante;

    private ImageView rgFrente, rgVerso, cpf, escritura;

    private final int PASTA_FOTOS = 1;
    private final int REQUEST_TIRAR_FOTO = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private final int CAMARA = 4;

    private static final String FOTO_RG_FRENTE = "rg_frente";
    private static final String FOTO_RG_VERSO = "rg_frente";
    private static final String FOTO_CPF = "rg_frente";
    private static final String FOTO_ESCRITURA = "rg_frente";

    private static final String[] CHAVE_REFERENCIA =
            new String[]{FOTO_RG_FRENTE, FOTO_RG_VERSO, FOTO_CPF, FOTO_ESCRITURA};

    private File foto;

    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        this.ctx = this;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            if (parametros != null) {
                carregarImovel(parametros.getLong("imovel"));
            }
        }
        this.rgFrente = (ImageView) findViewById(R.id.id_rg_frente);
        this.rgVerso = (ImageView) findViewById(R.id.id_rg_verso);
        this.cpf = (ImageView) findViewById(R.id.id_cpf);
        this.escritura = (ImageView) findViewById(R.id.id_escritura);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
              ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSAO_REQUEST);
            }
        }

        ifNotExistsCreateNewInstance();
        verificarExistenciaDeFoto();

        this.rgFrente.setOnClickListener(new FotoRgFrente());
        this.rgVerso.setOnClickListener(new FotoRgVerso());
        this.cpf.setOnClickListener(new FotoCPF());
        this.escritura.setOnClickListener(new FotoEscritura());
    }

    private void verificarExistenciaDeFoto() {
        if (StringUtils.isNotBlank(comprovante.getFotoRgFrente())) {

        }
        else {
            this.rgFrente.setImageResource(R.drawable.rg_frente);
        }
        if (StringUtils.isNotBlank(comprovante.getFotoRgVerso())) {

        }
        else {
            this.rgVerso.setImageResource(R.drawable.rg_verso);
        }

        if (StringUtils.isNotBlank(comprovante.getFotoCPF())) {

        }
        else {
            this.cpf.setImageResource(R.drawable.cpf);
        }
        if (StringUtils.isNotBlank(comprovante.getFotoEscritura())) {

        }
        else {
            this.escritura.setImageResource(R.drawable.escritura);
        }
    }

    private void ifNotExistsCreateNewInstance() {
        if (true) {
            this.comprovante = new Comprovante();
        }
    }


    class FotoRgFrente implements View.OnClickListener {
        public FotoRgFrente() {

        }

        @Override
        public void onClick(View view) {
            Intent intentRgFrente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgFrente.resolveActivity(getPackageManager()) != null) {
                foto = null;
                try {
                    foto = criarArquivoDeImagem(0);
                }catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Arquivo criado em: "+foto.toString());
                if (foto != null) {
                  //  Uri photoURI = Uri.parse(photoFile.toString());
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName()+".provider", foto);
                    System.out.println("O que vem no photoURI: "+photoURI);
                    intentRgFrente.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intentRgFrente, CAMARA);

                }
            }
        }
    }

    class FotoRgVerso implements View.OnClickListener {
        public FotoRgVerso() {

        }

        @Override
        public void onClick(View view) {
            Intent intentRgVerso = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgVerso.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intentRgVerso, CAMARA);
            }
        }
    }

    class FotoCPF implements View.OnClickListener {
        public FotoCPF() {

        }

        @Override
        public void onClick(View view) {
            Intent intentCPF = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentCPF.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intentCPF, CAMARA);
            }
        }
    }

    class FotoEscritura implements View.OnClickListener {
        public FotoEscritura() {

        }

        @Override
        public void onClick(View view) {
            Intent intentEscritura = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentEscritura.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intentEscritura, CAMARA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bundle extras = data.getExtras();
            Bitmap imagemCapturada = (Bitmap) extras.get("data");
        }
        if (requestCode == CAMARA && resultCode == RESULT_OK) {
          sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(foto)));
           exibirImagem();
        }
    }

    private void exibirImagem() {
        int targetW = this.rgFrente.getWidth();
        int targetH = this.rgFrente.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(foto.getAbsolutePath(), bmOptions);
        int fotoW = bmOptions.outWidth;
        int fotoH = bmOptions.outHeight;
        int scala = Math.min(fotoW/targetW, fotoH/targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scala;
        Bitmap bitmap = BitmapFactory.decodeFile(foto.getAbsolutePath(), bmOptions);
        this.rgFrente.setImageBitmap(bitmap);
    }

    private void carregarImovel(long imovel) {
        conectarBanco();
        try {
            this.imovel = new RepositorioImovel(this.conexao).buscarImovelPorId(imovel);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        if (this.imovel.getContribuinte() != null) {

            carregarContribuinte(this.imovel.getContribuinte().getId());
        }
    }

    private void carregarContribuinte(Long id) {
        conectarBanco();
        Contribuinte contribuinte = new Contribuinte();
        try {
            contribuinte = new RepositorioContribuinte(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        desconectarBanco();
        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            carregarOsDadosAtualizados(contribuinte.getAtualizacaoDoContribuinte().getId());
        }

    }

    private void carregarOsDadosAtualizados(Long id) {
        conectarBanco();
        System.out.println("id do novo cadastro: " +id);
        try {
            this.atualizado = new RepositorioDadosAtualizadosDoContribuinte(this.conexao).buscar(id);
        } catch (RepositorioException e) {
            e.printStackTrace();
        }

        desconectarBanco();
    }

    private void desconectarBanco() {
        this.conexao.close();
    }

    private void conectarBanco() {
        this.conexao = new ConexaoDataBase().concectarComBanco(this);
    }


    //jogar em outra classe depois

    private File criarArquivoDeImagem(int chave) throws IOException{
        String nomeDaMidia = this.imovel.getId()+ "_" +atualizado.getId()
                +"_"+ CHAVE_REFERENCIA[chave]+ "_"
                + new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss").format(new Date());

        File dirMida = new File(ConstantesSistemas.CAMINHO_FOTOS);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
        File image = new File(dirMida.getPath(), nomeDaMidia+".jpg"); /*File.createTempFile(nomeDaMidia,".jpg",getExternalFilesDir(dirMida.toString()));*/
        System.out.println("Diretório das fotos: "+dirMida.toString());
        System.out.println("Nome da foto: "+image.toString());
        if (chave == 0) {
            comprovante.setFotoRgFrente(nomeDaMidia);
        }
        if (chave == 1) {
            comprovante.setFotoRgVerso(nomeDaMidia);
        }
        if (chave == 2) {
            comprovante.setFotoCPF(nomeDaMidia);
        }
        if (chave == 3) {
            comprovante.setFotoEscritura(nomeDaMidia);
        }
      return image;
    }

}
