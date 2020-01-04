package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;

public interface IControladorImovelTributo {
    void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws ControladorException;

    void atualizarIndicadorImovelEnviado(String str) throws ControladorException;

    void atualizarPosicaoImovel(Integer num, Integer num2) throws ControladorException;

    ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws ControladorException;

    ArrayList<Imovel> buscarImoveisOrdenadosNovos() throws ControladorException;

    Imovel buscarImovelContaPorPosicao(Integer num) throws ControladorException;

    ArrayList<Imovel> buscarImovelContaPorQuadra(String str) throws ControladorException;

    Imovel buscarImovelContaPosicao(Integer num, Boolean bool) throws ControladorException;

    Imovel buscarImovelContaSequencial(int i) throws ControladorException;

    ArrayList<Imovel> buscarImovelContas() throws ControladorException;

    Imovel buscarPrimeiroImovel() throws ControladorException;

    ArrayList<Integer> buscarQuadras() throws ControladorException;

    boolean enviarAoFinalizar(Imovel imovel);

    void enviarDadosCadastraisBackground(Imovel imovel) throws ControladorException;

    void enviarEmBackground(Imovel imovel) throws ControladorException;

    boolean existeImovelImpresso(Integer num) throws ControladorException;

    ArrayList<Integer> getIdsNaoLidos() throws ControladorException;

    Integer getQtdImoveis() throws ControladorException;

    void informarMotivoNaoEntregaDocumento(Integer num, Integer num2) throws ControladorException;

    void inverterRoteiro() throws ControladorException;

    Integer obterIndicadorPermiteContinuarImpressao(Integer num) throws ControladorException;

    Integer obterIndicadorRisco(Integer num) throws ControladorException;

    Integer obterPosicaoImovel(Integer num) throws ControladorException;

    Integer obterPosicaoImovelCondominioNaoCalculado(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisPorQuadra(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisRoteiroCadastro() throws ControladorException;

    Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws ControladorException;

    Integer obterQuantidadeImoveisRoteiroCadastroPendente() throws ControladorException;

    Integer obterQuantidadeImoveisVisitados() throws ControladorException;

    Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws ControladorException;

    Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws ControladorException;

    Integer obterQuantidadeImovelContratoDemanda(Integer num) throws ControladorException;

    Integer obterQuantidadeImovelMicro(Integer num) throws ControladorException;

    double obterValorContaAguaEsgoto(Integer num) throws ControladorException;

    double obterValorContaSemCreditos(Integer num, boolean z) throws ControladorException;

    double obterValorContaSemImposto(Integer num, boolean z) throws ControladorException;

    Integer pesquisarIdLocalidade() throws ControladorException;

    void setContext(Context context);

    boolean verificarBloqueioRecalcularConta(Imovel imovel) throws ControladorException;

    boolean verificarRateioCondominio(Integer num) throws ControladorException;

}
