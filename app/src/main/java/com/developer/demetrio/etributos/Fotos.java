package com.developer.demetrio.etributos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.developer.demetrio.databases.ConexaoDataBase;
import com.developer.demetrio.execoes.LogErro;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.AtualizacaoDoContribuinte;
import com.developer.demetrio.model.Comprovante;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioComprovante;
import com.developer.demetrio.repositorio.RepositorioDadosAtualizadosDoContribuinte;
import com.developer.demetrio.repositorio.RepositorioImovel;
import com.developer.demetrio.util.ConstantesSistemas;
import com.developer.demetrio.util.ImageUtil;
import com.developer.demetrio.util.Util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Fotos extends AppCompatActivity {

    private SQLiteDatabase conexao;
    private RepositorioImovel imoveis;
    private RepositorioDadosAtualizadosDoContribuinte atualizados;

    private Imovel imovel;
    private AtualizacaoDoContribuinte atualizado;
    private Comprovante comprovante;

    private ImageView rgFrente, rgVerso, cpf, escritura;
    private Button btSalvar, btCancelar;

    private final int IMAGEM_DO_ARQUIVO = 1;
    private final int PERMISSAO_REQUEST = 2;
    private final int TIRAR_FOTO = 3;
    private final int CAMARA = 4;

    public int cont = 0;

    private static final String FOTO_RG_FRENTE = "rg_frente";
    private static final String FOTO_RG_VERSO = "rg_verso";
    private static final String FOTO_CPF = "cpf";
    private static final String FOTO_ESCRITURA = "escritura";

    private static final String[] CHAVE_REFERENCIA =
            new String[]{FOTO_RG_FRENTE, FOTO_RG_VERSO, FOTO_CPF, FOTO_ESCRITURA};

    private File foto;

    private Context ctx;

    private int chave = 0;

    private boolean isCamera = false, isFile = false;

    private String frenteRg, versoRG, cpfFoto, escrituraFoto;

    private int largura, altura;
    private static final int LARGURA = 200;
    private static final int ALTURA = 131;

    private Handler handler;
    private Bitmap bitmap;

    public Activity activity;

    public File aux;

    private CarregarImagem carregarImagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);
        this.ctx = this;
        this.activity = this;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle parametros = intent.getExtras();
            if (parametros != null) {
                long idImovel = parametros.getLong("imovel");
                if (parametros.get("atualizado") != null) {
                    long idAtualizado = parametros.getLong("atualizado");
                    carregarContribuinte(idAtualizado);
                }
                carregarImovel(idImovel);
            }
        }
      /*      conectarBanco();
        try {
            new RepositorioComprovante(this.conexao).listarComprovantes();
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
            desconectarBanco(); */
        this.rgFrente = (ImageView) findViewById(R.id.id_rg_frente);
        this.rgVerso = (ImageView) findViewById(R.id.id_rg_verso);
        this.cpf = (ImageView) findViewById(R.id.id_cpf);
        this.escritura = (ImageView) findViewById(R.id.id_escritura);
        this.btSalvar = (Button) findViewById(R.id.id_bt_salvar);
        this.btCancelar = (Button) findViewById(R.id.id_bt_cancelar);

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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSAO_REQUEST);
            }
        }
        handler = new Handler();

        if (comprovante != null) {
            verificarExistenciaDeFoto();
        } else {
            preencherImageViewDefault();
        }


        this.rgFrente.setOnClickListener(new FotoRgFrente());
        this.rgVerso.setOnClickListener(new FotoRgVerso());
        this.cpf.setOnClickListener(new FotoCPF());
        this.escritura.setOnClickListener(new FotoEscritura());
    }

    private void preencherImageViewDefault() {
        this.rgFrente.setImageResource(R.drawable.rg_frente);
        this.rgVerso.setImageResource(R.drawable.rg_verso);
        this.cpf.setImageResource(R.drawable.cpf);
        this.escritura.setImageResource(R.drawable.escritura);
    }


    private Comprovante novaInstancia() {
        return new Comprovante();
    }

    private void verificarExistenciaDeFoto() {

     final Timer timer = new Timer();
     final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (cont < 4) {
                   aux = getPath(cont);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (aux != null && aux.exists()) {
                            foto = aux;
                            carregarImagem = null;
                            if (carregarImagem == null || carregarImagem.getStatus() != AsyncTask.Status.RUNNING) {
                                carregarImagem = new CarregarImagem(activity);
                                carregarImagem.execute();
                            }
                        }
                    }
                });
                if (cont == 3) {
                    timer.cancel();
                }
                cont++;
            }
        };
        timer.schedule(task, 500, 500);
     }

    private File getPath(int i) {
        switch (i) {
            case 0:
                if (StringUtils.isNotBlank(comprovante.getFotoRgFrente())) {
                    chave = i;
                    return new File(ConstantesSistemas.CAMINHO_FOTOS, comprovante.getFotoRgFrente());
                }
                else {
                    this.rgFrente.setImageResource(R.drawable.rg_frente);
                }
                break;
            case 1:
                if (StringUtils.isNotBlank(comprovante.getFotoRgVerso())) {
                    chave = i;
                    return new File(ConstantesSistemas.CAMINHO_FOTOS, comprovante.getFotoRgVerso());
                }
                else {
                    this.rgVerso.setImageResource(R.drawable.rg_verso);
                }
                break;
            case 2:
                if (StringUtils.isNotBlank(comprovante.getFotoCPF())) {
                    chave = i;
                    return new File(ConstantesSistemas.CAMINHO_FOTOS, comprovante.getFotoCPF());
               }
                else {
                    this.cpf.setImageResource(R.drawable.cpf);
                }
                break;
            case 3:
                if (StringUtils.isNotBlank(comprovante.getFotoEscritura())) {
                    chave = i;
                    return new File(ConstantesSistemas.CAMINHO_FOTOS, comprovante.getFotoEscritura());
                 }
                else {
                    this.escritura.setImageResource(R.drawable.escritura);
                }
                break;
                default:
                break;
        }
         return null;
    }

    class FotoRgFrente implements View.OnClickListener {
        public FotoRgFrente() {}

        @Override
        public void onClick(View view) {
            Intent intentRgFrente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgFrente.resolveActivity(getPackageManager()) != null) {
                chave = 0;
                foto = null;
                try {
                    foto = criarArquivoDeImagem(chave);
                }catch (IOException e) {
                    new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar criar arquivo da foto da frente da rg");
                    e.printStackTrace();
                }
                if (foto != null) {
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName()+".provider", foto);
                    intentRgFrente.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intentRgFrente, CAMARA);

                }
            }
        }
    }

    class FotoRgVerso implements View.OnClickListener {
        public FotoRgVerso() {}

        @Override
        public void onClick(View view) {
            Intent intentRgFrente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgFrente.resolveActivity(getPackageManager()) != null) {
                foto = null;
                chave = 1;
                try {
                    foto = criarArquivoDeImagem(chave);
                }catch (IOException e) {
                    new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar criar arquivo da foto do verso da rg");
                    e.printStackTrace();
                }
                if (foto != null) {
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName()+".provider", foto);
                    intentRgFrente.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intentRgFrente, CAMARA);

                }
            }
        }

    }

    class FotoCPF implements View.OnClickListener {
        public FotoCPF() {}

        @Override
        public void onClick(View view) {
            Intent intentRgFrente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgFrente.resolveActivity(getPackageManager()) != null) {
                foto = null;
                chave = 2;
                try {
                    foto = criarArquivoDeImagem(chave);
                }catch (IOException e) {
                    new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar criar arquivo da foto do cpf");
                    e.printStackTrace();
                }
                if (foto != null) {
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName()+".provider", foto);
                    intentRgFrente.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intentRgFrente, CAMARA);

                }
            }
        }
    }

    class FotoEscritura implements View.OnClickListener {
        public FotoEscritura() {}

        @Override
        public void onClick(View view) {
            Intent intentRgFrente = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intentRgFrente.resolveActivity(getPackageManager()) != null) {
                foto = null;
                chave = 3;
                try {
                    foto = criarArquivoDeImagem(chave);
                }catch (IOException e) {
                    new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar criar arquivo da foto da escritura");
                   e.printStackTrace();
                }
                if (foto != null) {
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(),
                            getBaseContext().getApplicationContext().getPackageName()+".provider", foto);
                    intentRgFrente.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intentRgFrente, CAMARA);

                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        switch (chave) {
            case 0:
                this.rgFrente.setImageBitmap(bitmap);
                break;
            case 1:
                this.rgVerso.setImageBitmap(bitmap);
                break;
            case 2:
                this.cpf.setImageBitmap(bitmap);
                break;
            case 3:
                this.escritura.setImageBitmap(bitmap);
                break;
                default:
                    break;
        }
    }

    private void carregarImovel(long id) {
       conectarBanco();
        try {
            this.imovel = new RepositorioImovel(this.conexao).buscarImovelPorId(id);
        } catch (RepositorioException e) {
            new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar buscar objeto imóvel no banco");
            e.printStackTrace();
        }
        desconectarBanco();

        if (this.imovel.getComprovante() != null) {
            conectarBanco();
            try {
                comprovante = new RepositorioComprovante(this.conexao)
            .buscarPorId(this.imovel.getComprovante().getId());
            } catch (RepositorioException e) {
                new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar buscar objeto comprovante no banco");
                e.printStackTrace();
            }
            desconectarBanco();
        }
     }

    private void carregarContribuinte(long id) {
        conectarBanco();
        try {
            atualizado = new RepositorioDadosAtualizadosDoContribuinte(this.conexao).buscar(id);
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

    public void salvar(View view) {
        if (this.comprovante != null) {
            if (StringUtils.isNotBlank(String.valueOf(this.comprovante.getId()))
                    && this.comprovante.getId() != 0){
                seterDados();
                conectarBanco();
                try {
                    new RepositorioComprovante(this.conexao)
                            .atualizar(this.comprovante);
                    Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_LONG).show();
                } catch (RepositorioException e) {
                    new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar atualizar dados");
                    e.printStackTrace();
                }
            }
            desconectarBanco();
        }
        if (this.comprovante == null){
            this.comprovante = novaInstancia();
           seterDados();
            conectarBanco();
            try {
                this.comprovante.setId(new RepositorioComprovante(this.conexao)
                        .salvar(this.comprovante));
                Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_LONG).show();
            } catch (RepositorioException e) {
                new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar salvar dados");
                e.printStackTrace();
            }
            desconectarBanco();
        }
        atualizarImovelComOsComprovantes();

    }

    private void seterDados() {
        this.comprovante.setFotoRgFrente(frenteRg);
        this.comprovante.setFotoRgVerso(versoRG);
        this.comprovante.setFotoCPF(cpfFoto);
        this.comprovante.setFotoEscritura(escrituraFoto);
    }

    private void abrirTelaDeImpressao() {
        Bundle parametros = new Bundle();
        parametros.putLong("id", this.imovel.getId());
        Intent activity = new Intent(this, ListaImoveis.class);
        activity.putExtras(parametros);
        startActivity(activity);
    }

    private void atualizarImovelComOsComprovantes() {
        if (this.comprovante != null &&
                StringUtils.isNotBlank(String.valueOf(this.comprovante.getId()))) {
            conectarBanco();
            this.imovel.setComprovante(novaInstancia());
            this.imovel.getComprovante().setId(this.comprovante.getId());
            try {
                new RepositorioImovel(this.conexao).atualizarComprovante(this.imovel);
            } catch (RepositorioException e) {
                new LogErro().criarArquivoDeLog(e, "Erro na class Acticty Fotos ao tentar atualizar imóvel");
                e.printStackTrace();
            }
            desconectarBanco();
        }
        abrirTelaDeImpressao();
   }

    public void cancelar(View view) {
        abrirTelaDeImpressao();
    }



    private File criarArquivoDeImagem(int chave) throws IOException{

        String nomeDaMidia = this.imovel.getId()+ "_" + CHAVE_REFERENCIA[chave]+ "_"
                + new SimpleDateFormat("dd_MM_yyyy_hh").format(new Date())+".jpg";

        File dirMida = new File(ConstantesSistemas.CAMINHO_FOTOS);
        if (!dirMida.exists()) {
            dirMida.mkdirs();
        }
        File image = new File(dirMida.getPath(), nomeDaMidia);
        if (chave == 0) {
            if (frenteRg == null || frenteRg == ""){
                frenteRg = nomeDaMidia;
            }
        }
        if (chave == 1) {
            if (versoRG == null || versoRG == "") {
                versoRG = nomeDaMidia;
            }
        }
        if (chave == 2) {
            if (cpfFoto == null || cpfFoto == "") {
                cpfFoto = nomeDaMidia;
            }
        }
        if (chave == 3) {
            if (escrituraFoto == null || escrituraFoto == "") {
                escrituraFoto = nomeDaMidia;
            }
        }
      return image;
    }

    class CarregarImagem extends AsyncTask<Void, Void, Bitmap> {
        public Bitmap bit;
        public Activity activity;
        public CarregarImagem() {
        }
        public CarregarImagem(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return  ImageUtil.carregarImagem(foto, LARGURA, ALTURA);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            this.bit = bitmap;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (bit != null) {
                        switch (chave) {
                            case 0:
                                rgFrente.setImageBitmap(bit);
                                ImageUtil.salvarUltimaMida(activity, ImageUtil.MIDIA_FOTO, foto.getAbsolutePath());
                                return;
                            case 1:
                                rgVerso.setImageBitmap(bit);
                                ImageUtil.salvarUltimaMida(activity, ImageUtil.MIDIA_FOTO, foto.getAbsolutePath());
                                return;
                            case 2:
                                cpf.setImageBitmap(bit);
                                ImageUtil.salvarUltimaMida(activity, ImageUtil.MIDIA_FOTO, foto.getAbsolutePath());
                                return;
                            case 3:
                                escritura.setImageBitmap(bit);
                                ImageUtil.salvarUltimaMida(activity, ImageUtil.MIDIA_FOTO, foto.getAbsolutePath());
                                return;
                            default:
                                break;
                        }
                    }
                }
            });
        }
    }

}
