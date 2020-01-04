package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.ImpressaoException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.controladores.ControladorImpressao.FlagImpressao;

interface IControladorAlertaValidarImpressao {

    void imprimirContaRateio(Imovel imovel, Context context) throws ControladorException, ImpressaoException;

    boolean imprimirExtratoMacro(Context context, Imovel imovel) throws ControladorException;

    boolean verificarExistenciaImpressora(Imovel imovel);

    FlagImpressao verificarImpressaoConta(Imovel imovel, Context context, int i, boolean z) throws ControladorException;
}
