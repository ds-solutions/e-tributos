package com.developer.demetrio.fachada;

import android.content.Context;
import com.developer.demetrio.beans.Parametros;
import com.developer.demetrio.controladores.ControladorBasico;
import com.developer.demetrio.controladores.ControladorImovelTributo;
import com.developer.demetrio.controladores.ControladorImpressao;
import com.developer.demetrio.controladores.ControladorParamentros;
import com.developer.demetrio.controladores.IControladorImovelTributo;
import com.developer.demetrio.controladores.IControladorImpressao;
import com.developer.demetrio.controladores.IControladorParametros;

import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.FachadaException;
import com.developer.demetrio.model.Imovel;

public class Fachada {
    private static Fachada instance;
    private Context context;
    private IControladorImovelTributo controladorImovelTributo;
    private IControladorImpressao controladorImpressao;
    private IControladorParametros controladorParametros;
    private Imovel imovel;

    private IControladorImovelTributo getControladorImovelTributo() {
        if (this.controladorImovelTributo == null) {
            this.controladorImovelTributo = ControladorImovelTributo.getInstance();
        }
        return this.controladorImovelTributo;
    }

    private IControladorImpressao getControladorImpressao() {
        if (this.controladorImpressao == null) {
            this.controladorImpressao = ControladorImpressao.getInstancia();
        }
        return this.controladorImpressao;
    }

    public static Fachada getInstance() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    public static void setContext(Context context) {
        ControladorBasico.getInstance().setContext(context);
    }

    public Parametros buscarParametros() throws FachadaException, ControladorException {
        Parametros parametros = this.getControladorParametros().buscarParametro();
        return parametros;
    }

    public void enviarEmBackground(Imovel imovel) throws FachadaException, ControladorException {
        getControladorImovelTributo().enviarEmBackground(imovel);
        return;
    }

    public IControladorParametros getControladorParametros() throws FachadaException {
        if (this.controladorParametros == null) {
            this.controladorParametros = ControladorParamentros.getInstance();
        }
        return this.controladorParametros;
    }

    public ControladorImpressao.FlagImpressao verificarImpressaoConta(Imovel imovel, Context context) throws FachadaException, ControladorException {
        ControladorImpressao.FlagImpressao flagImpressao = getControladorImpressao().verificarImpressaoConta(imovel, context, true);
        return flagImpressao;
    }
}

