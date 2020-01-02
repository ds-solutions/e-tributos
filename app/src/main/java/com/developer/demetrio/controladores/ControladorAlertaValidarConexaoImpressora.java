package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.excecoes.ControladorException;
import com.developer.demetrio.excecoes.ImpressaoException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.util.Util;

class ControladorAlertaValidarConexaoImpressora/* extends ControladorAlertaBasico implements IControladorAlertaValidarImpressao */{
    private Imovel imovel;

    public ControladorAlertaValidarConexaoImpressora(Imovel imovel) {
    this.imovel = imovel;
    }
  /*  @Override
    public void alertaMensagem() {
        Util.chamaProximo(ControladorBasico.getContext(), this.imovel, true);
    }

    @Override
    public void alertaPerguntaNao() {

    }

    @Override
    public void alertaPerguntaSim() {

    }

    @Override
    public void imprimirContaRateio(Imovel imovel, Context context) throws ControladorException, ImpressaoException {

    }

    @Override
    public boolean imprimirExtratoMacro(Context context, Imovel imovel) throws ControladorException {
        return false;
    }

    @Override
    public boolean verificarExistenciaImpressora(Imovel imovel) {
        return false;
    }

    @Override
    public ControladorImpressao.FlagImpressao verificarImpressaoConta(Imovel imovel, Context context, int i, boolean z) throws ControladorException {
        return null;
    }  */
}
