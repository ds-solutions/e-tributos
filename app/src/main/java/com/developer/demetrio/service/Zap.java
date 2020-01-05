package com.developer.demetrio.service;

import com.developer.demetrio.model.Imovel;

public class Zap {
    private String para;
    private static final String[] TITULO_TRIBUTOS =  {"IPTU", "ALVARÁ", "TAXA DE BOMBEIROS"};
    private String tituloDoZap;
    private String exercicio;
    private String destino;
    private static final String INICIO = "Saudações ";
    private static final String QUEBRA_LINHA = "\n";
    private static final String EXCLAMACAO = "!";
    private static final String POS_NOME = "        A PREFEITURA MUNICIPAL DE ";
    private static final String POS_MUNICIPIO = ", através da startup e-Tributos, está enviando para sua maior comodidade o *link para download do seu* ";


    private static final String MENSAGEM_ANTES_DO_LINK = "Baixe o seu ";
    private static final String ACESSO_AO_IPTU = " no link abaixo";
    private static final String CONSIDERACOES_FINAIS = "    Para maiores informações acesse no site oficial da sua cidade ou visite o nosso site:"+QUEBRA_LINHA+
            "https://200.98.162.49/CadCity/";
    private String mensagem;

    private Imovel imovel;
    private String nome;

    public Zap() {
    }

    public Zap prepararZap(Imovel imovel, int index) {
        this.imovel = imovel;
        this.exercicio = this.imovel.getTributo().getIptu().getExercicio();

        this.destino = " 81 9977-5594"; //this.imovel.getContribuinte().getNumeroCelular();
        this.tituloDoZap = "*"+TITULO_TRIBUTOS[index]+" "+this.exercicio+"*";
        this.mensagem = INICIO + formatarNome(this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null ?
                this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getNome() : this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNome()) +EXCLAMACAO+
     QUEBRA_LINHA +QUEBRA_LINHA+ corpoDaMensagemDoZap(index, this.imovel.getCadastro().getNumCadastro())
                + contato();
        return this;
    }

    private String corpoDaMensagemDoZap(int index, String cadastro) {
        return "   "+POS_NOME + this.imovel.getEndereco().getCidade() +
                POS_MUNICIPIO + this.tituloDoZap +
                EXCLAMACAO + QUEBRA_LINHA + QUEBRA_LINHA +
                MENSAGEM_ANTES_DO_LINK + tituloDoZap +
                ACESSO_AO_IPTU
                +QUEBRA_LINHA+
                QUEBRA_LINHA+
                "https://www.cgu.gov.br/simulador/manual.pdf" //+"/"+codigo
                +QUEBRA_LINHA + QUEBRA_LINHA +
                "       "+CONSIDERACOES_FINAIS
                +QUEBRA_LINHA + QUEBRA_LINHA + QUEBRA_LINHA;
    }

    private String contato() {
        return  "*CONTATOS:*" + QUEBRA_LINHA +
                "*e-mail:* e.tributos01@gmail.com" + QUEBRA_LINHA+
                "*Fone/WhatsApp:* +55 81 99256-0214";
    }

    private String formatarNome(String nome) {
        int totalCaractere = nome.length();
        char[] caracateres = nome.toCharArray();
        String fistName = "";
        String lastName = "";
        int cont = 0;
        int startLastName = 1;
        for (char l: caracateres) {
            if (l != ' ' && cont == 0) {
                fistName +=l;
            }
            if (cont > 0) {
                startLastName++;
            }
            if (l == ' ') {
                cont++;
            }
        }
        lastName += nome.subSequence(startLastName, totalCaractere);
        return fistName += lastName;
    }

    public void rejetar() {
        new Zap();
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getTituloDoZap() {
        return tituloDoZap;
    }

    public void setTituloDoZap(String tituloDoZap) {
        this.tituloDoZap = tituloDoZap;
    }

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
