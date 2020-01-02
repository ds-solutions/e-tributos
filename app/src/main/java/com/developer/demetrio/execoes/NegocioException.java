package com.developer.demetrio.execoes;

public class NegocioException extends Exception {
    private static final long serialVersionUID = 1;

    public NegocioException(String mensagem) {
        super(mensagem);
    }
}
