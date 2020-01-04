package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.beans.ObjetoBasico;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;

public class ControladorBasico implements IControladorBasico {
    protected static Context context;
    private static ControladorBasico instance;
    private ControladorImpressao controladorImpressao;
    private ControladorTributo controladorTributo;
    private ControladorAlertaValidarConexaoImpressora controladorAlertaValidarConexaoImpressora;


    public static Context getContext() {
        return context;
    }

    public void resetarInstancia() {
        instance = null;
    }

    protected ControladorBasico() {

    }
    public static ControladorBasico getInstance() {
        if (instance == null) {
            instance = new ControladorBasico();
        }
        return instance;
    }


    @Override
    public void apagarBanco() {

    }

    @Override
    public void atualizar(ObjetoBasico objetoBasico) throws ControladorException {

    }

    @Override
    public void carregaLinhaParaBD(String str) throws ControladorException {

    }

    @Override
    public ControladorConta getControladorConta() {
        return null;
    }

    @Override
    public ControladorImovelTributo getControladorImovelConta() {
        return null;
    }

    @Override
    public long inserir(ObjetoBasico objetoBasico) throws ControladorException {
        return 0;
    }

    @Override
    public <T extends ObjetoBasico> ArrayList<T> pesquisar(T t) throws ControladorException {
        return null;
    }

    @Override
    public <T extends ObjetoBasico> T pesquisarPorId(Integer num, T t) throws ControladorException {
        return null;
    }

    @Override
    public void remover(ObjetoBasico objetoBasico) throws ControladorException {

    }

    public void setContext(Context ctx) {
        context = ctx;
    }

    @Override
    public boolean verificarExistenciaBancoDeDados() {
        return false;
    }

    public ControladorImpressao getControladorImpressao() {
        if (this.controladorImpressao == null) {
            this.controladorImpressao = ControladorImpressao.getInstancia();
        }
        return this.controladorImpressao;
    }

    public ControladorTributo getControladorTributo() {
        if (this.controladorTributo == null) {
            this.controladorTributo = ControladorTributo.getInstance();
        }
        return this.controladorTributo;
    }


    protected Object pesquisarPorId(Long id, Imovel imovel) {
        return  null;
    }

    public ControladorAlertaValidarConexaoImpressora getControladorAlertaValidarConexaoImpressora(Imovel imovel) {
        this.controladorAlertaValidarConexaoImpressora = new ControladorAlertaValidarConexaoImpressora(imovel);
        return this.controladorAlertaValidarConexaoImpressora;
    }

}
