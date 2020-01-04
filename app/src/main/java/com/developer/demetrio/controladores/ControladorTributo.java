package com.developer.demetrio.controladores;

import com.developer.demetrio.execoes.ControladorException;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.repositorio.RepositorioImovelIPTU;

import java.util.ArrayList;

public class ControladorTributo extends ControladorBasico implements IControladorTributo {

    private RepositorioImovelIPTU repositorioImovelIPTU;

    private static ControladorTributo instance;
    public static ControladorTributo getInstance() {
        if (instance == null) {
            instance = new ControladorTributo();
            instance.repositorioImovelIPTU = RepositorioImovelIPTU.getInstance();
        }
        return instance;
    }

    @Override
    public void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws ControladorException {

    }



    @Override
    public void atualizarPosicaoImovel(Integer num, Integer num2) throws ControladorException {

    }
    @Override
    public ArrayList<Integer> buscarIdsImoveisLidosNaoImpressos() throws ControladorException {
        return null;
    }

     @Override
    public Imovel buscarImovelContaPorPosicao(Integer num) throws ControladorException {
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
    public void enviarEmBackground(Imovel imovel) throws ControladorException {

    }

    @Override
    public boolean existeImovelImpresso(Integer num) throws ControladorException {
        return false;
    }

    @Override
    public String formatarInscricao(String str) {
        return null;
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
    public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisPorQuadra(Integer num) throws ControladorException {
        return null;
    }


    @Override
    public Integer obterQuantidadeImoveisVisitados() throws ControladorException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws ControladorException {
        return null;
    }


}
