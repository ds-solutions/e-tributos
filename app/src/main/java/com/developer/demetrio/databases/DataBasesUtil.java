package com.developer.demetrio.databases;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.developer.demetrio.databases.constantes._DataBase;
import com.developer.demetrio.etributos.R;
import com.developer.demetrio.util.ConstantesSistemas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DataBasesUtil {
    private static final int PERMISSION_REQUEST = 1;
    private Context context;
    private Activity activity;

    public void exportDataBase(Context context, Activity activity, String nomeDatabase, String databaseExport, boolean permissao) {
        this.context = context;
        this.activity = activity;
        File folderBD = new File(_DataBase.CAMINHO_DA_BASE_DE_DADOS);
        File folderPublic = null;
        String nomeBD = null;
        File dir = null;

        solicitarPermissoes();
           folderPublic = new File(ConstantesSistemas.CAMINHO_SDCARD + "banco/");
            nomeBD = nomeDatabase + databaseExport;

            dir = new File(folderPublic.toString(), nomeBD);
            if (!dir.exists()) {

                Toast.makeText(context, "Criando caminho para exportação do banco", Toast.LENGTH_LONG).show();

                    if (dir.getParentFile().mkdirs()) {
                        System.out.println(dir.toString());
                        Toast.makeText(context, "Criando caminho para exportação do banco", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Não foi possível criar o caminho para exportar o banco!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Você não tem permissão para criar arquivos!", Toast.LENGTH_LONG).show();
                }


/*
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(dir);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/

        if (dir.exists()) {
            System.out.println("Entoru no na condição de que existe o diretório "+dir);
            File data = Environment.getDataDirectory();

            File currentPath = new File("/data/data/"+context.getResources().getString(R.string.provider_authority).toString()+"/databases/"+nomeDatabase);
            try {
                FileChannel source = new FileInputStream(currentPath).getChannel();
                FileChannel destino = new FileOutputStream(dir).getChannel();
                destino.transferFrom(source, 0, source.size());
                source.close();
                destino.close();
                Toast.makeText(context, "Banco exportado com sucesso!", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context, "Erro ao tentar exportar banco de dados!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        /*
        boolean retorno = false;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String data = format.format(new Date());
        Calendar cal = Calendar.getInstance();
        File sd = Environment.getExternalStorageDirectory();
        File baseDeDados = Environment.getDataDirectory();
        FileChannel source = null;
        FileChannel destino = null;
        String backupDBPath = null;
        String caminhoDoBanco = "/data/"+packagePath+"/databases/"+nomeDatabase+databaseExport;
        System.out.println("ANTES DA CONDIÇÃO A VARIAVEL SD = "+sd);
        System.out.println("ANTES DA CONDIÇÃO A VARIAVEL baseDeDados = "+baseDeDados.toString());
        System.out.println("ANTES DA CONDIÇÃO A VARIAVEL caminhoDoBanco = "+caminhoDoBanco.toString());
        if (permissao) {
            backupDBPath = nomeDatabase+databaseExport;
            File dir = new File(ConstantesSistemas.PATH_DB+"/");
            File bd = new File(dir, backupDBPath);
            FileChannel sd = new InputStream()
            if (bd.exists()) {

            }

            System.out.println("DENTRO DA CONDIÇÃO VERDADEIRA A VARIAVEL backupDBPath = "+backupDBPath.toString());

            Toast.makeText(context, "Banco exportado para o caminho \n"
                    + backupDBPath.toString(), Toast.LENGTH_LONG).show();
            System.out.println("Banco exportado para o caminho \n"
                    + backupDBPath.toString());
            return true;
        } else {
            backupDBPath = nomeDatabase+"_"+format.format(cal.getTime())+databaseExport;
            System.out.println("DENTRO DA CONDIÇÃO FALSA A VARIAVEL backupDBPath = " +backupDBPath.toString());
            File currentDB = new File(baseDeDados, caminhoDoBanco);
            System.out.println("DENTRO DA CONDIÇÃO FALSO A VARIAVEL currentDB = "+currentDB.toString());
            File backupDB = new File(sd, backupDBPath);
            System.out.println("DENTRO DA CONDIÇÃO FALSO A VARIAVEL backupDB = "+backupDB.toString());
            try {
                source = new FileInputStream(currentDB).getChannel();
                destino = new FileOutputStream(backupDB).getChannel();
                destino.transferFrom(source, 0, source.size());
                System.out.println("DENTRO DA CONDIÇÃO FALSO NO TRY VARIAVEL source = "+source.toString());
                System.out.println("DENTRO DA CONDIÇÃO FALSO NO TRY VARIAVEL destino = "+destino.toString());
                source.close();
                destino.close();

                Toast.makeText(context, "Banco exportado para o caminho \n"
                        + backupDB.toString(), Toast.LENGTH_LONG).show();
                return true;
            } catch (IOException e) {
                Toast.makeText(context, "Erro ao tentar exportar banco \n"
                        + e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }*/
   //return retorno;
    }

    private void solicitarPermissoes() {
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
            }
        }
    }
}
