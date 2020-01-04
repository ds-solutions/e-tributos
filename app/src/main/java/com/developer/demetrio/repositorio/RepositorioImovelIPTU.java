package com.developer.demetrio.repositorio;

import com.developer.demetrio.execoes.RepositorioException;
import com.developer.demetrio.model.Imovel;

import java.util.ArrayList;

public class RepositorioImovelIPTU implements IRepositorioImovelIPTU{

    private static RepositorioImovelIPTU instancia;
    private Imovel objeto;

    public void resetarInstancia() {
        instancia = null;
    }

    public static RepositorioImovelIPTU getInstance() {
        if (instancia == null) {
            instancia = new RepositorioImovelIPTU();
            instancia.objeto = new Imovel();
        }
        return instancia;
    }

    @Override
    public void atualizarIndicadorContinuaImpressao(Integer num, Integer num2) throws RepositorioException {

    }

    @Override
    public void atualizarIndicadorImovelCalculado(Integer num, Integer num2) throws RepositorioException {

    }

    @Override
    public void atualizarIndicadorImovelCondominioNaoCalculado(Integer num) throws RepositorioException {

    }

    @Override
    public void atualizarIndicadorImovelEnviado(String str) throws RepositorioException {

    }

    @Override
    public void atualizarIndicadorRisco(Integer num, Integer num2) throws RepositorioException {

    }

    @Override
    public void atualizarPosicaoImovel(Integer num, Integer num2) throws RepositorioException {

    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisCadastroNaoEnviados() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisCalculados() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisCondominioLidos(String str) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidos() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviados() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisLidosNaoEnviadosNaoCondominio() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarIdsImoveisMicro(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImoveisSequencialNaoNulo() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImoveisSequencialNulo() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelCondominio(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarImovelCondominiosNaoCalculados(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarImovelCondominiosNaoImpressos(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Imovel buscarImovelContaPorHidrometro(String str) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContaPorQuadra(String str) throws RepositorioException {
        return null;
    }

    @Override
    public Imovel buscarImovelContaPosicao(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContas() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Imovel> buscarImovelContasLidos() throws RepositorioException {
        return null;
    }

    @Override
    public Imovel buscarPrimeiroImovel() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> buscarQuadras() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> getIdsNaoLidos() throws RepositorioException {
        return null;
    }

    @Override
    public Integer getQtdImoveis() throws RepositorioException {
        return null;
    }

    @Override
    public void inverterRoteiroImoveis() throws RepositorioException {

    }

    @Override
    public Integer obterIdUltimoImovelMicro(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> obterIdsImoveisMacro() throws RepositorioException {
        return null;
    }

    @Override
    public ArrayList<Integer> obterImovelCondominioCompleto(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterIndicadorPermiteContinuarImpressao(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterIndicadorRisco(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterPosicaoImovel(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterPosicaoImovelCondominioNaoCalculado(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisNaoVisitadosPorQuadra(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisPorQuadra(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastro() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastroAtualizado() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisRoteiroCadastroPendente() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitados() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitadosComAnormalidade() throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImoveisVisitadosPorQuadra(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImovelContratoDemanda(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer obterQuantidadeImovelMicro(Integer num) throws RepositorioException {
        return null;
    }

    @Override
    public Integer pesquisarIdLocalidade() throws RepositorioException {
        return null;
    }

    @Override
    public Integer verificarRateioCondominio(Integer num) throws RepositorioException {
        return null;
    }
}
