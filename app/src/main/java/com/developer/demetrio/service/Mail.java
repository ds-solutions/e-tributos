package com.developer.demetrio.service;

import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;

public class Mail {
    private String para;
    private static final String[] TITULO_TRIBUTOS =  {"IPTU", "ALVARÁ", "TAXA DE BOMBEIROS"};
    private String tituloDoEmail;
    private String exercicio;
    private String[] emails;
    private static final String INICIO = "Saudações ";
    private static final String QUEBRA_LINHA = "\n";
    private static final String EXCLAMACAO = "!";
    private static final String POS_NOME = "        A PREFEITURA MUNICIPAL DE ";
    private static final String POS_MUNICIPIO = ", através da startup e-Tributos, está enviando para sua maior comodidade o link para download do seu ";
    private static final String MENSAGEM_ANTES_DO_LINK = "Baixe o seu ";
    private static final String INDICACAO_DO_LINK = " no link abaixo ";

    private static final String CONSIDERACOES_FINAIS = "        Para maiores informações acesse no site oficial da sua cidade ou visite o nosso site:"+
            "https://200.98.162.49/CadCity/";
    private String mensagem;
    private Contribuinte contribuinte;
    private Imovel imovel;
    private String nome;
    private StringBuilder mensageBuilder;

    public Mail() {
    }

    public Mail prepararEmail(Imovel imovel, int index) {
       this.imovel = imovel;
        this.exercicio = this.imovel.getTributo().getIptu().getExercicio();
        this.emails = new String[]{"e.tributos01@gmail.com"}; //  this.para = this.contribuinte.getEmail();
        this.tituloDoEmail = this.imovel.getEndereco().getCidade() +" - "+TITULO_TRIBUTOS[index]+" "+this.exercicio;
        this.mensagem = INICIO + pegarPrimeiroNome(this.imovel.getContribuinte().getAtualizacaoDoContribuinte() != null ?
                this.imovel.getContribuinte().getAtualizacaoDoContribuinte().getNome() : this.imovel.getContribuinte().getDadosCadastradosDoContribuinte().getNome()) +EXCLAMACAO+
            corpoDoEmail(index, this.imovel.getCadastro().getNumCadastro()) +
                "       "+CONSIDERACOES_FINAIS+
                rodaPe();
        return this;
    }

    private String  rodaPe() {
       return  QUEBRA_LINHA + QUEBRA_LINHA + QUEBRA_LINHA+
               "CONTATOS:" + QUEBRA_LINHA +
               "e-mail: e.tributos01@gmail.com" + QUEBRA_LINHA+
               "Fone/WhatsApp: +55 81 99256-0214";
    }

    private String corpoDoEmail(int index, String codigo) {
        return  QUEBRA_LINHA +QUEBRA_LINHA+
                "   "+POS_NOME + this.imovel.getEndereco().getCidade() +
                POS_MUNICIPIO + TITULO_TRIBUTOS[index]+" "+this.exercicio +
                EXCLAMACAO +
                QUEBRA_LINHA +QUEBRA_LINHA+
                MENSAGEM_ANTES_DO_LINK + TITULO_TRIBUTOS[index]+" "+this.exercicio + INDICACAO_DO_LINK
                + QUEBRA_LINHA+ QUEBRA_LINHA
                +"https://www.cgu.gov.br/simulador/manual.pdf" //+"/"+codigo
                + QUEBRA_LINHA + QUEBRA_LINHA;
    }

    private StringBuilder estruturaHtml(int index) {

        StringBuilder builder = new StringBuilder();
        builder.append("<html lang=\"pt-br\">")
                .append("<body>")
                .append("<div id=\"imagem\" style=\"text-align: center;\">")
                .append("<img style=\"width: 120px; margin: 30px; padding=0\"\n" +
                        "src=\"https://itaenga.pe.gov.br/v2/wp-content/uploads/2018/11/LogoPref_Vertical_Atualizada.png\"></img>")
                .append("</div>")
                .append("<br/>")
                .append("<div id=\"margem\" style=\"margin-left: 480px; margin-right: 480px\">")
                .append("<div id=\"saudacao\">")
                .append("<h3>Saudações Demétrio Santana!</h3>")
                .append("</div>")
                .append("<div id=\"paragrafo_p\">")
                .append("<p><font>A PREFEITURA MUNICIPAL DE LAGOA DE ITAENGA, através da startup e-Tributos, está enviando para sua maior comodidade o link para download do seu <b>IPTU 2020!</b> </font></p>")
                .append("</div>")
                .append("<br/>")
                .append("<div id=\"paragrafo_s\">")
                .append("<font>Baixe o seu <b>IPTU 2020</b> no link abaixo!</font>")
                .append("<br/>")
                .append("<a>https://www.cgu.gov.br/simulador/manual.pdf</a>")
                .append("</div>")
                .append("<br/>")
                .append("<div id=\"paragrafo_t\">")
                .append("<p><font>Para maiores informações entre no site oficial da sua cidade ou acesse o nosso site: ")
                .append("<a>https://200.98.162.49/CadCity/</a></font></p>")
                .append("</div>")
                .append("<br/>")
                .append("<br/>")
                .append("<div id=\"paragrafo_q\">")
                .append("<p>")
                .append("<font><b>CONTATOS</b>")
                .append("<br/>")
                .append("<b>Fone/WhatsApp: +55 81 99256-0214</b>")
                .append("<br/>")
                .append("<b>e-mail: e.tributos01@gmail.com</b>")
                .append("</font>")
                .append("</p>")
                .append("</div>")
                .append("</div>")
                .append("</body>")
                .append("</html>");
        return builder;
    }

    private String pegarPrimeiroNome(String nome) {
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
        new Mail();
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getTituloDoEmail() {
        return tituloDoEmail;
    }

    public void setTituloDoEmail(String tituloDoEmail) {
        this.tituloDoEmail = tituloDoEmail;
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

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public StringBuilder getMensageBuilder() {
        return mensageBuilder;
    }

    public void setMensageBuilder(StringBuilder mensageBuilder) {
        this.mensageBuilder = mensageBuilder;
    }
}
