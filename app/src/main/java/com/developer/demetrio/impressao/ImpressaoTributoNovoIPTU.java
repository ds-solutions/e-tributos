package com.developer.demetrio.impressao;

import android.util.Log;

import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.iptu.DescricaoDaDivida;
import com.developer.demetrio.model.Contribuinte;
import com.developer.demetrio.model.Imovel;
//import com.developer.demetrio.impressao.ImpressaoTributo;
import com.developer.demetrio.util.ConstantesSistemas;
import com.developer.demetrio.util.Util;
//import com.developer.demetrio.zebra.android.comm.PrinterBase;
//import com.developer.demetrio.zebra.android.util.internal.StringUtilities;
import com.zebra.sdk.printer.FontUtil;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.util.internal.StringUtilities;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class ImpressaoTributoNovoIPTU extends ImpressaoTributo{
    private static ImpressaoTributoNovoIPTU instancia;
    Fachada fachada = Fachada.getInstance();
    private Imovel imovel;

    private ImpressaoTributoNovoIPTU() {

    }

    public static ImpressaoTributoNovoIPTU getInstancia(Imovel imovelInformado) {
        if (instancia == null){
            instancia = new ImpressaoTributoNovoIPTU();
        }
        instancia.imovel = imovelInformado;
        return instancia;
    }

    public StringBuilder imprimirIptu() {
        this.buffer = new StringBuilder();
        if (this.imovel != null) {
                this.buffer = layoutIPTU(this.imovel);
        }
        return this.buffer;
    }

    private StringBuilder layoutIPTU(Imovel imovel) {
        StringBuilder iptuBuilder = new StringBuilder();
     //   iptuBuilder.append("! 0 816 0 2630 1\n");

        iptuBuilder.append("! 0 816 0 1731 1\n");
        int linha = 110;
        //inserir data e hora da impressão
        iptuBuilder.append("T90 0 1 820 890 IMPRESSO EM: "+ Util.convertDateToDateStr(new Date())+ StringUtilities.LF);

        //inserir codigo da divida e exercicio
        iptuBuilder.append(formarLinha(7, 0, 165, linha, this.imovel.getTributo().getIptu().getCodigoDeBaixa().toString(), 0, 0));
        int linhaDoExercicio = linha - 16;
        iptuBuilder.append(formarLinha(7, 1, 730, linhaDoExercicio, this.imovel.getTributo().getIptu().getExercicio(), 0, 0));

        //inserir matricula
        linha+=45;
        iptuBuilder.append(formarLinha(7, 0, 145, linha, this.imovel.getCadastro().getNumCadastro(), 0, 0));

        //inserir inscrição
        iptuBuilder.append(formarLinha(7, 0, 320, linha, this.imovel.getCadastro().getInscricao(), 0, 0));

        //inserir codigo da divida
        iptuBuilder.append(formarLinha(7, 0, 715, linha, this.imovel.getTributo().getIptu().getCodigoDaDivida(), 0, 0));

        //TODO: inserir contribuinte, mas antes verificar se tem contribuinte alterado
        linha+=45;

            //TODO: nessa condicional, irei verificar se o atributo contribuinte alterado está vazio, se sim colocar o nome do contribuinte, se não colocar o nome do novo contribuinte
            iptuBuilder.append(formarLinha(7, 0, 187, linha, getNomeDoContribuinte(this.imovel.getContribuinte()), 0, 0));


        //inserir logradouro, número e complemento
        linha+=43;
        iptuBuilder.append(formarLinha(7, 0, 185, linha, this.imovel.getEndereco().getLogradouro()+", "+this.imovel.getEndereco().getNumero()+" - "+ this.imovel.getEndereco().getComplemento(), 0, 0));

        //inserir bairro, cidade e UF
        linha+=42;
        iptuBuilder.append(formarLinha(7, 0, 40, linha, this.imovel.getEndereco().getBairro()+" - "+ this.imovel.getEndereco().getCidade()+" - "+this.imovel.getEndereco().getUf(), 0, 0));

        //inserir os harders do demostrativo de cálculos
        linha += 66;
        iptuBuilder.append(formarLinha(7, 0, 40, linha, "Cód.", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 120, linha, "Descrição", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 370, linha, "Valor", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 490, linha, "Pontualidade", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 680, linha, "Isenção", 0, 0));
        int t = this.imovel.getTributo().getIptu().getListDescricao().size();

        linha+=25;
        iptuBuilder.append("LINE 15 " + linha + " 808 " + linha +" 1\n");

        linha = 382;
        int i = 0;
        for (DescricaoDaDivida descricaoDaDivida : this.imovel.getTributo().getIptu().getListDescricao()) {

            if (i <= t) {
                iptuBuilder.append(formarLinha(7, 0, 47, linha, descricaoDaDivida.getCodigo(), 0, 0));

                iptuBuilder.append(formarLinha(7, 0, 120, linha, descricaoDaDivida.getDescricao(), 0, 0));

                iptuBuilder.append(formarLinha(7, 0, 373, linha, margemDireita(descricaoDaDivida.getValor()), 0, 0));

                iptuBuilder.append(formarLinha(7, 0, 493, linha, margemDireita(descricaoDaDivida.getPontualidade()), 0, 0));

                iptuBuilder.append(formarLinha(7, 0, 685, linha, margemDireita(descricaoDaDivida.getIsencao()), 0, 0));

            }
            linha+=30;
        }

        /***
         * INSERIR A SOMA DOS VALORES QUE COMPOEM O IPTU
         */
        //iptuBuilder.append("[COMAND] [INICIO] [EIXO Y INICIO] [FIM] [EIXO Y FIM] [EXPESSURA DA LINHA]\n");
        iptuBuilder.append("LINE 375 "+ linha +" 808 "+ linha +" 1\n");
            linha+=15;
        iptuBuilder.append(formarLinha(7, 0, 130, linha, "SOMA", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 373, linha, margemDireita(this.imovel.getTributo().getIptu().getSomaDoValor()), 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 493, linha, margemDireita(this.imovel.getTributo().getIptu().getSomaDoDesconto()), 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 685, linha, margemDireita(this.imovel.getTributo().getIptu().getSomaIsencao()), 0, 0));


        /***
         * INSERIR VENCIMENTO E VALOR TOTAL
         */
        linha = 601;
        iptuBuilder.append(formarLinha(7, 1, 210, linha, this.imovel.getTributo().getIptu().getVencimento(), 0, 0));

        iptuBuilder.append(formarLinha(7, 1, 680, linha, margemDireita(this.imovel.getTributo().getIptu().getValorTotal()), 0, 0));

        //GRADE COM 3 COLUNAS PARA DIVIDIR O CAMPO DE DESCRIÇÃO DO IMÓVEL
        iptuBuilder.append("LINE 290 689 290 1015 1\nLINE 550 689 550 1015 1\nLINE 15 718 808 718 1\n");

        //hearders da descrição do imóvel
        linha = 689;
        iptuBuilder.append(formarLinha(7, 0, 80, linha, "VALORES VENAIS", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 320, linha, "ÁREAS DO IMÓVEL", 0, 0));

        iptuBuilder.append(formarLinha(7, 0, 620, linha, "ALIQUOTAS", 0, 0));

    //Inserir os dados dos valores venais
        linha = 723;
        iptuBuilder.append(formarLinha(7, 0, 30, linha, "TERRENO:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 175, linha, margemDireita(this.imovel.getCadastro().getValoresVenais().getTerreno()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 30, linha, "EDIFICADA:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 175, linha, margemDireita(this.imovel.getCadastro().getValoresVenais().getEdificada()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 30, linha, "EXCEDENTE:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 175, linha, margemDireita(this.imovel.getCadastro().getValoresVenais().getExcedente()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 30, linha, "TOTAL:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 175, linha, margemDireita(this.imovel.getCadastro().getValoresVenais().getTotal()), 0, 0));

        //Inserir os dados áreas do imóvel
        linha = 723;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "TERRENO:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getAreaDoTerreno()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "EDIFICADA:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getEdificado()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "EDIF. TOTAL:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getAreaTotalEdificado()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "EXCEDENTE:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getExcedente()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "TESTADA:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getTestada()), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7, 0, 305, linha, "FRAÇÃO:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 440, linha, margemDireita(this.imovel.getCadastro().getAreasDoImovel().getFracao()), 0, 0));

        linha = 723;
        //Inserir os dados das aliquotas
        iptuBuilder.append(formarLinha(7, 0, 560, linha, "TERRENO:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 720, linha, this.imovel.getCadastro().getAliquota().getTerreno(), 0, 0));

        linha += 25;
        //Inserir os dados das aliquotas
        iptuBuilder.append(formarLinha(7, 0, 560, linha, "EDIFICADO:", 0, 0));
        iptuBuilder.append(formarLinha(7, 0, 720, linha, this.imovel.getCadastro().getAliquota().getEdificado(), 0, 0));
        linha += 30;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,620, linha, "ZONEAMENTO", 0, 0));
        linha += 25;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,560, linha, "SEÇÃO:  "+this.imovel.getCadastro().getAliquota().getZoneamento(), 0, 0));
        linha += 25;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,570, linha, "TIPO DE EDIFICAÇÃO", 0, 0));
        linha += 25;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,560, linha, this.imovel.getCadastro().getAliquota().getTipoConstrucao(), 0, 0));

        linha += 25;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,560, linha, "CÓDIGO DE COBRANÇA", 0, 0));
        linha += 25;
        iptuBuilder.append("LINE 550 "+linha+" 808 "+linha+" 1\n");
        linha += 4;
        iptuBuilder.append(formarLinha(7,0,560, linha, this.imovel.getCadastro().getAliquota().getCodigoDeCobranca().getTipo(), 0, 0));
        linha += 30;
        iptuBuilder.append(formarLinha(7,0,560, linha, "TESTADA TAXA:", 0, 0));
        iptuBuilder.append(formarLinha(7,0,720, linha, this.imovel.getCadastro().getAliquota().getCodigoDeCobranca().getTaxaTestada(), 0, 0));


        //inserir as mensagens para o contribuinte:
        linha += 115;
        if (this.imovel.getTributo().getIptu().getMensagem() != null) {
            char[] chars = this.imovel.getTributo().getIptu().getMensagem().toCharArray();
            String message = "";
            int cont = 0, index = 0;
            int proximo = 0;
            for (char caracter : chars) {
                proximo = (1 + index);
                if (cont < 63) {
                    message += caracter;
                    cont ++; //= (caracter == ':' && chars[proximo] == ' ') || (caracter == '.' && chars[proximo] == ' ') ? 0 : cont +1;
                }
                if (cont == 62) {
                    iptuBuilder.append(formarLinha(7, 0, 35, linha, message+" - ",0, 0));
                    message = "";
                    cont = 0;
                }
                if ((caracter == ':' && chars[proximo] == ' ') || (caracter == '.' && chars[proximo] == ' ')) {
                    linha +=30;
                    iptuBuilder.append(formarLinha(7, 0, 35, linha, message,0, 0));
                    message = "";
                    cont = 0;
                }

            index++;
            }
            if (chars.length == index) {
                linha +=30;
                iptuBuilder.append(formarLinha(7, 0, 35, linha, message.substring(1),0, 0));
                message = "";
                cont = 0;
            }
/*
            iptuBuilder.append(formarLinha(7, 0, 35, linha, formatarMensagem(this.imovel.getTributo().getIptu().getMensagem()),0, 0));*/
            }

       /* linha += 30;
        if (this.imovel.getTributo().getIptu().getMensagem1() != null) {
            length = this.imovel.getTributo().getIptu().getMensagem1().length();
            if (length <= 61) {
            iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem1(), 0, 0));
            } else {
                iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem1().substring(0, 61), 0, 0));
                linha += 30;
                iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem1().substring(61, tamanoDaString(length)), 0, 0));
            }
        }
        linha += 30;
        if (this.imovel.getTributo().getIptu().getMensagem2() != null) {
            length = this.imovel.getTributo().getIptu().getMensagem2().length();
            if (length <= 61) {
                iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem2(), 0, 0));
            } else {
                iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem2().substring(0, 61), 0, 0));
                linha += 30;
                iptuBuilder.append(formarLinha(7, 0, 35, linha, this.imovel.getTributo().getIptu().getMensagem2().substring(61, tamanoDaString(length)), 0, 0));
            }
        }
*/
        /***
         * TODO: INSERINDO OS DADOS DO CANHOTO
         */
        linha = 1392;
        linhaDoExercicio = linha - 16;
        System.out.println("Quantidade de linha percorrida: "+ linha);
        //inserir codigo da divida e exercicio
        iptuBuilder.append(formarLinha(7, 0, 165, linha, this.imovel.getTributo().getIptu().getCodigoDeBaixa().toString(), 0, 0));

        iptuBuilder.append(formarLinha(7, 1, 730, linhaDoExercicio, this.imovel.getTributo().getIptu().getExercicio(), 0, 0));

        //inserir matricula
        linha+=45;
        iptuBuilder.append(formarLinha(7, 0, 145, linha, this.imovel.getCadastro().getNumCadastro(), 0, 0));

        //inserir inscrição
        iptuBuilder.append(formarLinha(7, 0, 320, linha, this.imovel.getCadastro().getInscricao(), 0, 0));

        //inserir codigo da divida
        iptuBuilder.append(formarLinha(7, 0, 720, linha, this.imovel.getTributo().getIptu().getCodigoDaDivida(), 0, 0));

        //TODO: inserir contribuinte, mas antes verificar se tem contribuinte alterado
        linha+=40;


            //TODO: nessa condicional, irei verificar se o atributo contribuinte alterado está vazio, se sim colocar o nome do contribuinte, se não colocar o nome do novo contribuinte
            iptuBuilder.append(formarLinha(7, 0, 187, linha, getNomeDoContribuinte(this.imovel.getContribuinte()), 0, 0));


        //inserir vencimento e valor total
        linha += 39;
        iptuBuilder.append(formarLinha(7, 1, 200, linha, this.imovel.getTributo().getIptu().getVencimento(), 0, 0));

        iptuBuilder.append(formarLinha(7, 1, 680, linha, margemDireita(this.imovel.getTributo().getIptu().getValorTotal()), 0, 0));

        linha += 55;

        iptuBuilder.append(formarLinha(7,0,80, linha, this.imovel.getTributo().getIptu().getCampo1CodigoDeBarras()+
                " " + this.imovel.getTributo().getIptu().getCampo2CodigoDeBarras()+" "+
                this.imovel.getTributo().getIptu().getCampo3CodigoDeBarras()+" "+
                this.imovel.getTributo().getIptu().getCampo4CodigoDeBarras(),0,0));
        linha += 25;

        iptuBuilder.append("B I2OF5 1 2 100 60 "+linha+" "+ new StringBuilder(this.imovel.getTributo().getIptu().getDigitosDoCodigoDeBarras()+ StringUtilities.LF));
