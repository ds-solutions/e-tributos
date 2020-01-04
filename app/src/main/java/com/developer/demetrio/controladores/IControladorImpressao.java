package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.controladores.ControladorImpressao.FlagImpressao;
import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.ImpressaoException;
import com.developer.demetrio.iptu.IPTU;
import com.developer.demetrio.model.Imovel;

public interface IControladorImpressao {
    void imprimirContaRateio(Imovel imovel, Context context) throws ControladorException, ImpressaoException;

    boolean imprimirExtratoMacro(Context context, Imovel imovel) throws ControladorException;


    boolean verificarExistenciaImpressora(Imovel imovel);

    FlagImpressao verificarImpressaoConta(Imovel imovel, Context context, boolean z) throws ControladorException;
}
