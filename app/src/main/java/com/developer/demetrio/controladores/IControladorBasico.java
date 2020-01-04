package com.developer.demetrio.controladores;

import android.content.Context;

import com.developer.demetrio.beans.ObjetoBasico;
import com.developer.demetrio.execoes.ControladorException;

import java.util.ArrayList;

public interface IControladorBasico {

    void apagarBanco();

    void atualizar(ObjetoBasico objetoBasico) throws ControladorException;

    void carregaLinhaParaBD(String str) throws ControladorException;

    ControladorConta getControladorConta();

    //  ControladorImovel getControladorImovel();

    ControladorImovelTributo getControladorImovelConta();

   // ControladorSistemasParametros getControladorSistemaParametros();

    long inserir(ObjetoBasico objetoBasico) throws ControladorException;

    <T extends ObjetoBasico> ArrayList<T> pesquisar(T t) throws ControladorException;

    <T extends ObjetoBasico> T pesquisarPorId(Integer num, T t) throws ControladorException;

    void remover(ObjetoBasico objetoBasico) throws ControladorException;

    void setContext(Context context);

    boolean verificarExistenciaBancoDeDados();

}