/**
 * ENTENDENDO A GERAÇÃO DO CÓDIGO DE BARRAS COM OS COMANDOS: VB I2OF5 1 2 70 60 2550
 *  VB -> DEVE SER BARRA VERTICAL
 *  I20F5 ->    TIPO
 *  1 -> É A LARGURA
 *  2 -> É A RELAÇÃO
 *  105 -> É A ALTURA
 *  60 -> EIXO x
 *  2550 -> onde termina o eixo y
 */
     /*   linha+=145;
        iptuBuilder.append("LINE 15 "+linha+" 813 "+linha+" 1\n");

        linha+=35;
        iptuBuilder.append(formarLinha(7, 1, 695, linha, String.valueOf((linha-=35)).toString()+StringUtilities.LF, 0, 0));
*/
   iptuBuilder.append(comandoImpressao());

         return iptuBuilder;
    }

    private String getNomeDoContribuinte(Contribuinte contribuinte) {
        String nome = contribuinte.getDadosCadastradosDoContribuinte().getNome();
        if (contribuinte.getAtualizacaoDoContribuinte() != null) {
            if (StringUtils.isNotBlank(contribuinte.getAtualizacaoDoContribuinte().getNome())) {
               nome = contribuinte.getAtualizacaoDoContribuinte().getNome();
            }
        }
         return nome;
    }

    private String formatarMensagem(String mensagem) {
        char[] chars = mensagem.toCharArray();
        String message = "";
        int cont = 0;
        for (char caracter : chars) {
            if (cont < 62) {
                int proximo = 1 + cont;
                   message += (caracter == ':' && chars[proximo] == ' ') || (caracter == '.' && chars[proximo] == ' ') ? caracter+"\n" : caracter;
                   cont = (caracter == ':' && chars[proximo] == ' ') || (caracter == '.' && chars[proximo] == ' ') ? 0 : cont +1;
            }
            if (cont == 61) {
                 message += "\n";
                 cont = 0;
            }
            System.out.println(message);
        }
       return message;
    }




    private String margemDireita(String s) {
        String retorno = "";
        int tamanho = s.length();
        int diferenca = 9 - tamanho;
        if (diferenca > 0) {
            for (int i = 0; i < diferenca; i++) {
                retorno += " ";
            }
            retorno += s;
        } else {
            retorno = s;
        }

        return retorno;
    }

    private int tamanoDaString(int length) {
        return length > 121 ? 121 : length;
    }

    public byte[] converterEmBytes(StringBuilder imovel) {
        byte[] configLabel = null;
        try {
            configLabel = imovel.toString().getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            Log.v(ConstantesSistemas.CATEGORIA, e.getMessage());
            e.printStackTrace();
        }
        return configLabel;
    }
}
