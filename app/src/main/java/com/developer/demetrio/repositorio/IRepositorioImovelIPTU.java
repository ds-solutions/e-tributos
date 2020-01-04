package com.developer.demetrio.repositorio;


import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

interface IRepositorioImovelIPTU {
    void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws RepositorioException;

    void atualizarIndicadorImovelCalculado(Integer num, Integer num2) throws RepositorioException;

    void atualizarIndicadorImovelCondominioNaoCalculado(Integer num) throws RepositorioException;

    void atualizarIndicadorImovelEnviado(String str) throws RepositorioException;

    void atualizarIndicadorRisco(Integer num, Integer num2) throws RepositorioException;

    void atualizarPosicaoImovel(Integer num, Integer num2) throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisCadastroNaoEnviados() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisCalculados() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisCondominioLidos(String str) throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisMicro(Integer num) throws RepositorioException;

    ArrayList<Imovel> buscarImoveisSequencialNaoNulo() throws RepositorioException;

    ArrayList<Imovel> buscarImoveisSequencialNulo() throws RepositorioException;

    ArrayList<Imovel> buscarImovelCondominio(Integer num) throws RepositorioException;

    ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer num) throws RepositorioException;

    ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer num) throws RepositorioException;

    Imovel buscarImovelContaPorHidrometro(String str) throws RepositorioException;

    ArrayList<Imovel> buscarImovelContaPorQuadra(String str) throws RepositorioException;

    Imovel buscarImovelContaPosicao(Integer num) throws RepositorioException;

    ArrayList<Imovel> buscarImovelContas() throws RepositorioException;



    ArrayList<Imovel> buscarImovelContasLidos() throws RepositorioException;

    Imovel buscarPrimeiroImovel() throws RepositorioException;

    ArrayList<Integer> buscarQuadras() throws RepositorioException;

    ArrayList<Integer> getIdsNaoLidos() throws RepositorioException;

    Integer getQtdImoveis() throws RepositorioException;

  //  long inserirImovelContaVencimento(ImovelConta imovelConta) throws RepositorioException;

    void inverterRoteiroImoveis() throws RepositorioException;

    Integer obterIdUltimoImovelMicro(Integer num) throws RepositorioException;

    ArrayList<Integer> obterIdsImoveisMacro() throws RepositorioException;

   // ImovelConta obterImovelAreaComum(Integer num) throws RepositorioException;

    ArrayList<Integer> obterImovelCondominioCompleto(Integer num) throws RepositorioException;

    Integer obterIndicadorPermiteContinuarImpressao(Integer num) throws RepositorioException;

    Integer obterIndicadorRisco(Integer num) throws RepositorioException;

    Integer obterPosicaoImovel(Integer num) throws RepositorioException;

    Integer obterPosicaoImovelCondominioNaoCalculado(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisPorQuadra(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisRoteiroCadastro() throws RepositorioException;

    Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws RepositorioException;

    Integer obterQuantidadeImoveisRoteiroCadastroPendente() throws RepositorioException;

    Integer obterQuantidadeImoveisVisitados() throws RepositorioException;

    Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws RepositorioException;

    Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws RepositorioException;

    Integer obterQuantidadeImovelContratoDemanda(Integer num) throws RepositorioException;

    Integer obterQuantidadeImovelMicro(Integer num) throws RepositorioException;

    Integer pesquisarIdLocalidade() throws RepositorioException;

   // MapaIntegracao pesquisarImovelCoordenada() throws RepositorioException;

    Integer verificarRateioCondominio(Integer num) throws RepositorioException;

    List<Imovel> getImoveis();
}
