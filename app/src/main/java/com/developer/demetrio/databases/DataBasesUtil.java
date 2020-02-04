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

    public boolean exportDataBase(Context context, Activity activity, String nomeDatabase, String databaseExport) {
        this.context = context;
        this.activity = activity;

        solicitarPermissoes();

        File folderPublic = folderPublic = new File(ConstantesSistemas.CAMINHO_SDCARD + "banco/");
        String nomeBD = nomeDatabase + databaseExport;
        File dir = new File(folderPublic.toString(), nomeBD);

       if (!dir.exists()) {
            Toast.makeText(context, "Criando caminho para exportação do banco", Toast.LENGTH_LONG).show();

            if (dir.getParentFile().mkdirs()) {
                System.out.println(dir.toString());
                Toast.makeText(context, "Criando caminho para exportação do banco", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Não foi possível criar o caminho para exportar o banco!", Toast.LENGTH_LONG).show();
            }
       }

       if (dir.exists()) {
            File data = Environment.getDataDirectory();

            File currentPath = new File("/data/data/"+context.getResources().getString(R.string.provider_authority).toString()+"/databases/"+nomeDatabase);
            try {
                FileChannel source = new FileInputStream(currentPath).getChannel();
                FileChannel destino = new FileOutputStream(dir).getChannel();
                destino.transferFrom(source, 0, source.size());
                source.close();
                destino.close();
                return true;

            } catch (IOException e) {
                Toast.makeText(context, "Erro ao tentar exportar banco de dados!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    return false;
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
