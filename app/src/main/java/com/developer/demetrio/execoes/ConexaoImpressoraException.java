package com.developer.demetrio.execoes;

public class ConexaoImpressoraException extends ImpressaoException {
    private static final long serialVersionUID = 1;

    public ConexaoImpressoraException() {
        super("Erro de conexão.");
    }
}
