package com.developer.demetrio.repositorio;


import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

interface IRepositorioImovel {
    List<Imovel> buscarTodosEnviadosPorEmail() throws RepositorioException;

    List<Imovel> buscarTodosEnviadosPorWhatsApp() throws RepositorioException;

    List<Imovel> buscarTodosImoveisImpressos() throws RepositorioException;

    List<Imovel> buscarTodosImoveisNaoImpressos() throws RepositorioException;

    List<Imovel> buscarTodosImoveisPorInscricao(String[] parametros) throws RepositorioException;

    List<Imovel> buscarTodosPorLogradouro(String[] parametros) throws RepositorioException;

    List<Imovel> buscarImoveisNaoEntregues() throws RepositorioException;

    List<Imovel> buscarPorSetorQuadra(String setor, String quadra) throws RepositorioException;

    long inserir(Imovel imovel) throws RepositorioException;

    long atualizarIndicadorDeEnvio(long id, Imovel i) throws RepositorioException;

    Imovel buscarImovelPorId(long id) throws RepositorioException;

    Integer getQtdImoveis() throws RepositorioException;

    List<Imovel> getImoveis() throws RepositorioException;

    long primeiraPosicaoNaoEmitida() throws RepositorioException;

    boolean rotaFinalizada();

    List<Imovel> buscarDaMatricula(String[] parametros) throws RepositorioException;

    long primeiraPosicao() throws RepositorioException;

    long totalDeImoveisVisitados() throws RepositorioException;

    long totalEnviadosPorEmail() throws RepositorioException;

    long totalEnviadosPorWhatsApp() throws RepositorioException;

    long totalImpresso() throws RepositorioException;

    long totalImoveisAVisitar() throws RepositorioException;

    long totalDeCadastroAlterados() throws RepositorioException;


    long totalDeImoveisDemolidos() throws RepositorioException;

    long totalDeImoveisNaoLocalizados() throws RepositorioException;

    long totalNaoEntreguesPorSerTerreno() throws RepositorioException;

    long naoEntreguesPorRecusarReceber() throws RepositorioException;

    long totalDeTributosNaoEntregues() throws RepositorioException;

    void atualizarMotivoDaNaoEntrega(Imovel imovel) throws RepositorioException;
}
