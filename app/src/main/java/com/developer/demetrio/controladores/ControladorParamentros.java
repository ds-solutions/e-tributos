package com.developer.demetrio.controladores;

import android.content.Context;
import android.util.Log;

import com.developer.demetrio.beans.Parametros;
import com.developer.demetrio.excecoes.ControladorException;
import com.developer.demetrio.excecoes.RepositorioException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioParametros;
import com.developer.demetrio.tributos.R;
import com.developer.demetrio.util.ConstantesSistemas;

public class ControladorParamentros extends ControladorBasico implements IControladorParametros {
   protected static Context context;
   private static ControladorParamentros instance;
   private RepositorioParametros repositorioParametros;

   public void resetarInstancia() {
       instance =  null;
   }

   private ControladorParamentros() {

   }

   public static ControladorParamentros getInstance() {
       if (instance == null) {
           instance = new ControladorParamentros();
       }
       return instance;
   }


    @Override
    public void atualizarArquivoCarregadoBD() throws ControladorException {

    }

    @Override
    public void atualizarDadosImovelContratoDemanda(Integer num) throws ControladorException {

    }

    @Override
    public void atualizarDadosImovel(Imovel imovel) throws ControladorException {

    }

    @Override
    public void atualizarQntImoveis() throws ControladorException {

    }

    @Override
    public void atualizarSistemaParametros(Parametros parametros) throws ControladorException {

    }

    @Override
    public Parametros buscarParametro() throws ControladorException {
        try {
            return this.repositorioParametros.buscarParametros();
        } catch (RepositorioException ex) {
            ex.printStackTrace();
            Log.e(ConstantesSistemas.CATEGORIA, ex.getMessage());
            throw new ControladorException(context.getResources().getString(R.string.db_erro));
        }
    }

    @Override
    public boolean validaSenhaAdm(String str) throws ControladorException {
        return false;
    }

    @Override
    public boolean validaSenhaApagar(String str) throws ControladorException {
        return false;
    }

    @Override
    public boolean validaSenhaLayoutConta(String str) throws ControladorException {
        return false;
    }
}
