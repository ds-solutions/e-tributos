package com.developer.demetrio.controladores;

import android.content.Context;


import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;
import java.util.List;

public interface IControladorTributo {

    void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws  ControladorException;

    void atualizarPosicaoImovel(Integer num, Integer num2) throws ControladorException;

    ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() throws ControladorException;

    Imovel buscarImovelPorId(long num) throws ControladorException, RepositorioException;

    Imovel buscarPrimeiroImovel() throws ControladorException;

    ArrayList<Integer> buscarQuadras() throws ControladorException;

    void enviarEmBackground(Imovel imovel) throws ControladorException;

    boolean existeImovelImpresso(Integer num) throws ControladorException;

    String formatarInscricao(String str);

    ArrayList<Integer> getIdsNaoLidos() throws ControladorException;

    Integer getQtdImoveis() throws ControladorException;

    void inverterRoteiro() throws ControladorException;

    Integer obterIndicadorPermiteContinuarImpressao(Integer num) throws ControladorException;

    Integer obterIndicadorRisco(Integer num) throws ControladorException;

    Integer obterPosicaoImovel(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisPorQuadra(Integer num) throws ControladorException;

    Integer obterQuantidadeImoveisVisitados() throws ControladorException;

    Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws ControladorException;



    void setContext(Context context);

}
