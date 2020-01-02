package com.developer.demetrio.iptu;

import com.developer.demetrio.model.Imovel;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

public class IPTU implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long id;
    private String codigoDaDivida;
    private String codigoDeBaixa;
    private String mensagem;
    private String mensagem1;
    private String mensagem2;
    private String exercicio;
    private String vencimento;
    private String valorTotal;
    private List<DescricaoDaDivida> listDescricao;
    private String somaDoValor;
    private String somaDoDesconto;
    private String somaIsencao;
    private String digitosDoCodigoDeBarras;
    private String campo1CodigoDeBarras;
    private String campo2CodigoDeBarras;
    private String campo3CodigoDeBarras;
    private String campo4CodigoDeBarras;

    public IPTU() {
    }

    public IPTU(Long id, String codigoDaDivida, String codigoDeBaixa,
                String mensagem, String mensagem1, String mensagem2,
                String exercicio, String vencimento, String valorTotal,
                List<DescricaoDaDivida> listDescricao, String somaDoValor,
                String somaDoDesconto, String somaIsencao) {
        this.id = id;
        this.codigoDaDivida = codigoDaDivida;
        this.codigoDeBaixa = codigoDeBaixa;
        this.mensagem = mensagem;
        this.mensagem1 = mensagem1;
        this.mensagem2 = mensagem2;
        this.exercicio = exercicio;
        this.vencimento = vencimento;
        this.valorTotal = valorTotal;
        this.listDescricao = listDescricao;
        this.somaDoValor = somaDoValor;
        this.somaDoDesconto = somaDoDesconto;
        this.somaIsencao = somaIsencao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoDaDivida() {
        return codigoDaDivida;
    }

    public void setCodigoDaDivida(String codigoDaDivida) {
        this.codigoDaDivida = codigoDaDivida;
    }

    public String getCodigoDeBaixa() {
        return codigoDeBaixa;
    }

    public void setCodigoDeBaixa(String codigoDeBaixa) {
        this.codigoDeBaixa = codigoDeBaixa;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem1() {
        return mensagem1;
    }

    public void setMensagem1(String mensagem1) {
        this.mensagem1 = mensagem1;
    }

    public String getMensagem2() {
        return mensagem2;
    }

    public void setMensagem2(String mensagem2) {
        this.mensagem2 = mensagem2;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public List<DescricaoDaDivida> getListDescricao() {
        return listDescricao;
    }

    public void setListDescricao(List<DescricaoDaDivida> listDescricao) {
        this.listDescricao = listDescricao;
    }

    public String getSomaDoValor() {
        return somaDoValor;
    }

    public void setSomaDoValor(String somaDoValor) {
        this.somaDoValor = somaDoValor;
    }

    public String getSomaDoDesconto() {
        return somaDoDesconto;
    }

    public void setSomaDoDesconto(String somaDoDesconto) {
        this.somaDoDesconto = somaDoDesconto;
    }

    public String getSomaIsencao() {
        return somaIsencao;
    }

    public void setSomaIsencao(String somaIsencao) {
        this.somaIsencao = somaIsencao;
    }

    public String getDigitosDoCodigoDeBarras() {
        return digitosDoCodigoDeBarras;
    }

    public void setDigitosDoCodigoDeBarras(String digitosDoCodigoDeBarras) {
        this.digitosDoCodigoDeBarras = digitosDoCodigoDeBarras;
    }

    public String getCampo1CodigoDeBarras() {
        return campo1CodigoDeBarras;
    }

    public void setCampo1CodigoDeBarras(String campo1CodigoDeBarras) {
        this.campo1CodigoDeBarras = campo1CodigoDeBarras;
    }

    public String getCampo2CodigoDeBarras() {
        return campo2CodigoDeBarras;
    }

    public void setCampo2CodigoDeBarras(String campo2CodigoDeBarras) {
        this.campo2CodigoDeBarras = campo2CodigoDeBarras;
    }

    public String getCampo3CodigoDeBarras() {
        return campo3CodigoDeBarras;
    }

    public void setCampo3CodigoDeBarras(String campo3CodigoDeBarras) {
        this.campo3CodigoDeBarras = campo3CodigoDeBarras;
    }

    public String getCampo4CodigoDeBarras() {
        return campo4CodigoDeBarras;
    }

    public void setCampo4CodigoDeBarras(String campo4CodigoDeBarras) {
        this.campo4CodigoDeBarras = campo4CodigoDeBarras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IPTU iptu = (IPTU) o;

        return id.equals(iptu.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
