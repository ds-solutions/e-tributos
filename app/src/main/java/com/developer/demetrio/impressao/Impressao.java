package com.developer.demetrio.impressao;

import android.annotation.SuppressLint;

import com.developer.demetrio.fachada.Fachada;
import com.developer.demetrio.model.Imovel;
import com.developer.demetrio.util.Util;
import com.zebra.sdk.util.internal.StringUtilities;
//import com.developer.demetrio.zebra.android.util.internal.StringUtilities;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Impressao {

    protected StringBuilder buffer;
    protected Fachada fachada = Fachada.getInstance();
    protected Imovel imovel;


    protected StringBuilder dividirLinha(int fonte, int tamanhoFonte, int x, int y, String texto, int tamanhoLinha, int deslocarPorLinha) {
        StringBuilder retorno = new StringBuilder();
        int contador = 0;
        int i = 0;
        while (i < texto.length()) {
            contador += tamanhoLinha;
            if (contador > texto.length()) {
                retorno.append("T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, texto.length()).trim() + StringUtilities.LF);
            } else {
                retorno.append("T " + fonte + " " + tamanhoFonte + " " + x + " " + y + " " + texto.substring(i, contador).trim() + StringUtilities.LF);
            }
            y += deslocarPorLinha;
            i += tamanhoLinha;
        }
        return retorno;
    }

    protected StringBuilder formarLinha(int font, int tamanhoFonte, int x, int y, StringBuilder texto, int adicionarColuna, int adicionarLinha) {
        return new StringBuilder("T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto + StringUtilities.LF);
    }

    protected StringBuilder formarLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna, int adicionarLinha) {
        return new StringBuilder("T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto + StringUtilities.LF);
    }

    protected StringBuilder formarLinhaHorizontal(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna, int adicionarLinha) {
        return new StringBuilder("T270 " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto + StringUtilities.LF);
    }

    protected StringBuilder formarLinhaSemProximaLinha(int font, int tamanhoFonte, int x, int y, String texto, int adicionarColuna, int adicionarLinha) {
        return new StringBuilder("T " + font + " " + tamanhoFonte + " " + (x + adicionarColuna) + " " + (y + adicionarLinha) + " " + texto);
    }

    protected boolean isNullOrEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    protected StringBuilder zerosDireita(int tamanhoMaximo, StringBuilder valor) {
        StringBuilder zeros = new StringBuilder();
        for (int i = 0; i < tamanhoMaximo - valor.length(); i++) {
            zeros.append("0");
        }
        return valor.append(zeros);
    }

    protected String VerificarString(BigDecimal valor) {
        if (valor == null) {
            return "--";
        }
        return valor + "";
    }

    protected String substring(String str, int inicio, int tamanho) {
        String retorno = "";
        if (isNullOrEmpty(str) || inicio >= str.length()) {
            return retorno;
        }
        if (str.length() < tamanho) {
            return str;
        }
        return str.substring(inicio, Math.min(inicio + tamanho, str.length() - 1));
    }

    @SuppressLint("WrongConstant")
    protected final String formatarData(Date data) {
        StringBuilder sb = new StringBuilder(25);
        if (data != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(data);
            @SuppressLint("WrongConstant")
            int day = calendar.get(5);
            if (day < 10) {
                sb.append("0");
            }
            sb.append(day);
            sb.append("/");
            @SuppressLint("WrongConstant") int month = calendar.get(2) + 1;
            if (month < 10) {
                sb.append("0");
            }
            sb.append(month);
            sb.append("/");
            sb.append(calendar.get(1));
        }
        return sb.toString();
    }

    protected StringBuilder formatarTelefone(String telefone) {
        if (telefone == null) {
            return new StringBuilder(telefone);
        }
        if (telefone.length() < 10) {
            return new StringBuilder(telefone);
        }
        return new StringBuilder(telefone.substring(0, 2) + " " + telefone.substring(2, 6) + "-" + telefone.substring(6));
    }

    protected StringBuilder formatarValorMonetario(double valor) {
        double valorArredondado = Util.arredondar(valor, 2);
        int inteiro = (int) valorArredondado;
        double decimal = Util.arredondar(valorArredondado - ((double) inteiro), 2);
        StringBuilder inteiroString = new StringBuilder(inteiro);
        StringBuilder comPontoInvertido = new StringBuilder();
        int contador = 0;
        int i = inteiroString.length() - 1;
        while (i >= 0) {
            contador++;
            comPontoInvertido.append(inteiroString.charAt(i));
            if (contador % 3 == 0 && i != 0) {
                comPontoInvertido.append(".");
            }
            i--;
        }
        StringBuilder comPonto = new StringBuilder();
        for (i = comPontoInvertido.length() - 1; i >= 0; i--) {
            contador++;
            comPonto.append(comPontoInvertido.charAt(i));
        }
        StringBuilder decimalString = new StringBuilder(Double.toString(decimal));
        return comPonto.append("," + zerosDireita(2, new StringBuilder(decimalString.substring(2, decimalString.length()))));
    }

    protected void appendTextos(int fonte, int tamanho, int x, int y, String texto, int caracteresPorLinha, int alturaLinha) {
        int i = 0;
        while (i < texto.length()) {
            appendTexto(fonte, tamanho, x, y, false, texto.substring(i, Math.min(i + caracteresPorLinha, texto.length())));
            i += caracteresPorLinha;
            y += alturaLinha;
        }
    }

    protected void appendTextos(int fonte, int tamanho, int x, int y, String[] texto, int alturaLinha) {
        int i = 0;
        while (i < texto.length) {
            appendTexto(fonte, tamanho, x, y, false, texto[i]);
            i++;
            y += alturaLinha;
        }
    }

    protected void appendTexto(int fonte, int tamanho, int x, int y, boolean direita, StringBuilder texto) {
        if (direita) {
            this.buffer.append("RIGHT ");
            this.buffer.append(x);
            this.buffer.append(StringUtilities.LF);
        }
        this.buffer.append("TEXT ");
        this.buffer.append(fonte);
        this.buffer.append(" ");
        this.buffer.append(tamanho);
        this.buffer.append(" ");
        this.buffer.append(x);
        this.buffer.append(" ");
        this.buffer.append(y);
        this.buffer.append(" ");
        this.buffer.append(texto.toString().trim());
        this.buffer.append(StringUtilities.LF);
        if (direita) {
            this.buffer.append("LEFT\n");
        }
    }

    protected void appendTexto(int fonte, int tamanho, int x, int y, boolean direita, String texto) {
        if (direita) {
            this.buffer.append("RIGHT ");
            this.buffer.append(x);
            this.buffer.append(StringUtilities.LF);
        }
        this.buffer.append("TEXT ");
        this.buffer.append(fonte);
        this.buffer.append(" ");
        this.buffer.append(tamanho);
        this.buffer.append(" ");
        this.buffer.append(x);
        this.buffer.append(" ");
        this.buffer.append(y);
        this.buffer.append(" ");
        this.buffer.append(texto.trim());
        this.buffer.append(StringUtilities.LF);
        if (direita) {
            this.buffer.append("LEFT\n");
        }
    }

    protected void appendTexto70(int x, int y, StringBuilder texto) {
        appendTexto(7, 0, x, y, false, texto);
    }

    protected void appendTexto70(int x, int y, boolean direita, StringBuilder texto) {
        appendTexto(7, 0, x, y, direita, texto);
    }

    protected void appendTexto70(int x, int y, String texto) {
        appendTexto(7, 0, x, y, false, texto);
    }

    protected void appendTexto20(int x, int y, String texto) {
        appendTexto(0, 2, x, y, false, texto);
    }

    protected void appendTexto70(int x, int y, boolean direita, String texto) {
        appendTexto(7, 0, x, y, direita, texto);
    }

    protected void appendLinha(int xIni, int yIni, int xFim, int yFim, float width) {
        this.buffer.append("LINE ");
        this.buffer.append(xIni);
        this.buffer.append(" ");
        this.buffer.append(yIni);
        this.buffer.append(" ");
        this.buffer.append(xFim);
        this.buffer.append(" ");
        this.buffer.append(yFim);
        this.buffer.append(" ");
        this.buffer.append(width);
        this.buffer.append(StringUtilities.LF);
    }

    protected void appendTexto(String texto) {
        this.buffer.append(texto);
    }

    protected void appendBarcode(int x, int y, String barcode) {
        this.buffer.append("BARCODE I2OF5 0.24 2 13 ");
        this.buffer.append(x);
        this.buffer.append(" ");
        this.buffer.append(y);
        this.buffer.append(" ");
        this.buffer.append(barcode);
        this.buffer.append(StringUtilities.LF);
    }

    protected static String[] cortarEndereco(String endereco) {
        if (endereco.length() <= 62) {
            return new String[]{endereco};
        }
        int ultimoEspaco;
        for (ultimoEspaco = 62; endereco.charAt(ultimoEspaco) != ' '; ultimoEspaco--) {
        }
        return new String[]{endereco.substring(0, ultimoEspaco), endereco.substring(ultimoEspaco + 1)};
    }

    protected String comandoImpressao() {
        return "FORM\nPRINT ";
    }

    protected String formarLinhaSeiko(int tamanhoFonte, int x, int margemSuperior, String texto) {
        String ativarComando;
        String desativarComando;
        switch (tamanhoFonte) {
            case 2:
                ativarComando = "1B5701";
                desativarComando = "1B5700";
                break;
            case 3:
                ativarComando = "1B7701";
                desativarComando = "1B7700";
                break;
            case 4:
                ativarComando = "124611";
                desativarComando = "124600";
                break;
            default:
                ativarComando = "";
                desativarComando = "";
                break;
        }
        return getMargenSuperior(margemSuperior) + getQuebraDeLinha() + getMargenEsquerda(x) + ativarComando + asciiToHex(texto) + desativarComando;
    }

    protected String formarLinhaSeiko(int x, String texto) {
        return getMargenEsquerda(x) + asciiToHex(texto);
    }

    protected String getMargenEsquerda(int margem) {
        return "1B6C" + intToHex(margem);
    }

    protected String getMargenSuperior(int margem) {
        return "1B33" + intToHex(margem);
    }

    protected String getTamanhoFonteMenor() {
        return "124600";
    }

    protected String intToHex(int valor) {
        String hex = Integer.toHexString(valor);
        if (hex.length() == 1) {
            return "0" + hex;
        }
        return hex;
    }

    protected String getQuebraDeLinha() {
        return "0A";
    }

    protected String getFeed() {
        return "0C";
    }

    protected String asciiToHex(String asciiValue) {
        char[] chars = asciiValue.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (char toHexString : chars) {
            hex.append(Integer.toHexString(toHexString));
        }
        return hex.toString();
    }

    protected StringBuilder setPageMode(int n, int x, int yl, int yh, StringBuilder conteudoPageMode) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("127A00" + intToHex(n) + intToHex(x) + intToHex(yl) + intToHex(yh));
        buffer.append("122432" + intToHex(0));
        buffer.append("122433" + intToHex(2));
        buffer.append(conteudoPageMode);
        buffer.append("127A01");
        return buffer;
    }

    protected StringBuilder pularLinha(int quantidadeLinhas) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < quantidadeLinhas; i++) {
            buffer.append(getQuebraDeLinha());
        }
        return buffer;
    }

    protected String setTexto(int nl, int nh, String texto) {
        return "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto);
    }

    protected String setTextoComQuebraLinha(int nl, int nh, String texto) {
        return "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto) + getQuebraDeLinha();
    }

    protected String getEspacamentoInicioTexto() {
        return "1B241200";
    }

    protected String setDetalhamentoConta(int nl, int nh, String texto, String texto2, String texto3) {
        return getQuebraDeLinha() + "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto) + "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto2) + "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto3);
    }

    protected String setDetalhamentoContaDescricao(int nl, int nh, String texto) {
        return getQuebraDeLinha() + "1B24" + intToHex(nl) + intToHex(nh) + asciiToHex(texto);
    }
}
