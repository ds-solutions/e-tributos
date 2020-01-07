package com.developer.demetrio.repositorio;


import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

interface IRepositorioImovel {
    void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws RepositorioException;

    long inserir(Imovel imovel) throws RepositorioException;
    public long atualizarIndicadorEmissao(long id, int indicador) throws RepositorioException;

    long atualizarIndicadorEnvioEmail(long id, int indicador) throws RepositorioException;

    long atualizarIndicadorEnvioWhatsAap(long id, int indicador) throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisCadastroNaoEnviados() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException;

    ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException;

    ArrayList<Imovel> buscarImovelContaPorQuadra(String setor, String quadra) throws RepositorioException;

    Imovel buscarImovelContaPosicao(Integer num) throws RepositorioException;

    ArrayList<Imovel> buscarImovelContas() throws RepositorioException;

    ArrayList<Imovel> buscarImovelContasLidos() throws RepositorioException;

    ArrayList<Integer> buscarQuadras() throws RepositorioException;

    Integer getQtdImoveis() throws RepositorioException;

    void inverterRoteiroImoveis() throws RepositorioException;

    Integer obterQuantidadeDeRegistro() throws RepositorioException;

    Integer obterPosicaoImovel(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisPorQuadra(Integer num) throws RepositorioException;

    Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws RepositorioException;

    List<Imovel> getImoveis();
}
