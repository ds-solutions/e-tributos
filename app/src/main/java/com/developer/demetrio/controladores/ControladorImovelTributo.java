package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.excecoes.ControladorException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioImovelIPTU;

import java.util.ArrayList;

public class ControladorImovelTributo implements IControladorImovelTributo {
   protected static Context context;
   private static ControladorImovelTributo instance;
   private RepositorioImovelIPTU repositorioImovelIPTU;

    public static IControladorImovelTributo getInstance() {
        if (instance == null) {
            instance = new ControladorImovelTributo();
            instance.repositorioImovelIPTU = RepositorioImovelIPTU.getInstance();
        }
        return instance;
    }

    @Override
    public void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws ControladorException {

    }

    @Override
    public void atualizarIndicadorImovelEnviado(String str) throws ControladorException {

    }

    @Override
    public void atualizarPosicaoImovel(Integer num, Integer num2) throws ControladorException {

    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws ControladorException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImoveisOrdenadosNovos() throws ControladorException {
        return null;
    }

    @Override
    public Imovel buscarImovelContaPorPosicao(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContaPorQuadra(String str) throws ControladorException {
        return null;
    }

    @Override
    public Imovel buscarImovelContaPosicao(Integer num, Boolean bool) throws ControladorException {
        return null;
    }

    @Override
    public Imovel buscarImovelContaSequencial(int i) throws ControladorException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContas() throws ControladorException {
        return null;
    }

    @Override
    public Imovel buscarPrimeiroImovel() throws ControladorException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarQuadras() throws ControladorException {
        return null;
    }

    @Override
    public boolean enviarAoFinalizar(Imovel imovel) {
        return false;
    }

    @Override
    public void enviarDadosCadastraisBackground(Imovel imovel) throws ControladorException {

    }

    @Override
    public void enviarEmBackground(Imovel imovel) throws ControladorException {

    }

    @Override
    public boolean existeImovelImpresso(Integer num) throws ControladorException {
        return false;
    }

    @Override
    public ArrayList<Integer> getIdsNaoLidos() throws ControladorException {
        return null;
    }

    @Override
    public Integer getQtdImoveis() throws ControladorException {
        return null;
    }

    @Override
    public void informarMotivoNaoEntregaDocumento(Integer num, Integer num2) throws ControladorException {

    }

    @Override
    public void inverterRoteiro() throws ControladorException {

    }

    @Override
    public Integer obterIndicadorPermiteContinuarImpressao(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterIndicadorRisco(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterPosicaoImovel(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterPosicaoImovelCondominioNaoCalculado(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisPorQuadra(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastro() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastroPendente() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitados() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImovelContratoDemanda(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImovelMicro(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public double obterValorContaAguaEsgoto(Integer num) throws ControladorException {
        return 0;
    }

    @Override
    public double obterValorContaSemCreditos(Integer num, boolean z) throws ControladorException {
        return 0;
    }

    @Override
    public double obterValorContaSemImposto(Integer num, boolean z) throws ControladorException {
        return 0;
    }

    @Override
    public Integer pesquisarIdLocalidade() throws ControladorException {
        return null;
    }

    @Override
    public void setContext(Context context) {

    }

    @Override
    public boolean verificarBloqueioRecalcularConta(Imovel imovel) throws ControladorException {
        return false;
    }

    @Override
    public boolean verificarRateioCondominio(Integer num) throws ControladorException {
        return false;
    }
}
