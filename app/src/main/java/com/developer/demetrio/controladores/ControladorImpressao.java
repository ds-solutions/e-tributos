package com.developer.demetrio.controladores;


import android.content.Context;
import android.util.Log;

import com.developer.demetrio.excecoes.ControladorException;
import com.developer.demetrio.excecoes.ImpressaoException;
import com.developer.demetrio.impressao.ImpressaoTributoNovoIPTU;
import com.developer.demetrio.impressao.utils.ZebraUtils;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.util.ConstantesSistemas;

public class ControladorImpressao extends ControladorBasico implements IControladorImpressao {
    private static ControladorImpressao instancia;
    protected final int ID_MSG_ANORMALIDADE_IMPRESSAO_AGUA = 5;
    protected final int ID_MSG_ANORMALIDADE_IMPRESSAO_ESGOTO = 6;
    protected final int ID_MSG_IMOVEL_NAO_CALCULADO = 1;
    protected final int ID_MSG_IMOVEL_NAO_EMITIDO = 2;
    protected final int ID_MSG_VALOR_MAIOR_PERMITIDO = 4;
    protected final int ID_MSG_VALOR_MENOR_PERMITIDO = 3;
    private StringBuilder conta;

    private boolean enviarTributoImpressora(ControladorImpressao controladorImpressao) throws ImpressaoException {
    return ZebraUtils.getInstance(context).imprimir(controladorImpressao.conta);
    }

    public class FlagImpressao {
        public static final int FLAG_IMPRESSAO_CARTA_CONEXAO_ESG = 4;
        public static final int FLAG_IMPRESSAO_CARTA_NOTIFICACAO_AC_DEB_AUTO = 8;
        public static final int FLAG_IMPRESSAO_CARTA_NOTIFICACAO_ALTO_CONSUMO = 22;
        public static final int FLAG_IMPRESSAO_CARTA_NOTIFICACAO_DEBITO = 50;
        public static final int FLAG_IMPRESSAO_CONTA = 2;
        private int flags = 0;

        public boolean isFlagSet(int value) {
            return (this.flags & value) == value;
        }

        public void setFlag(int value) {
            this.flags |= value;
        }

        public void unsetFlag(int value) {
            this.flags &= value ^ -1;
        }

        public boolean isPeloMenosUmaImpressao() {
            return isFlagSet(4) || isFlagSet(8) || isFlagSet(22) || isFlagSet(2) || isFlagSet(50);
        }
    }

    public void resetarInstancia() {
        instancia = null;
    }

    protected ControladorImpressao() {
    }


    public static ControladorImpressao getInstancia() {
        if (instancia == null) {
            instancia = new ControladorImpressao();
        }
        return instancia;
    }

    @Override
    public void imprimirContaRateio(Imovel imovel, Context context) throws ControladorException, ImpressaoException {

    }

    @Override
    public boolean imprimirExtratoMacro(Context context, Imovel imovel) throws ControladorException {
        return false;
    }

    public boolean verificarExistenciaImpressora(Imovel imovel) {
        if (ConstantesSistemas.SIMULADOR) {
            return true;
        }
        return ZebraUtils.getInstance(context).verificaExistenciaImpressoraConfigigurada(context, imovel);
    }

    @Override
    public FlagImpressao verificarImpressaoConta(Imovel imovel, Context context, boolean z) throws ControladorException {
       FlagImpressao flagsImpressao = new FlagImpressao();
       //Ativar esse código quando for trabalhar com banco
      /* Imovel imovelTributado = (Imovel) ControladorBasico.getInstance()
               .pesquisarPorId(imovel.getId(), imovel); */
      if (verificarExistenciaImpressora(imovel)) {
              try {
                 if(imprimirTributo(imovel, ControladorBasico.getContext())) {
                     flagsImpressao.setFlag(2);
                 }
              } catch (ImpressaoException e) {
                  e.printStackTrace();
              }
              //TODO: HAVIA MEXIDO AQUI
      }
      return flagsImpressao;
    }


//parei aqui

    private boolean imprimirTributo(Imovel imovel, Context context) throws ControladorException, ImpressaoException {

//        Parametros sistemaParametros = Parametros.getInstancia()

        if (!ConstantesSistemas.SIMULADOR) {
            this.conta = ImpressaoTributoNovoIPTU.getInstancia(imovel).imprimirIptu();

            Log.v("STRING IMPRESSAO IPTU", this.conta.toString());
            imovel.setIndcEmissaoConta(ConstantesSistemas.SIM);
           if (!instancia.enviarTributoImpressora(this)) {
               System.out.println("retornou true para enviar conta para impressora "+conta.toString());
               return false;
           }

        }
            return true;
        }

