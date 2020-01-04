package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.beans.Parametros;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

public interface IControladorParametros {

    void atualizarArquivoCarregadoBD() throws ControladorException, ControladorException;

    void atualizarDadosImovelContratoDemanda(Integer num) throws ControladorException;

    void atualizarDadosImovel(Imovel imovel) throws ControladorException;

    void atualizarQntImoveis() throws ControladorException;

    void atualizarSistemaParametros(Parametros parametros) throws ControladorException;

    Parametros buscarParametro() throws ControladorException, RepositorioException;

    void setContext(Context context);

    boolean validaSenhaAdm(String str) throws ControladorException;

    boolean validaSenhaApagar(String str) throws ControladorException;

    boolean validaSenhaLayoutConta(String str) throws ControladorException;
}