        /*   if (SettingsHelper.getPrinterName(context).startsWith("@Zebra@")) {
                this.conta = ImpressaoContaCompesaSeiko.getInstancia(imovel, ConstantesSistema.CODIGO_FEBRABAN_COMPESA).imprimirConta();
            } else {
                this.conta = ImpressaoContaCompesaNovo.getInstancia(imovel).imprimirConta();
            }

        if (!enviarContaImpressora()) {
            return false;
        }
        atualizaDadosImpressaoImovel(imovel);
        return true;
    }

    private boolean enviarContaImpressora() throws ImpressaoException {
        if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
            return SeikoUtils.getInstance(context).imprimir(this.conta);
        }
        return ZebraUtils.getInstance(context).imprimir(this.conta);
    }

    private boolean imprimirNotificacaoDebito(Imovel imovel, Context context) throws ControladorException, ImpressaoException {
        StringBuilder notificacaoDeDebito = null;
        if (SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)) {
            NotificacaoDebitoCompesa notificacaoDebito = NotificacaoDebitoCompesa.getInstancia();
            if (SistemaParametros.getInstancia().getIndicadorLayoutAntigo().equals(ConstantesSistema.SIM)) {
                notificacaoDeDebito = notificacaoDebito.imprimirNotificacaoDebito(imovel);
            } else if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
                notificacaoDeDebito = notificacaoDebito.imprimirNotificacaoDebitoLayoutSeiko(imovel);
            } else {
                notificacaoDeDebito = notificacaoDebito.imprimirNotificacaoDebitoLayoutNovo(imovel);
            }
        }
        if (ConstantesSistema.SIMULADOR) {
            Log.v("STRING DE IMPRESSAO DE NOTIFICAO DE DEBITO", notificacaoDeDebito.toString());
            return true;
        }
        ZebraUtils.getInstance(context).imprimir(notificacaoDeDebito);
        return true;
    }

    private void atualizaDadosImpressaoImovel(ImovelConta imovel) {
        try {
            imovel.setQntVezesImpressaoConta(Integer.valueOf(imovel.getQntVezesImpressaoConta().intValue() + 1));
            imovel.setIndcImovelImpresso(ConstantesSistema.SIM);
            Integer idMacro = imovel.getMatriculaCondominio();
            if (idMacro == null || !getControladorTributo().verificarRateioCondominio(idMacro)) {
                imovel.setIndcImovelEnviado(ConstantesSistema.NAO);
            }
            ControladorBasico.getInstance().atualizar(imovel);
        } catch (Exception e) {
            Log.e("ControladorImpressao:", "Falha ao atualizar dados do imovel: " + imovel.getId());
        }
    }

    private void imprimirCartasNotificacao(Imovel imovel, FlagImpressao flagsImpressao) throws ControladorException, ImpressaoException {
        ArrayList<ContaDebito> arrayListContaDebito = new ArrayList();
        if (ControladorContaDebito.getInstance().buscarContasDebitosPorIdImovel(imovel.getId()) != null && imprimirNotificacaoDebito(imovel, context)) {
            imovel.setIndicadorImpressaoNotificacaoDebitos(ConstantesSistema.SIM);
        }
        if (imovel.getIndcEmissaoCartaObrigConEsgoto() != null && imovel.getIndcEmissaoCartaObrigConEsgoto().equals(ConstantesSistema.SIM) && imprimirCartaNotificacaoEsgoto(imovel, context)) {
            flagsImpressao.setFlag(4);
            imovel.setIndicadorImpressaoCartaConexaoEsgoto(ConstantesSistema.SIM);
        }
        if (imovel.getIndicadorImprimeCarta() != null && imovel.getIndicadorImprimeCarta().equals(ConstantesSistema.SIM)) {
            if (imovel.getCodigoAgencia() == null || imovel.getCodigoAgencia().equals("")) {
                if (imprimirCartaNotificacaoImovel(imovel, context)) {
                    flagsImpressao.setFlag(22);
                    imovel.setIndicadorImpressaoCartaNotificacaoAC(ConstantesSistema.SIM);
                }
            } else if (imprimirCartaNotificacaoImovelDebitoAutomatico(imovel, context)) {
                flagsImpressao.setFlag(8);
                imovel.setIndicadorImpressaoCartaNotificacaoAC(ConstantesSistema.SIM);
            }
        }
        if (flagsImpressao.isFlagSet(8) || flagsImpressao.isFlagSet(22) || flagsImpressao.isFlagSet(4)) {
            ControladorBasico.getInstance().atualizar(imovel);
        }
    }

    public boolean imprimirExtratoMacro(Context context, ImovelConta imovelMacro) throws ControladorException {
        StringBuilder extratoMacro = new StringBuilder();
        if (!verificarExistenciaImpressora(imovelMacro)) {
            return false;
        }
        if (SistemaParametros.getInstancia().getCodigoEmpresaFebraban().equals(ConstantesSistema.CODIGO_FEBRABAN_COMPESA)) {
            if (SistemaParametros.getInstancia().getIndicadorLayoutAntigo().equals(ConstantesSistema.SIM)) {
                extratoMacro = ExtratoMacroCompesa.getInstancia(imovelMacro).obterStringExtratoMacroCompesa();
            } else if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
                extratoMacro = ExtratoMacroCompesa.getInstancia(imovelMacro).obterStringExtratoMacroCompesaLayoutSeiko();
            } else {
                extratoMacro = ExtratoMacroCompesa.getInstancia(imovelMacro).obterStringExtratoMacroCompesaLayoutNovo();
            }
        }
        if (ConstantesSistema.SIMULADOR) {
            Log.v("STRING DE IMPRESSAO DE EXTRATO MACRO", extratoMacro.toString());
            return true;
        }
        try {
            ZebraUtils.getInstance(context).imprimir(extratoMacro);
            return true;
        } catch (ImpressaoException e) {
            Log.e(ConstantesSistema.CATEGORIA, "ControladorImpressao.imprimirExtratoMacro:", e);
            throw new ControladorException(e.getMessage());
        }
    }

    private boolean imprimirCartaNotificacaoEsgoto(ImovelConta imovel, Context context) throws ControladorException, ImpressaoException {
        StringBuilder carta;
        CartaNotificacaoEsgoto cartaNotificacaoEsgoto = CartaNotificacaoEsgoto.getInstancia();
        if (SistemaParametros.getInstancia().getIndicadorLayoutAntigo().equals(ConstantesSistema.SIM)) {
            carta = cartaNotificacaoEsgoto.imprimirCartaNotificacaoEsgoto(imovel);
        } else if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
            carta = cartaNotificacaoEsgoto.imprimirCartaNotificacaoEsgotoLayoutSeiko(imovel);
        } else {
            carta = cartaNotificacaoEsgoto.imprimirCartaNotificacaoEsgotoLayoutNovo(imovel);
        }
        if (!ConstantesSistema.SIMULADOR) {
            return ZebraUtils.getInstance(context).imprimir(carta);
        }
        Log.v("STRING DE IMPRESSAO DE CARTA NOTIFICAO DE ESGOTO", carta.toString());
        return true;
    }

    private boolean imprimirCartaNotificacaoImovelDebitoAutomatico(ImovelConta imovel, Context context) throws ControladorException, ImpressaoException {
        StringBuilder carta;
        CartaNotificacaoImovelDebitoAutomatico cartaDeb = CartaNotificacaoImovelDebitoAutomatico.getInstancia();
        if (SistemaParametros.getInstancia().getIndicadorLayoutAntigo().equals(ConstantesSistema.SIM)) {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMes(imovel);
        } else if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMesLayoutNovoSeiko(imovel, true);
        } else {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMesLayoutNovo(imovel, true);
        }
        if (!ConstantesSistema.SIMULADOR) {
            return ZebraUtils.getInstance(context).imprimir(carta);
        }
        Log.v("STRING DE IMPRESSAO DE CARTA NOTIFICAO DE ESGOTO", carta.toString());
        return true;
    }

    private boolean imprimirCartaNotificacaoImovel(ImovelConta imovel, Context context) throws ControladorException, ImpressaoException {
        StringBuilder carta;
        CartaNotificacaoImovelDebitoAutomatico cartaDeb = CartaNotificacaoImovelDebitoAutomatico.getInstancia();
        if (SistemaParametros.getInstancia().getIndicadorLayoutAntigo().equals(ConstantesSistema.SIM)) {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMes(imovel);
        } else if (SettingsHelper.getPrinterName(context).startsWith("@Seiko@")) {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMesLayoutNovoSeiko(imovel, false);
        } else {
            carta = cartaDeb.imprimirCartaNotificacaoPrimeiroMesLayoutNovo(imovel, false);
        }
        if (!ConstantesSistema.SIMULADOR) {
            return ZebraUtils.getInstance(context).imprimir(carta);
        }
        Log.v("STRING DE IMPRESSAO DE CARTA NOTIFICAO DE ESGOTO", carta.toString());
        return true;
    }

    public void imprimirContaRateio(ImovelConta imovelMicro, Context context) throws ControladorException, ImpressaoException {
        imprimirConta(imovelMicro, context);
    }

    private boolean isAnormalidadeConsumoDA(Integer idImovel) {
        Fachada fachada = Fachada.getInstance();
        Integer estouroDA = new Integer(23);
        boolean retorno = false;
        ConsumoHistorico consumoAgua = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(idImovel, Integer.valueOf(1));
        if (!(consumoAgua == null || consumoAgua.getConsumoAnormalidade() == null)) {
            retorno = estouroDA.equals(consumoAgua.getConsumoAnormalidade().getId());
        }
        ConsumoHistorico consumoEsgoto = fachada.buscarConsumoHistoricoPorImovelIdTipoLigacao(idImovel, Integer.valueOf(2));
        if (retorno || consumoEsgoto == null || consumoEsgoto.getConsumoAnormalidade() == null) {
            return retorno;
        }
        return estouroDA.equals(consumoEsgoto.getConsumoAnormalidade().getId());
    }

    */

}
